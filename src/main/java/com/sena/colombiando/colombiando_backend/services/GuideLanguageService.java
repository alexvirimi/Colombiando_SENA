package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.GuideLanguageDto;
import com.sena.colombiando.colombiando_backend.entities.GuideEntity;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguageEntity;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguageId;
import com.sena.colombiando.colombiando_backend.entities.LanguageEntity;
import com.sena.colombiando.colombiando_backend.mappers.GuideLanguageMapper;
import com.sena.colombiando.colombiando_backend.repositories.GuideLanguageRepository;
import com.sena.colombiando.colombiando_backend.repositories.GuideRepository;
import com.sena.colombiando.colombiando_backend.repositories.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuideLanguageService {

    private final GuideLanguageMapper guideLanguageMapper;
    private final GuideLanguageRepository guideLanguageRepository;
    private final GuideRepository guideRepository;
    private final LanguageRepository languageRepository;

/** Inicializa la instancia.
 * @param guideLanguageMapper parametro de entrada.
 * @param guideLanguageRepository parametro de entrada.
 * @param guideRepository parametro de entrada.
 * @param languageRepository parametro de entrada.
 */
    public GuideLanguageService(
            GuideLanguageMapper guideLanguageMapper,
            GuideLanguageRepository guideLanguageRepository,
            GuideRepository guideRepository,
            LanguageRepository languageRepository
    ) {
        this.guideLanguageMapper = guideLanguageMapper;
        this.guideLanguageRepository = guideLanguageRepository;
        this.guideRepository = guideRepository;
        this.languageRepository = languageRepository;
    }

/** Ejecuta la operacion responses.
 * @param guideLanguages parametro de entrada.
 * @return resultado de la operacion.
 */
    private List<GuideLanguageDto.Response> responses(List<GuideLanguageEntity> guideLanguages) {
        List<GuideLanguageDto.Response> responses = new ArrayList<>();

        for (GuideLanguageEntity guideLanguage : guideLanguages){
            responses.add(guideLanguageMapper.toDto(guideLanguage));
        }

        return responses;
    }

/** Crea guide language.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public GuideLanguageDto.Response createGuideLanguage(GuideLanguageDto.Create request) {
        GuideEntity guide = guideRepository.getReferenceById(request.guideId());
        LanguageEntity language = languageRepository.getReferenceById(request.languageId());

        GuideLanguageEntity guideLanguage = guideLanguageMapper.toEntity(request);
        guideLanguage.setGuide(guide);
        guideLanguage.setLanguage(language);

        guideLanguageRepository.save(guideLanguage);
        return guideLanguageMapper.toDto(guideLanguage);
    }

/** Actualiza guide language.
 * @param guideLanguageId parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public GuideLanguageDto.Response updateGuideLanguage(
            GuideLanguageId guideLanguageId, GuideLanguageDto.Update request
    ) {
        GuideLanguageEntity guideLanguage = guideLanguageRepository
                .findById(guideLanguageId).orElseThrow(() -> new EntityNotFoundException("Idioma del guía no encontrado."));

        Optional.ofNullable(request.level()).ifPresent(guideLanguage::setLevel);

        guideLanguageRepository.save(guideLanguage);
        return guideLanguageMapper.toDto(guideLanguage);
    }

/** Elimina guide language.
 * @param guideLanguageId parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public GuideLanguageDto.Response deleteGuideLanguage(GuideLanguageId guideLanguageId) {
        GuideLanguageEntity guideLanguage = guideLanguageRepository
                .findById(guideLanguageId).orElseThrow(() -> new EntityNotFoundException("Idioma del guía no encontrado."));
        guideLanguageRepository.delete(guideLanguage);
        return guideLanguageMapper.toDto(guideLanguage);
    }

/** Consulta guide language.
 * @param guideLanguageId parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public GuideLanguageDto.Response getGuideLanguage(GuideLanguageId guideLanguageId) {
        GuideLanguageEntity guideLanguage = guideLanguageRepository
                .findById(guideLanguageId).orElseThrow(() -> new EntityNotFoundException("Idioma del guía no encontrado."));
        return guideLanguageMapper.toDto(guideLanguage);
    }

/** Consulta search guide languages.
 * @param guideId parametro de entrada.
 * @param languageId parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public List<GuideLanguageDto.Response> searchGuideLanguages(
            UUID guideId, UUID languageId
    ) {
        List<GuideLanguageEntity>  guideLanguages = guideLanguageRepository.findAll();
        return responses(guideLanguages);
    }

/** Consulta guide language by guide id and language id.
 * @param guideId parametro de entrada.
 * @param languageId parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public GuideLanguageDto.Response getGuideLanguageByGuideIdAndLanguageId(
            UUID guideId, UUID languageId
    ) {
        GuideLanguageEntity guideLanguage = guideLanguageRepository.findByGuideIdAndLanguageId(guideId, languageId);
        if  (guideLanguage == null) {
            return null;
        }
        return guideLanguageMapper.toDto(guideLanguage);
    }

/** Elimina guide language by guide id and language id.
 * @param guideId parametro de entrada.
 * @param languageId parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public GuideLanguageDto.Response deleteGuideLanguageByGuideIdAndLanguageId(UUID guideId, UUID languageId) {
        GuideLanguageDto.Response guideLanguageToBeDeleted = getGuideLanguageByGuideIdAndLanguageId(guideId, languageId);
        guideLanguageRepository.deleteByGuideIdAndLanguageId(guideId, languageId);
        return guideLanguageToBeDeleted;
    }

}
