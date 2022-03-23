package rs.raf.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.Faktura;
import rs.raf.demo.model.TipFakture;
import rs.raf.demo.repositories.IFakturaRepository;
import rs.raf.demo.services.IFakturaService;

import java.util.*;

@Service
public class FakturaService implements IFakturaService {

    private final IFakturaRepository fakturaRepository;

    @Autowired
    public FakturaService(IFakturaRepository fakturaRepository) {
        this.fakturaRepository = fakturaRepository;
    }

    public List<Faktura> findAll(){
        return fakturaRepository.findAll();
    }

    public List<Faktura> findAll(Specification<Faktura> spec){
        return fakturaRepository.findAll(spec);
    }


    public List<Faktura> findUlazneFakture(){
        List<Faktura> ulazneFakture = new ArrayList<>();
        for(Faktura f : fakturaRepository.findAll()){
            if(f.getTipFakture().equals(TipFakture.ULAZNA_FAKTURA)){
                ulazneFakture.add(f);
            }
        }
        return ulazneFakture;
    }

    public List<Faktura> findIzlazneFakture(){
        List<Faktura> izlacneFakture = new ArrayList<>();
        for(Faktura f : fakturaRepository.findAll()){
            if(f.getTipFakture().equals(TipFakture.IZLAZNA_FAKTURA)){
                izlacneFakture.add(f);
            }
        }
        return izlacneFakture;
    }

    public Optional<Faktura> findById(Long id){
        return fakturaRepository.findByFakturaId(id);
    }

    public Faktura save(Faktura faktura){
        return fakturaRepository.save(faktura);
    }

    @Override
    public void deleteById(Long id) {
        fakturaRepository.deleteById(id);
    }
}
