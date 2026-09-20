package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.GuideDto;
import com.sena.colombiando.colombiando_backend.entities.GuideEntity;
import com.sena.colombiando.colombiando_backend.entities.GuideStatusEnum;
import com.sena.colombiando.colombiando_backend.mappers.GuideMapper;
import com.sena.colombiando.colombiando_backend.repositories.GuideRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuideService {

    private final GuideMapper guideMapper;
    private final GuideRepository guideRepository;

    public GuideService(GuideMapper guideMapper, GuideRepository guideRepository) {
        this.guideMapper = guideMapper;
        this.guideRepository = guideRepository;
    }

    @Transactional
    public GuideDto.Response createGuide(GuideDto.Create request) {
        GuideEntity guide = guideMapper.toEntity(request);
        guideRepository.save(guide);
        return guideMapper.toDto(guide);
    }

    @Transactional
    public GuideDto.Response updateGuide(UUID id, GuideDto.Update request) {
        var dataRequest = request.data();

        GuideEntity guide = guideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Guía no encontrado."));

        Optional.ofNullable(dataRequest.name())
                .ifPresent(guide::setName);
        Optional.ofNullable(dataRequest.lastName())
                .ifPresent(guide::setLastName);
        Optional.ofNullable(dataRequest.bio())
                .ifPresent(guide::setBio);
        Optional.ofNullable(dataRequest.phone())
                .ifPresent(guide::setPhone);
        Optional.ofNullable(dataRequest.profileImage())
                .ifPresent(guide::setProfileImage);
        Optional.ofNullable(request.status())
                .ifPresent(guide::setStatus);

        guideRepository.save(guide);
        return guideMapper.toDto(guide);
    }

    @Transactional
    public GuideDto.Response deleteGuide(UUID id) {
        GuideEntity guide = guideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Guía no encontrado."));

        guide.setStatus(GuideStatusEnum.SUSPENDED);

        guideRepository.save(guide);
        return guideMapper.toDto(guide);
    }

    @Transactional
    public GuideDto.Response getGuide(UUID id) {
        GuideEntity guide = guideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Guía no encontrado."));
        return guideMapper.toDto(guide);
    }

    @Transactional
    public List<GuideDto.Response> GetAllGuides() {
        List<GuideEntity> guides = guideRepository.findAll();
        List<GuideDto.Response> responses = new ArrayList<>();

        for (GuideEntity guideEntity : guides) {
            responses.add(guideMapper.toDto(guideEntity));
        }

        return responses;
    }

    @Transactional
    public List<GuideDto.Response> getGuideByNameAndLastName(String name, String lastName) {
        List<GuideEntity> guides = guideRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, lastName);
        List<GuideDto.Response> responses = new ArrayList<>();

        for (GuideEntity guideEntity : guides) {
            responses.add(guideMapper.toDto(guideEntity));
        }

        return responses;
    }

    @Transactional
    public List<GuideDto.Response> getGuideByStatus(GuideStatusEnum status) {
        List<GuideEntity> guides = guideRepository.findByStatus(status);
        List<GuideDto.Response> responses = new ArrayList<>();

        for (GuideEntity guideEntity : guides) {
            responses.add(guideMapper.toDto(guideEntity));
        }

        return responses;
    }
}
