package raf.si.racunovodstvo.nabavka.converters.impl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import raf.si.racunovodstvo.nabavka.model.Konverzija;
import raf.si.racunovodstvo.nabavka.requests.KonverzijaRequest;
import raf.si.racunovodstvo.nabavka.responses.KonverzijaResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class KonverzijaRequestConverterTest {

    @InjectMocks
    private KonverzijaRequestConverter konverzijaRequestConverter;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void convertTest() {
        KonverzijaRequest source = Mockito.mock(KonverzijaRequest.class);
        Konverzija expectedResponse = new Konverzija();
        given(modelMapper.map(source, Konverzija.class)).willReturn(expectedResponse);

        Konverzija actualResponse = konverzijaRequestConverter.convert(source);

        assertEquals(expectedResponse, actualResponse);
    }
}
