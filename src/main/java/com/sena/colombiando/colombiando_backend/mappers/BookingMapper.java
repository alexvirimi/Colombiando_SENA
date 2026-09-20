package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.BookingDto;
import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceEntity;
import com.sena.colombiando.colombiando_backend.entities.UserEntity;
import com.sena.colombiando.colombiando_backend.repositories.ScheduleInstanceRepository;
import com.sena.colombiando.colombiando_backend.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    private final UserRepository userRepository;
    private final ScheduleInstanceRepository scheduleInstanceRepository;

    private final UserMapper userMapper;
    private final ScheduleInstanceMapper scheduleInstanceMapper;

    public BookingMapper(UserRepository userRepository,
                         ScheduleInstanceRepository scheduleInstanceRepository,
                         UserMapper userMapper,
                         ScheduleInstanceMapper scheduleInstanceMapper) {
        this.userRepository = userRepository;
        this.scheduleInstanceRepository = scheduleInstanceRepository;
        this.userMapper = userMapper;
        this.scheduleInstanceMapper = scheduleInstanceMapper;
    }

    public BookingEntity toEntity(BookingDto.Create request) {
        var entity = new BookingEntity();
        UserEntity user = userRepository.getReferenceById(request.userId());
        ScheduleInstanceEntity scheduleInstance = scheduleInstanceRepository.getReferenceById(request.scheduleInstanceId());

        entity.setNumPeople(request.numPeople());
        entity.setUser(user);
        entity.setScheduleInstance(scheduleInstance);

        return entity;
    }

    public BookingDto.BookingPublic toPublicDto(BookingEntity entity) {
        if(entity == null) {
            return null;
        }

        return new BookingDto.BookingPublic(
                entity.getId(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    public BookingDto.Response toDto(BookingEntity entity) {
        if (entity == null) {
            return null;
        }

        return new BookingDto.Response(
                entity.getId(),
                entity.getNumPeople(),
                userMapper.toPublicDto(entity.getUser()),
                entity.getTotalPrice(),
                scheduleInstanceMapper.toDto(entity.getScheduleInstance()),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

}
