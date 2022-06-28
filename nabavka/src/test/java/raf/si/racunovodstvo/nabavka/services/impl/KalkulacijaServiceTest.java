package raf.si.racunovodstvo.nabavka.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import raf.si.racunovodstvo.nabavka.converters.impl.KalkulacijaConverter;
import raf.si.racunovodstvo.nabavka.converters.impl.KalkulacijaReverseConverter;
import raf.si.racunovodstvo.nabavka.model.Kalkulacija;
import raf.si.racunovodstvo.nabavka.model.KalkulacijaArtikal;
import raf.si.racunovodstvo.nabavka.repositories.KalkulacijaRepository;
import raf.si.racunovodstvo.nabavka.requests.KalkulacijaRequest;
import raf.si.racunovodstvo.nabavka.responses.KalkulacijaResponse;

import javax.persistence.EntityNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class KalkulacijaServiceTest {

    @InjectMocks
    private KalkulacijaService kalkulacijaService;

    @Mock
    private KalkulacijaRepository kalkulacijaRepository;

    @Mock
    private KalkulacijaConverter kalkulacijaConverter;

    @Mock
    private KalkulacijaReverseConverter kalkulacijaReverseConverter;

    private static final Long KALKULACIJA_ID_MOCK = 1L;

    @Test
    void saveTest() {
        KalkulacijaRequest kalkulacijaRequest = new KalkulacijaRequest();
        Kalkulacija kalkulacija = new Kalkulacija();
        KalkulacijaResponse kalkulacijaResponse = new KalkulacijaResponse();
        given(kalkulacijaConverter.convert(kalkulacijaRequest)).willReturn(kalkulacija);
        given(kalkulacijaRepository.save(kalkulacija)).willReturn(kalkulacija);
        given(kalkulacijaReverseConverter.convert(kalkulacija)).willReturn(kalkulacijaResponse);

        assertEquals(kalkulacijaResponse, kalkulacijaService.save(kalkulacijaRequest));
    }

    @Test
    void updateTest() {
        KalkulacijaRequest kalkulacijaRequest = new KalkulacijaRequest();
        kalkulacijaRequest.setId(KALKULACIJA_ID_MOCK);
        Kalkulacija kalkulacija = Mockito.mock(Kalkulacija.class);
        KalkulacijaResponse kalkulacijaResponse = Mockito.mock(KalkulacijaResponse.class);
        Optional<Kalkulacija> optionalArtikal = Optional.of(kalkulacija);
        given(kalkulacijaRepository.findById(KALKULACIJA_ID_MOCK)).willReturn(optionalArtikal);
        given(kalkulacijaConverter.convert(kalkulacijaRequest)).willReturn(kalkulacija);
        given(kalkulacijaRepository.save(kalkulacija)).willReturn(kalkulacija);
        given(kalkulacijaReverseConverter.convert(kalkulacija)).willReturn(kalkulacijaResponse);

        assertEquals(kalkulacijaResponse, kalkulacijaService.update(kalkulacijaRequest));
    }

    @Test
    void updateExceptionTest() {
        KalkulacijaRequest kalkulacijaRequest = new KalkulacijaRequest();
        kalkulacijaRequest.setId(KALKULACIJA_ID_MOCK);
        given(kalkulacijaService.findById(KALKULACIJA_ID_MOCK)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> kalkulacijaService.update(kalkulacijaRequest));
    }

    @Test
    void deleteByIdTest() {
        given(kalkulacijaRepository.findById(KALKULACIJA_ID_MOCK)).willReturn(Optional.of(Mockito.mock(Kalkulacija.class)));
        given(kalkulacijaReverseConverter.convert(any())).willReturn(new KalkulacijaResponse());

        kalkulacijaService.deleteById(KALKULACIJA_ID_MOCK);

        then(kalkulacijaRepository).should(times(1)).deleteById(KALKULACIJA_ID_MOCK);
    }

    @Test
    void deleteByIdExceptionTest() {
        given(kalkulacijaRepository.findById(KALKULACIJA_ID_MOCK)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> kalkulacijaService.deleteById(KALKULACIJA_ID_MOCK));
    }

    @Test
    void findAllPageableTest() {
        Kalkulacija kalkulacija = Mockito.mock(Kalkulacija.class);
        Page<Kalkulacija> kalkulacijaPage = new PageImpl<>(List.of(kalkulacija));
        KalkulacijaResponse kalkulacijaResponse = new KalkulacijaResponse();
        Pageable pageable = Mockito.mock(Pageable.class);
        given(kalkulacijaRepository.findAll(pageable)).willReturn(kalkulacijaPage);
        given(kalkulacijaReverseConverter.convert(kalkulacija)).willReturn(kalkulacijaResponse);

        Page<KalkulacijaResponse> result = kalkulacijaService.findAll(pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(kalkulacijaResponse, result.getContent().get(0));
    }

    @Test
    void findAllPageableWithSpecTest() {
        Kalkulacija kalkulacija = Mockito.mock(Kalkulacija.class);
        Page<Kalkulacija> kalkulacijaPage = new PageImpl<>(List.of(kalkulacija));
        KalkulacijaResponse kalkulacijaResponse = new KalkulacijaResponse();
        Pageable pageable = Mockito.mock(Pageable.class);
        Specification<Kalkulacija> spec = Mockito.mock(Specification.class);
        given(kalkulacijaRepository.findAll(spec,pageable)).willReturn(kalkulacijaPage);
        given(kalkulacijaReverseConverter.convert(kalkulacija)).willReturn(kalkulacijaResponse);

        Page<KalkulacijaResponse> result = kalkulacijaService.findAll(spec,pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(kalkulacijaResponse, result.getContent().get(0));
    }

    @Test
    void findAllTest() {
        Kalkulacija kalkulacija = Mockito.mock(Kalkulacija.class);
        List<Kalkulacija> kalkulacijaList = List.of(kalkulacija);
        Pageable pageable = Mockito.mock(Pageable.class);
        given(kalkulacijaRepository.findAll()).willReturn(kalkulacijaList);

        List<Kalkulacija> result = kalkulacijaService.findAll();
        assertEquals(1, result.size());
        assertEquals(kalkulacija, result.get(0));
    }

    @Test
    void findAllKalkulacijeTest() {
        Kalkulacija kalkulacija = Mockito.mock(Kalkulacija.class);
        Page<Kalkulacija> kalkulacijaPage = new PageImpl<>(List.of(kalkulacija));
        KalkulacijaResponse kalkulacijaResponse = new KalkulacijaResponse();
        Pageable pageable = Mockito.mock(Pageable.class);
        Specification<Kalkulacija> spec = Mockito.mock(Specification.class);
        given(kalkulacijaRepository.findAll(spec,pageable)).willReturn(kalkulacijaPage);

        Page<Kalkulacija> result = kalkulacijaService.findAllKalkulacije(spec, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(kalkulacijaPage, result);
    }

    @Test
    void increaseProdajnaAndNabavnaCenaTest() {

        KalkulacijaRequest kalkulacijaRequest = new KalkulacijaRequest();
        kalkulacijaRequest.setId(KALKULACIJA_ID_MOCK);
        Kalkulacija kalkulacija = Mockito.mock(Kalkulacija.class);
        Optional<Kalkulacija> optionalArtikal = Optional.of(kalkulacija);
        given(kalkulacijaRepository.findById(KALKULACIJA_ID_MOCK)).willReturn(optionalArtikal);
        given(kalkulacijaRepository.save(any(Kalkulacija.class))).willReturn(kalkulacija);

        KalkulacijaArtikal kalkulacijaArtikal = new KalkulacijaArtikal();
        kalkulacijaArtikal.setProdajnaCena(1D);
        kalkulacijaArtikal.setUkupnaProdajnaVrednost(1D);
        assertEquals(kalkulacija, kalkulacijaService.increaseNabavnaAndProdajnaCena(KALKULACIJA_ID_MOCK, kalkulacijaArtikal));
    }

    @Test
    void increaseProdajnaAndNabavnaCenaExceptionTest() {
        KalkulacijaRequest kalkulacijaRequest = new KalkulacijaRequest();
        kalkulacijaRequest.setId(KALKULACIJA_ID_MOCK);
        given(kalkulacijaService.findById(KALKULACIJA_ID_MOCK)).willReturn(Optional.empty());
        KalkulacijaArtikal kalkulacijaArtikal = new KalkulacijaArtikal();
        kalkulacijaArtikal.setProdajnaCena(1D);
        kalkulacijaArtikal.setUkupnaProdajnaVrednost(1D);
        assertThrows(EntityNotFoundException.class, () -> kalkulacijaService.increaseNabavnaAndProdajnaCena(KALKULACIJA_ID_MOCK, kalkulacijaArtikal));
    }

    @Test
    void saveKalkulacijaTest() {
        Kalkulacija kalkulacija = new Kalkulacija();
        given(kalkulacijaRepository.save(any(Kalkulacija.class))).willReturn(kalkulacija);

        assertEquals(kalkulacija, kalkulacijaService.save(kalkulacija));
    }

    @Test
    void getTotalKalkulacijeTest() {
        Kalkulacija kalkulacija = new Kalkulacija();

        KalkulacijaArtikal artikal = new KalkulacijaArtikal();
        artikal.setKolicina(1);
        artikal.setRabat(0.0);
        artikal.setNabavnaCena(0.0);
        artikal.setNabavnaCenaPosleRabata(0.0);
        artikal.setUkupnaNabavnaVrednost(0.0);
        artikal.setMarza(0.0);
        artikal.setProdajnaOsnovica(0.0);
        artikal.setPorez(0.0);
        artikal.setProdajnaCena(0.0);
        artikal.setOsnovica(0.0);
        artikal.setUkupnaProdajnaVrednost(0.0);

        List<KalkulacijaArtikal> artikalList = new ArrayList<>();
        artikalList.add(artikal);

        kalkulacija.setArtikli(artikalList);

        List<Kalkulacija> kalkulacijaList = new ArrayList<>();
        kalkulacijaList.add(kalkulacija);

        Map<String, Number> values = new HashMap<>();
        values.put("totalKolicina", 1);
        values.put("totalRabat", 0.0);
        values.put("totalNabavnaCena", 0.0);
        values.put("totalNabavnaCenaPosleRabata", 0.0);
        values.put("totalNabavnaVrednost", 0.0);
        values.put("totalMarza", 0.0);
        values.put("totalOsnovicaZaProdaju", 0.0);
        values.put("totalPorez", 0.0);
        values.put("totalProdajnaCena", 0.0);
        values.put("totalPoreskaOsnovica", 0.0);
        values.put("totalProdajnaVrednost", 0.0);

        assertEquals(values, kalkulacijaService.getTotalKalkulacije(kalkulacijaList));
    }
}
