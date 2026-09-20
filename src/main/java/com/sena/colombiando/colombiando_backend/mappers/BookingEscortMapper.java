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

    public BookingEscortMapper(
            BookingMapper bookingMapper,
            BookingRepository bookingRepository) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
    }

    public BookingEscortEntity toEntity(BookingEscortDto.Create request) {
        var entity = new BookingEscortEntity();
        var dataBase = request.data();

        BookingEntity booking = bookingRepository.getReferenceById(request.bookingId());

        entity.setBooking(booking);
        entity.setName(dataBase.name());
        entity.setLastName(dataBase.lastName());
        entity.setDocumentType(dataBase.documentType());
        entity.setIdNumber(dataBase.idNumber());
        entity.setBirthDate(dataBase.birthDate());

        return entity;
    }

    public BookingEscortDto.Response toDto(BookingEscortEntity entity) {
        if (entity == null) {
            return null;
        }

        var dataBase = new BookingEscortDto.Base(
                entity.getName(),
                entity.getLastName(),
                entity.getDocumentType(),
                entity.getIdNumber(),
                entity.getBirthDate()
        );

        return new BookingEscortDto.Response(
                entity.getId(),
                dataBase,
                bookingMapper.toPublicDto(entity.getBooking())
        );
    }

}