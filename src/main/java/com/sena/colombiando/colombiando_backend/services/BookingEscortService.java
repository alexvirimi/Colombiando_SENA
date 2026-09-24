package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.BookingEscortDto;
import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.BookingEscortEntity;
import com.sena.colombiando.colombiando_backend.mappers.BookingEscortMapper;
import com.sena.colombiando.colombiando_backend.repositories.BookingEscortRepository;
import com.sena.colombiando.colombiando_backend.repositories.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingEscortService {

    private BookingEscortMapper bookingEscortMapper;
    private BookingEscortRepository bookingEscortRepository;
    private BookingRepository bookingRepository;

/** Inicializa la instancia.
 * @param bookingEscortMapper parametro de entrada.
 * @param bookingEscortRepository parametro de entrada.
 * @param bookingRepository parametro de entrada.
 */
    public BookingEscortService(
            BookingEscortMapper bookingEscortMapper,
            BookingEscortRepository bookingEscortRepository,
            BookingRepository bookingRepository
    ) {
        this.bookingEscortMapper = bookingEscortMapper;
        this.bookingEscortRepository = bookingEscortRepository;
        this.bookingRepository = bookingRepository;
    }

/** Crea booking escort.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public BookingEscortDto.Response createBookingEscort(BookingEscortDto.Create request) {
        BookingEntity booking = bookingRepository.getReferenceById(request.bookingId());

        BookingEscortEntity bookingEscort = bookingEscortMapper.toEntity(request);
        bookingEscort.setBooking(booking);

        bookingEscortRepository.save(bookingEscort);
        return bookingEscortMapper.toDto(bookingEscort);
    }

/** Actualiza booking.
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public BookingEscortDto.Response updateBooking(UUID id, BookingEscortDto.Update request) {
        var dataBase = request.data();
        BookingEscortEntity bookingEscort = bookingEscortRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Acompañante de reserva no encontrado."));

        Optional.ofNullable(dataBase.name()).ifPresent(bookingEscort::setName);
        Optional.ofNullable(dataBase.lastName()).ifPresent(bookingEscort::setLastName);
        Optional.ofNullable(dataBase.documentType()).ifPresent(bookingEscort::setDocumentType);
        Optional.ofNullable(dataBase.idNumber()).ifPresent(bookingEscort::setIdNumber);
        Optional.ofNullable(request.birthDate()).ifPresent(bookingEscort::setBirthDate);

        bookingEscortRepository.save(bookingEscort);
        return bookingEscortMapper.toDto(bookingEscort);
    }

/** Elimina booking.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public BookingEscortDto.Response deleteBooking(UUID id) {
        BookingEscortEntity bookingEscort = bookingEscortRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Acompañante de reserva no encontrado."));
        bookingEscortRepository.delete(bookingEscort);
        return bookingEscortMapper.toDto(bookingEscort);
    }

/** Consulta booking escort.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public BookingEscortDto.Response getBookingEscort(UUID id) {
        BookingEscortEntity bookingEscort = bookingEscortRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Acompañante de reserva no encontrado."));
        return bookingEscortMapper.toDto(bookingEscort);
    }

/** Consulta all booking escorts.
 * @return resultado de la operacion.
 */
    @Transactional
    public List<BookingEscortDto.Response> getAllBookingEscorts() {
        List<BookingEscortEntity> bookingEscorts = bookingEscortRepository.findAll();
        List<BookingEscortDto.Response> responses = new ArrayList<>();

        for (BookingEscortEntity booking : bookingEscorts) {
            responses.add(bookingEscortMapper.toDto(booking));
        }

        return responses;
    }

/** Consulta all bookings by booking id.
 * @param bookingId parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public List<BookingEscortDto.Response> getAllBookingsByBookingId(UUID bookingId) {
        List<BookingEscortEntity> bookingEscorts = bookingEscortRepository.findByBookingId(bookingId);
        List<BookingEscortDto.Response> responses = new ArrayList<>();

        for (BookingEscortEntity booking : bookingEscorts) {
            responses.add(bookingEscortMapper.toDto(booking));
        }

        return responses;
    }

/** Cuenta escorts by booking id.
 * @param bookingId parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public Integer countEscortsByBookingId(UUID bookingId) {
        return bookingEscortRepository.countByBookingId(bookingId);
    }

/** Elimina escorts by booking id.
 * @param bookingId parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public List<BookingEscortDto.Response> deleteEscortsByBookingId(UUID bookingId) {
        List<BookingEscortDto.Response> escortsEliminated = getAllBookingsByBookingId(bookingId);
        bookingEscortRepository.deleteByBookingId(bookingId);
        return escortsEliminated.isEmpty() ? null : escortsEliminated;
    }

}
