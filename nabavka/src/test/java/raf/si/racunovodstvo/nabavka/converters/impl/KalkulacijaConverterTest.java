package raf.si.racunovodstvo.nabavka.converters.impl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import raf.si.racunovodstvo.nabavka.model.Kalkulacija;
import raf.si.racunovodstvo.nabavka.model.Konverzija;
import raf.si.racunovodstvo.nabavka.requests.KalkulacijaRequest;
import raf.si.racunovodstvo.nabavka.responses.KalkulacijaResponse;
import raf.si.racunovodstvo.nabavka.responses.KonverzijaResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class KalkulacijaConverterTest {

    @InjectMocks
    private KalkulacijaConverter kalkulacijaConverter;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void convertTest() {
        KalkulacijaRequest source = Mockito.mock(KalkulacijaRequest.class);
        Kalkulacija expectedResponse = new Kalkulacija();
        given(modelMapper.map(source, Kalkulacija.class)).willReturn(expectedResponse);

        Kalkulacija actualResponse = kalkulacijaConverter.convert(source);

        assertEquals(expectedResponse, actualResponse);
    }
}
