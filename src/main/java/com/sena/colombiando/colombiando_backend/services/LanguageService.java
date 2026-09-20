package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.LanguageDto;
import com.sena.colombiando.colombiando_backend.entities.LanguageEntity;
import com.sena.colombiando.colombiando_backend.mappers.LanguageMapper;
import com.sena.colombiando.colombiando_backend.repositories.LanguageRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LanguageService {

    private final LanguageMapper languageMapper;
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageMapper languageMapper, LanguageRepository languageRepository) {
        this.languageMapper = languageMapper;
        this.languageRepository = languageRepository;
    }

    @Transactional
    public LanguageDto.Response createLanguage(LanguageDto.Create request) {
        if (languageRepository.existsByCode(request.code())) {
            throw new EntityExistsException("Ya existe un idioma con ese código.");
        }

        var entity = languageMapper.toEntity(request);
        entity = languageRepository.save(entity);
        return languageMapper.toDto(entity);
    }

    @Transactional
    public LanguageDto.Response deleteLanguage(UUID id) {
        var entity = languageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Idioma no encontrado."));
        languageRepository.delete(entity);
        return languageMapper.toDto(entity);
    }

    @Transactional
    public LanguageDto.Response findByCode(String code) {
        var entity = languageRepository.findByCode(code);
        return languageMapper.toDto(entity);
    }

    @Transactional
    public LanguageDto.Response getById(UUID id) {
        var entity = languageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Idioma no encontrado."));
        return languageMapper.toDto(entity);
    }

    @Transactional
    public List<LanguageDto.Response> getAllLanguages(UUID id) {
        List<LanguageEntity> languages = languageRepository.findAll();
        List<LanguageDto.Response> responses = new ArrayList<>();

        languages.forEach(entity -> {
            responses.add(languageMapper.toDto(entity));
        });

        return responses;
    }
}
