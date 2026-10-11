package com.sena.colombiando.colombiando_backend;

import com.sena.colombiando.colombiando_backend.dto.*;
import com.sena.colombiando.colombiando_backend.entities.*;
import com.sena.colombiando.colombiando_backend.mappers.*;
import com.sena.colombiando.colombiando_backend.repositories.*;
import com.sena.colombiando.colombiando_backend.services.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleBookingServiceTest {

    @Mock ScheduleRepository scheduleRepository;
    @Mock ScheduleMapper scheduleMapper;
    @Mock GuideRepository guideRepository;
    @Mock PlaceRepository placeRepository;
    @Mock ScheduleInstanceRepository instanceRepository;
    @Mock ScheduleInstanceMapper instanceMapper;
    @Mock BookingRepository bookingRepository;
    @Mock BookingMapper bookingMapper;
    @Mock UserRepository userRepository;
    @Mock BookingEscortRepository escortRepository;
    @Mock BookingEscortMapper escortMapper;

    private ScheduleService scheduleService;
    private ScheduleInstanceService instanceService;
    private BookingService bookingService;
    private BookingEscortService escortService;

    @BeforeEach
    void setUp() {
        scheduleService = new ScheduleService(
                scheduleRepository, scheduleMapper, guideRepository, placeRepository);
        instanceService = new ScheduleInstanceService(
                instanceMapper, instanceRepository, scheduleRepository);
        bookingService = new BookingService(
                bookingMapper, bookingRepository, userRepository, instanceRepository, instanceService);
        escortService = new BookingEscortService(
                escortMapper, escortRepository, bookingRepository);
    }

    @Test
    void cpHor01_createsSchedule() {
        UUID guideId = UUID.randomUUID();
        UUID placeId = UUID.randomUUID();
        ScheduleDto.Create request = scheduleRequest(guideId, placeId);
        ScheduleEntity schedule = scheduleEntity();
        when(guideRepository.getReferenceById(guideId)).thenReturn(new GuideEntity());
        when(placeRepository.getReferenceById(placeId)).thenReturn(new PlaceEntity());
        when(scheduleMapper.toEntity(request)).thenReturn(schedule);
        when(scheduleMapper.toDto(schedule)).thenReturn(null);

        scheduleService.createSchedule(request);

        verify(scheduleRepository).save(schedule);
    }

    @Test
    void cpHor02_listsUpdatesAndDeletesSchedule() {
        UUID id = UUID.randomUUID();
        ScheduleEntity schedule = scheduleEntity();
        when(scheduleRepository.findById(id)).thenReturn(Optional.of(schedule));
        when(scheduleMapper.toDto(schedule)).thenReturn(null);
        ScheduleDto.Update update = new ScheduleDto.Update(
                new ScheduleDto.Base(LocalTime.of(9, 0), LocalTime.of(12, 0),
                        LocalDate.of(2026, 1, 2), LocalDate.of(2026, 1, 3)),
                12, new BigDecimal("60000"), ScheduleStatusEnum.ACTIVE);

        assertNull(scheduleService.getSchedule(id));
        scheduleService.updateSchedule(id, update);
        scheduleService.deleteSchedule(id);

        assertEquals(ScheduleStatusEnum.INACTIVE, schedule.getStatus());
        verify(scheduleRepository, times(2)).save(schedule);
    }

    @Test
    void cpHor03_createsScheduleInstanceWithAvailableCapacity() {
        UUID scheduleId = UUID.randomUUID();
        ScheduleEntity schedule = scheduleEntity();
        ScheduleInstanceDto.Create request = new ScheduleInstanceDto.Create(
                scheduleId, new ScheduleInstanceDto.Base(LocalDate.now(), 10));
        ScheduleInstanceEntity instance = new ScheduleInstanceEntity();
        instance.setMaxCapacity(10);
        instance.setAvailableCapacity(10);
        when(scheduleRepository.getReferenceById(scheduleId)).thenReturn(schedule);
        when(instanceMapper.toEntity(request)).thenReturn(instance);
        when(instanceMapper.toDto(instance)).thenReturn(null);

        instanceService.createScheduleInstace(request);

        assertSame(schedule, instance.getSchedule());
        assertEquals(10, instance.getAvailableCapacity());
    }

    @Test
    void cpRes01_createsBookingAndBooksAvailableSlots() {
        UUID userId = UUID.randomUUID();
        UUID instanceId = UUID.randomUUID();
        BookingDto.Create request = new BookingDto.Create(userId, instanceId, 3);
        UserEntity user = new UserEntity();
        ScheduleInstanceEntity instance = instanceEntity(10, new BigDecimal("50000"));
        BookingEntity booking = new BookingEntity();
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(instanceRepository.findById(instanceId)).thenReturn(Optional.of(instance));
        when(instanceRepository.decreaseCapacity(instanceId, 3)).thenAnswer(invocation -> {
            instance.setAvailableCapacity(instance.getAvailableCapacity() - 3);
            return 1;
        });
        when(bookingMapper.toEntity(request)).thenReturn(booking);
        when(bookingMapper.toDto(booking)).thenReturn(null);

        bookingService.createBooking(request);

        assertEquals(new BigDecimal("150000"), booking.getTotalPrice());
        verify(instanceRepository).decreaseCapacity(instanceId, 3);
        assertEquals(7, instance.getAvailableCapacity());
        verify(bookingRepository).save(booking);
    }

    @Test
    void cpRes02_rejectsBookingWithoutCapacity() {
        UUID instanceId = UUID.randomUUID();
        BookingDto.Create request = new BookingDto.Create(UUID.randomUUID(), instanceId, 11);
        when(instanceRepository.findById(instanceId)).thenReturn(Optional.of(instanceEntity(10, BigDecimal.TEN)));
        when(instanceRepository.decreaseCapacity(instanceId, 11)).thenReturn(0);

        assertThrows(IllegalStateException.class, () -> bookingService.createBooking(request));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void cpRes03_rejectsUnknownInstance() {
        UUID instanceId = UUID.randomUUID();
        when(instanceRepository.findById(instanceId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> bookingService.createBooking(new BookingDto.Create(UUID.randomUUID(), instanceId, 1)));
    }

    @Test
    void cpRes04_queriesUpdatesAndDeletesBooking() {
        UUID id = UUID.randomUUID();
        BookingEntity booking = new BookingEntity();
        booking.setNumPeople(2);
        booking.setScheduleInstance(instanceEntity(10, BigDecimal.TEN));
        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));
        when(bookingMapper.toDto(booking)).thenReturn(null);
        when(instanceRepository.restoreCapacity(any(), anyInt())).thenReturn(1);

        assertNull(bookingService.getBooking(id));
        bookingService.updateBooking(id, new BookingDto.Update(4, BookingStatusEnum.CONFIRMED));
        bookingService.deleteBooking(id);

        assertEquals(BookingStatusEnum.CANCELLED, booking.getStatus());
        verify(bookingRepository, times(2)).save(booking);
    }

    @Test
    void cpRes05_createsEscort() {
        UUID bookingId = UUID.randomUUID();
        BookingEscortDto.Create request = new BookingEscortDto.Create(
                bookingId, LocalDate.of(2000, 1, 1),
                new BookingEscortDto.Base("Ana", "Test", UserDocumentTypeEnum.CC, "123"));
        BookingEntity booking = new BookingEntity();
        BookingEscortEntity escort = new BookingEscortEntity();
        when(bookingRepository.getReferenceById(bookingId)).thenReturn(booking);
        when(escortMapper.toEntity(request)).thenReturn(escort);
        when(escortMapper.toDto(escort)).thenReturn(null);

        escortService.createBookingEscort(request);

        assertSame(booking, escort.getBooking());
        verify(escortRepository).save(escort);
    }

    @Test
    void cpRes06_listsAndCountsEscorts() {
        UUID bookingId = UUID.randomUUID();
        when(escortRepository.findByBookingId(bookingId)).thenReturn(
                List.of(new BookingEscortEntity(), new BookingEscortEntity(), new BookingEscortEntity()));
        when(escortRepository.countByBookingId(bookingId)).thenReturn(3);
        when(escortMapper.toDto(any())).thenReturn(null);

        assertEquals(3, escortService.getAllBookingsByBookingId(bookingId).size());
        assertEquals(3, escortService.countEscortsByBookingId(bookingId));
    }

    @Test
    void cpRes07_updatesAndDeletesEscort() {
        UUID id = UUID.randomUUID();
        BookingEscortEntity escort = new BookingEscortEntity();
        when(escortRepository.findById(id)).thenReturn(Optional.of(escort));
        when(escortMapper.toDto(escort)).thenReturn(null);
        BookingEscortDto.Update update = new BookingEscortDto.Update(
                new BookingEscortDto.Base("New", "Name", UserDocumentTypeEnum.CE, "456"),
                LocalDate.of(1999, 1, 1));

        escortService.updateBooking(id, update);
        escortService.deleteBooking(id);

        assertEquals("New", escort.getName());
        verify(escortRepository).delete(escort);
    }

    private ScheduleDto.Create scheduleRequest(UUID guideId, UUID placeId) {
        return new ScheduleDto.Create(guideId, placeId, 10, new BigDecimal("50000"),
                new ScheduleDto.Base(LocalTime.of(8, 0), LocalTime.of(12, 0),
                        LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 2)));
    }

    private ScheduleEntity scheduleEntity() {
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setStartDate(LocalDate.of(2026, 1, 1));
        schedule.setEndDate(LocalDate.of(2026, 1, 2));
        schedule.setPricePerPerson(new BigDecimal("50000"));
        return schedule;
    }

    private ScheduleInstanceEntity instanceEntity(int capacity, BigDecimal price) {
        ScheduleEntity schedule = scheduleEntity();
        schedule.setPricePerPerson(price);
        ScheduleInstanceEntity instance = new ScheduleInstanceEntity();
        instance.setMaxCapacity(capacity);
        instance.setAvailableCapacity(capacity);
        instance.setSchedule(schedule);
        return instance;
    }
}
