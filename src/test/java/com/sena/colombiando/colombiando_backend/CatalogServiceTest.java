package com.sena.colombiando.colombiando_backend;

import com.sena.colombiando.colombiando_backend.dto.*;
import com.sena.colombiando.colombiando_backend.entities.*;
import com.sena.colombiando.colombiando_backend.mappers.*;
import com.sena.colombiando.colombiando_backend.repositories.*;
import com.sena.colombiando.colombiando_backend.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogServiceTest {

    @Mock AddressRepository addressRepository;
    @Mock AddressMapper addressMapper;
    @Mock PlaceRepository placeRepository;
    @Mock PlaceMapper placeMapper;
    @Mock GuideRepository guideRepository;
    @Mock GuideMapper guideMapper;
    @Mock GuideLanguageRepository guideLanguageRepository;
    @Mock GuideLanguageMapper guideLanguageMapper;
    @Mock LanguageRepository languageRepository;

    private AddressService addressService;
    private PlaceService placeService;
    private GuideService guideService;
    private GuideLanguageService guideLanguageService;

    @BeforeEach
    void setUp() {
        addressService = new AddressService(addressRepository, addressMapper);
        placeService = new PlaceService(placeMapper, placeRepository, addressRepository);
        guideService = new GuideService(guideMapper, guideRepository);
        guideLanguageService = new GuideLanguageService(
                guideLanguageMapper, guideLanguageRepository, guideRepository, languageRepository);
    }

    @Test
    void cpCat01_createsAddress() {
        AddressDto.Create request = new AddressDto.Create(
                new AddressDto.Base("Calle 1", "CO", "Cartagena",
                        new BigDecimal("10.39"), new BigDecimal("-75.48")));
        AddressEntity entity = new AddressEntity();
        AddressDto.Response response = new AddressDto.Response(null, request.data());
        when(addressMapper.toEntity(request)).thenReturn(entity);
        when(addressMapper.toDto(entity)).thenReturn(response);

        assertSame(response, addressService.createAddress(request));
        verify(addressRepository).save(entity);
    }

    @Test
    void cpCat02_createsPlaceWithExistingAddress() {
        UUID addressId = UUID.randomUUID();
        PlaceDto.Create request = new PlaceDto.Create(
                new PlaceDto.Base("Castillo", "Lugar histórico", "image.jpg"), addressId);
        AddressEntity address = new AddressEntity();
        PlaceEntity place = new PlaceEntity();
        when(placeMapper.toEntity(request)).thenReturn(place);
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(placeMapper.toDto(place)).thenReturn(null);

        placeService.createPlace(request);

        assertSame(address, place.getAddress());
        verify(placeRepository).save(place);
    }

    @Test
    void cpCat03_queriesPlacesByAllIdAndAddress() {
        UUID id = UUID.randomUUID();
        PlaceEntity place = new PlaceEntity();
        when(placeRepository.findAll()).thenReturn(List.of(place));
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeRepository.findByAddressId(id)).thenReturn(place);
        when(placeMapper.toDto(place)).thenReturn(null);

        assertEquals(1, placeService.getAllPlaces().size());
        assertNull(placeService.getPlace(id));
        assertNull(placeService.getPlaceByAddressId(id));
    }

    @Test
    void cpCat04_createsQueriesAndDeletesGuide() {
        UUID id = UUID.randomUUID();
        GuideDto.Create request = new GuideDto.Create(
                new GuideDto.Base("Ana", "Guía", "Bio", "300", null));
        GuideEntity guide = new GuideEntity();
        when(guideMapper.toEntity(request)).thenReturn(guide);
        when(guideMapper.toDto(guide)).thenReturn(null);
        when(guideRepository.findById(id)).thenReturn(Optional.of(guide));

        guideService.createGuide(request);
        guideService.getGuide(id);
        guideService.deleteGuide(id);

        assertEquals(GuideStatusEnum.SUSPENDED, guide.getStatus());
        verify(guideRepository, times(2)).save(guide);
    }

    @Test
    void cpCat05_updatesAndDeletesGuideLanguage() {
        UUID guideId = UUID.randomUUID();
        UUID languageId = UUID.randomUUID();
        GuideLanguageId id = new GuideLanguageId();
        id.setGuideId(guideId);
        id.setLanguageId(languageId);
        GuideLanguageEntity entity = new GuideLanguageEntity();
        when(guideLanguageRepository.findById(id)).thenReturn(Optional.of(entity));
        when(guideLanguageMapper.toDto(entity)).thenReturn(null);

        guideLanguageService.updateGuideLanguage(
                id, new GuideLanguageDto.Update(GuideLanguagesLevelEnum.C1));
        guideLanguageService.deleteGuideLanguage(id);

        assertEquals(GuideLanguagesLevelEnum.C1, entity.getLevel());
        verify(guideLanguageRepository).delete(entity);
    }
}
