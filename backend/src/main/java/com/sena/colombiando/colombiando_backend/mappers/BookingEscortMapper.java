package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.BookingEscortDto;
import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.BookingEscortEntity;
import com.sena.colombiando.colombiando_backend.repositories.BookingRepository;
import org.springframework.stereotype.Component;

@Component
public class BookingEscortMapper {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;

/** Inicializa la instancia.
 * @param bookingMapper parametro de entrada.
 * @param bookingRepository parametro de entrada.
 */
    public BookingEscortMapper(
            BookingMapper bookingMapper,
            BookingRepository bookingRepository) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
    }

/** Convierte los datos mediante to entity.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    public BookingEscortEntity toEntity(BookingEscortDto.Create request) {
        var entity = new BookingEscortEntity();
        var dataBase = request.data();

        BookingEntity booking = bookingRepository.getReferenceById(request.bookingId());

        entity.setBooking(booking);
        entity.setName(dataBase.name());
        entity.setLastName(dataBase.lastName());
        entity.setDocumentType(dataBase.documentType());
        entity.setIdNumber(dataBase.idNumber());
        entity.setBirthDate(request.birthDate());

        return entity;
    }

/** Convierte los datos mediante to dto.
 * @param entity parametro de entrada.
 * @return resultado de la operacion.
 */
    public BookingEscortDto.Response toDto(BookingEscortEntity entity) {
        if (entity == null) {
            return null;
        }

        var dataBase = new BookingEscortDto.Base(
                entity.getName(),
                entity.getLastName(),
                entity.getDocumentType(),
                entity.getIdNumber()
        );

        return new BookingEscortDto.Response(
                entity.getId(),
                dataBase,
                entity.getBirthDate(),
                bookingMapper.toPublicDto(entity.getBooking())
        );
    }

}
