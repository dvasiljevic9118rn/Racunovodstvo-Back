package raf.si.racunovodstvo.knjizenje.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import raf.si.racunovodstvo.knjizenje.converters.IConverter;
import raf.si.racunovodstvo.knjizenje.model.Knjizenje;
import raf.si.racunovodstvo.knjizenje.responses.KnjizenjeResponse;
import raf.si.racunovodstvo.knjizenje.services.impl.IKnjizenjeService;


import java.util.ArrayList;
import java.util.List;


@Component
public class KnjizenjeConverter implements IConverter<List<Knjizenje>, Page<KnjizenjeResponse>> {

    @Autowired
    private IKnjizenjeService knjizenjeService;

    @Override
    public Page<KnjizenjeResponse> convert(List<Knjizenje> knjizenja) {
        List<KnjizenjeResponse> responses = new ArrayList<>();
        for (Knjizenje currKnjizenje : knjizenja) {
            KnjizenjeResponse response = new KnjizenjeResponse();

            if (currKnjizenje.getDokument() != null) {
                response.setDokumentId(currKnjizenje.getDokument().getDokumentId());
            }

            response.setKnjizenjeId(currKnjizenje.getKnjizenjeId());
            response.setDatumKnjizenja(currKnjizenje.getDatumKnjizenja());
            response.setBrojNaloga(currKnjizenje.getBrojNaloga());
            response.setKomentar(currKnjizenje.getKomentar());
            response.setDuguje(knjizenjeService.getSumaDugujeZaKnjizenje(currKnjizenje.getKnjizenjeId()));
            response.setPotrazuje(knjizenjeService.getSumaPotrazujeZaKnjizenje(currKnjizenje.getKnjizenjeId()));
            response.setSaldo(knjizenjeService.getSaldoZaKnjizenje(currKnjizenje.getKnjizenjeId()));
            responses.add(response);
        }
        return new PageImpl<>(responses);
    }
}
