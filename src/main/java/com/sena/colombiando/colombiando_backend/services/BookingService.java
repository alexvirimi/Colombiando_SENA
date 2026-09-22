package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.BookingDto;
import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.BookingStatusEnum;
import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceEntity;
import com.sena.colombiando.colombiando_backend.entities.UserEntity;
import com.sena.colombiando.colombiando_backend.mappers.BookingMapper;
import com.sena.colombiando.colombiando_backend.repositories.BookingRepository;
import com.sena.colombiando.colombiando_backend.repositories.ScheduleInstanceRepository;
import com.sena.colombiando.colombiando_backend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ScheduleInstanceRepository scheduleInstanceRepository;
    private final ScheduleInstanceService scheduleInstanceService;

/** Inicializa la instancia.
 * @param bookingMapper parametro de entrada.
 * @param bookingRepository parametro de entrada.
 * @param userRepository parametro de entrada.
 * @param scheduleInstanceRepository parametro de entrada.
 * @param scheduleInstanceService parametro de entrada.
 */
    public BookingService(
            BookingMapper bookingMapper,
            BookingRepository bookingRepository,
            UserRepository userRepository,
            ScheduleInstanceRepository scheduleInstanceRepository,
            ScheduleInstanceService scheduleInstanceService
    ) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.scheduleInstanceRepository = scheduleInstanceRepository;
        this.scheduleInstanceService = scheduleInstanceService;
    }

/** Ejecuta la operacion responses.
 * @param bookings parametro de entrada.
 * @return resultado de la operacion.
 */
    private List<BookingDto.Response> responses(List<BookingEntity> bookings){
        if (bookings.isEmpty()){
            return new ArrayList<>();
        }

        List<BookingDto.Response> responses = new ArrayList<>();
        for (BookingEntity booking : bookings) {
            responses.add(bookingMapper.toDto(booking));
        }
        return responses;
    }

/** Crea booking.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public BookingDto.Response createBooking(BookingDto.Create request){
        UserEntity user = userRepository.getReferenceById(request.userId());
        ScheduleInstanceEntity scheduleInstance = scheduleInstanceRepository.findById(request.scheduleInstanceId())
                .orElseThrow(() -> new EntityNotFoundException("Instancia de horario no encontrada."));

        scheduleInstanceService.bookSlots(request.scheduleInstanceId(), request.numPeople());

        BigDecimal totalPrice = scheduleInstance.getSchedule().getPricePerPerson()
                .multiply(BigDecimal.valueOf(request.numPeople()));

        BookingEntity booking = bookingMapper.toEntity(request);
        booking.setUser(user);
        booking.setScheduleInstance(scheduleInstance);
        booking.setTotalPrice(totalPrice);

        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

/** Actualiza booking.
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public BookingDto.Response updateBooking(UUID id, BookingDto.Update request){
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));

        Optional.ofNullable(request.numPeople()).ifPresent(booking::setNumPeople);
        Optional.ofNullable(request.status()).ifPresent(booking::setStatus);

        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

/** Elimina booking.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public BookingDto.Response deleteBooking(UUID id){
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));

        if (booking.getStatus() != BookingStatusEnum.CANCELLED) {
            scheduleInstanceService.releaseSlots(booking.getScheduleInstance().getId(), booking.getNumPeople());
            booking.setStatus(BookingStatusEnum.CANCELLED);
            bookingRepository.save(booking);
        }

        return bookingMapper.toDto(booking);
    }

/** Consulta booking.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public BookingDto.Response getBooking(UUID id){
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));
        return bookingMapper.toDto(booking);
    }

/** Consulta search bookings.
 * @param userId parametro de entrada.
 * @param scheduleInstanceId parametro de entrada.
 * @param status parametro de entrada.
 * @param fromDate parametro de entrada.
 * @param toDate parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public List<BookingDto.Response> searchBookings(
            UUID userId, UUID scheduleInstanceId, BookingStatusEnum status,
            LocalDate fromDate, LocalDate toDate
    ){
        List<BookingEntity> bookings = bookingRepository.search(
                userId, scheduleInstanceId, status, fromDate, toDate
        );
        return responses(bookings);
    }

}
