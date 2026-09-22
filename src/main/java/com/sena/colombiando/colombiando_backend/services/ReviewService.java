package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.ReviewDto;
import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.ReviewEntity;
import com.sena.colombiando.colombiando_backend.entities.UserEntity;
import com.sena.colombiando.colombiando_backend.mappers.ReviewMapper;
import com.sena.colombiando.colombiando_backend.repositories.BookingRepository;
import com.sena.colombiando.colombiando_backend.repositories.ReviewRepository;
import com.sena.colombiando.colombiando_backend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            ReviewMapper reviewMapper,
            UserRepository userRepository,
            BookingRepository bookingRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    private List<ReviewDto.Response> responses(List<ReviewEntity> reviews){
        List<ReviewDto.Response> responses = new ArrayList<>();

        for (ReviewEntity review : reviews) {
            responses.add(reviewMapper.toDto(review));
        }

        return responses;
    }

    @Transactional
    public List<ReviewDto.Response> getAllReviews(){
        List<ReviewEntity> reviews = reviewRepository.findAll();
        return responses(reviews);
    }

    @Transactional
    public List<ReviewDto.Response> getReviewsByBookingId(UUID id){
        List<ReviewEntity> reviews = reviewRepository.findByBookingId(id);
        return responses(reviews);
    }

    @Transactional
    public List<ReviewDto.Response> getReviewsByUserId(UUID id){
        List<ReviewEntity> reviews = reviewRepository.findByUserId(id);
        return responses(reviews);
    }

    @Transactional
    public ReviewDto.Response getReviewById(UUID id){
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrado."));
        return reviewMapper.toDto(review);
    }

    @Transactional
    public ReviewDto.Response createReview(ReviewDto.Create request){
        UserEntity user = userRepository.getReferenceById(request.userId());
        BookingEntity booking = bookingRepository.getReferenceById(request.bookingId());

        ReviewEntity review = reviewMapper.toEntity(request);
        review.setUser(user);
        review.setBooking(booking);

        reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    @Transactional
    public ReviewDto.Response updateReview(UUID id, ReviewDto.Update request){
        var dataBase = request.data();

        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrado."));


        Optional.ofNullable(dataBase.rating()).ifPresent(review::setRating);
        Optional.ofNullable(dataBase.review()).ifPresent(review::setReview);

        reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    @Transactional
    public ReviewDto.Response deleteReview(UUID id){
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrado."));
        reviewRepository.delete(review);
        return reviewMapper.toDto(review);
    }

    @Transactional
    public Double getAverageRating(UUID id){
        return reviewRepository.averageRatingByGuide(id);
    }
}
