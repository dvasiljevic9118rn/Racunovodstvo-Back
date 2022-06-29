package raf.si.racunovodstvo.nabavka.converters.impl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import raf.si.racunovodstvo.nabavka.model.Konverzija;
import raf.si.racunovodstvo.nabavka.responses.KonverzijaResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class KonverzijaConverterTest {

    @InjectMocks
    private KonverzijaConverter konverzijaConverter;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void convertTest() {
        Konverzija source = Mockito.mock(Konverzija.class);
        KonverzijaResponse expectedResponse = new KonverzijaResponse();
        given(modelMapper.map(source, KonverzijaResponse.class)).willReturn(expectedResponse);

        KonverzijaResponse actualResponse = konverzijaConverter.convert(source);

        assertEquals(expectedResponse, actualResponse);
    }
}
