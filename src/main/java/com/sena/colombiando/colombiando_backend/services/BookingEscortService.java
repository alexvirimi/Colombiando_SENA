package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.BookingEscortDto;
import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.BookingEscortEntity;
import com.sena.colombiando.colombiando_backend.mappers.BookingEscortMapper;
import com.sena.colombiando.colombiando_backend.repositories.BookingEscortRepository;
import com.sena.colombiando.colombiando_backend.repositories.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookingEscortService {

    private BookingEscortMapper bookingEscortMapper;
    private BookingEscortRepository bookingEscortRepository;
    private BookingRepository bookingRepository;

    public BookingEscortService(
            BookingEscortMapper bookingEscortMapper,
            BookingEscortRepository bookingEscortRepository,
            BookingRepository bookingRepository
    ) {
        this.bookingEscortMapper = bookingEscortMapper;
        this.bookingEscortRepository = bookingEscortRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public BookingEscortDto.Response createBooking(BookingEscortDto.Create request) {
        var dataBase = request.data();
        BookingEntity booking = bookingRepository.getReferenceById(request.bookingId());

        BookingEscortEntity bookingEscort = bookingEscortMapper.toEntity(request);
        bookingEscort.setBooking(booking);

        bookingEscortRepository.save(bookingEscort);
        return bookingEscortMapper.toDto(bookingEscort);
    }

    @Transactional
    public BookingEscortDto.Response updateBooking(UUID id, BookingEscortDto.Update request) {
        var dataBase = request.data();
        BookingEscortEntity bookingEscort = bookingEscortRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Acompañante de reserva no encontrado."));

        Optional.ofNullable(dataBase.name()).ifPresent(bookingEscort::setName);
        Optional.ofNullable(dataBase.lastName()).ifPresent(bookingEscort::setLastName);
        Optional.ofNullable(dataBase.documentType()).ifPresent(bookingEscort::setDocumentType);
        Optional.ofNullable(dataBase.idNumber()).ifPresent(bookingEscort::setIdNumber);
        Optional.ofNullable(dataBase.birthDate()).ifPresent(bookingEscort::setBirthDate);

        bookingEscortRepository.save(bookingEscort);
        return bookingEscortMapper.toDto(bookingEscort);
    }

    @Transactional
    public BookingEscortDto.Response deleteBooking(UUID id) {
        BookingEscortEntity bookingEscort = bookingEscortRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Acompañante de reserva no encontrado."));
        bookingEscortRepository.delete(bookingEscort);
        return bookingEscortMapper.toDto(bookingEscort);
    }

    @Transactional
    public BookingEscortDto.Response getBooking(UUID id) {
        BookingEscortEntity bookingEscort = bookingEscortRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Acompañante de reserva no encontrado."));
        return bookingEscortMapper.toDto(bookingEscort);
    }

    @Transactional
    public List<BookingEscortDto.Response> getAllBooking() {
        List<BookingEscortEntity> bookingEscorts = bookingEscortRepository.findAll();
        List<BookingEscortDto.Response> responses = new ArrayList<>();

        for (BookingEscortEntity booking : bookingEscorts) {
            responses.add(bookingEscortMapper.toDto(booking));
        }

        return responses;
    }

    @Transactional
    public List<BookingEscortDto.Response> findByBookingId(UUID bookingId) {
        List<BookingEscortEntity> bookingEscorts = bookingEscortRepository.findByBookingId(bookingId);
        List<BookingEscortDto.Response> responses = new ArrayList<>();

        for (BookingEscortEntity booking : bookingEscorts) {
            responses.add(bookingEscortMapper.toDto(booking));
        }

        return responses;
    }

    @Transactional
    public int countEscortsByBookingId(UUID bookingId) {
        return bookingEscortRepository.countByBookingId(bookingId);
    }

    @Transactional
    public List<BookingEscortDto.Response> deleteEscortsByBookingId(UUID bookingId) {
        List<BookingEscortDto.Response> escortsEliminated = findByBookingId(bookingId);
        bookingEscortRepository.deleteByBookingId(bookingId);
        return escortsEliminated.isEmpty() ? null : escortsEliminated;
    }

}
