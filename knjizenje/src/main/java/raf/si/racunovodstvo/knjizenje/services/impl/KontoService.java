package raf.si.racunovodstvo.knjizenje.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import raf.si.racunovodstvo.knjizenje.converter.KontoConverter;
import raf.si.racunovodstvo.knjizenje.model.Konto;
import raf.si.racunovodstvo.knjizenje.repositories.KontoRepository;
import raf.si.racunovodstvo.knjizenje.responses.GlavnaKnjigaResponse;
import raf.si.racunovodstvo.knjizenje.services.IService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class KontoService implements IService<Konto, Long> {

    @Lazy
    @Autowired
    private KontoConverter kontoConverter;

    private final KontoRepository kontoRepository;

    @Autowired
    public KontoService(KontoRepository kontoRepository) {
        this.kontoRepository = kontoRepository;
    }

    @Override
    public Konto save(Konto konto) {
        return kontoRepository.save(konto);
    }

    @Override
    public Optional<Konto> findById(Long id) {
        return kontoRepository.findById(id);
    }

    public Konto update(Long id) {
        return save(findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<Konto> findAll() {
        return kontoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        kontoRepository.deleteById(id);
    }

    public List<Konto> findByKontnaGrupa(String kontnaGrupa) {
        return this.kontoRepository.findKontoByKontnaGrupaBrojKonta(kontnaGrupa);
    }

    public List<Konto> findAll(Specification<Konto> spec) {
        return this.kontoRepository.findAll(spec);
    }

    public Page<GlavnaKnjigaResponse> findAllGlavnaKnjigaResponse(Specification<Konto> spec, Pageable pageSort) {
        return this.kontoConverter.convert(this.kontoRepository.findAll(spec, pageSort).getContent());
    }
}
