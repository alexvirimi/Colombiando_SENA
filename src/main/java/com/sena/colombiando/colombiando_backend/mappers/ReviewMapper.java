package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.ReviewDto;
import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.ReviewEntity;
import com.sena.colombiando.colombiando_backend.entities.UserEntity;
import com.sena.colombiando.colombiando_backend.repositories.BookingRepository;
import com.sena.colombiando.colombiando_backend.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    private final UserMapper userMapper;
    private final BookingMapper bookingMapper;

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

/** Inicializa la instancia.
 * @param userMapper parametro de entrada.
 * @param bookingMapper parametro de entrada.
 * @param userRepository parametro de entrada.
 * @param bookingRepository parametro de entrada.
 */
    public ReviewMapper(
            UserMapper userMapper,
            BookingMapper bookingMapper,
            UserRepository userRepository,
            BookingRepository bookingRepository
    ) {
        this.userMapper = userMapper;
        this.bookingMapper = bookingMapper;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

/** Convierte los datos mediante to entity.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    public ReviewEntity toEntity(ReviewDto.Create request) {
        var entity = new ReviewEntity();

        UserEntity user = userRepository.getReferenceById(request.userId());
        BookingEntity booking = bookingRepository.getReferenceById(request.bookingId());

        entity.setUser(user);
        entity.setBooking(booking);
        entity.setRating(request.rating());
        entity.setReview(request.review());

        return entity;
    }

/** Convierte los datos mediante to dto.
 * @param entity parametro de entrada.
 * @return resultado de la operacion.
 */
    public ReviewDto.Response toDto(ReviewEntity entity) {
        if (entity == null){
            return null;
        }

        var dataBase = new ReviewDto.Base(
                entity.getRating(),
                entity.getReview()
        );

        return new ReviewDto.Response(
                entity.getId(),
                userMapper.toPublicDto(entity.getUser()),
                bookingMapper.toPublicDto(entity.getBooking()),
                dataBase,
                entity.getCreatedAt()
        );
    }

}
