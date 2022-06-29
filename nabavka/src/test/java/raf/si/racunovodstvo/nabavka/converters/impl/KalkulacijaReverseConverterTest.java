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
import raf.si.racunovodstvo.nabavka.responses.KalkulacijaResponse;
import raf.si.racunovodstvo.nabavka.responses.KonverzijaResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class KalkulacijaReverseConverterTest {

    @InjectMocks
    private KalkulacijaReverseConverter kalkulacijaReverseConverter;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void convertTest() {
        Kalkulacija source = Mockito.mock(Kalkulacija.class);
        KalkulacijaResponse expectedResponse = new KalkulacijaResponse();
        given(modelMapper.map(source, KalkulacijaResponse.class)).willReturn(expectedResponse);

        KalkulacijaResponse actualResponse = kalkulacijaReverseConverter.convert(source);

        assertEquals(expectedResponse, actualResponse);
    }
}
