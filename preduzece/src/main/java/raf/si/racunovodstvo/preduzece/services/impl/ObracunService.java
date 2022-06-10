package raf.si.racunovodstvo.preduzece.services.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import raf.si.racunovodstvo.preduzece.feign.KnjizenjeFeignClient;
import raf.si.racunovodstvo.preduzece.model.Knjizenje;
import raf.si.racunovodstvo.preduzece.model.Obracun;
import raf.si.racunovodstvo.preduzece.model.ObracunZaposleni;
import raf.si.racunovodstvo.preduzece.repositories.ObracunRepository;
import raf.si.racunovodstvo.preduzece.services.IObracunService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ObracunService implements IObracunService {

    private final ObracunRepository obracunRepository;
    private final KnjizenjeFeignClient knjizenjeFeignClient;

    public ObracunService(ObracunRepository obracunRepository, KnjizenjeFeignClient knjizenjeFeignClient){
        this.obracunRepository = obracunRepository;
        this.knjizenjeFeignClient =knjizenjeFeignClient;
    }

    @Override
    public <S extends Obracun> S save(S var1) {
        return obracunRepository.save(var1);
    }

    @Override
    public Optional<Obracun> findById(Long var1) {
        return obracunRepository.findById(var1);
    }

    @Override
    public List<Obracun> findAll() {
        return obracunRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        obracunRepository.deleteById(var1);
    }

    private void proknjiziObracunZaposleni(ObracunZaposleni obracunZaposleni, String token){
        Knjizenje knjizenje = new Knjizenje();

        ResponseEntity<?> response = knjizenjeFeignClient.createDnevnikKnjizenja(knjizenje, token);

        if(response.getStatusCode().isError()){
            throw new RuntimeException(String.format("Knjizenje ObracunZaposleni:%s nije uspoelo", obracunZaposleni.getObracunZaposleniId().toString()));
        }
    }

    public void  proknjizi(Long var1, String token){
        Optional<Obracun> optionalObracun = obracunRepository.findById(var1);

        if(optionalObracun.isEmpty()){
            throw new RuntimeException(String.format("Obracun sa id-jem %s ne postoji",var1));
        }

        Obracun obracun = optionalObracun.get();
        List<ObracunZaposleni> obracunZaposleniList = obracun.getObracunZaposleniList();


        List<ObracunZaposleni> proknjizeniObracunZaposleni = obracunZaposleniList.stream().filter(obracunZaposleni -> obracunZaposleni.getProknjizeno()).collect(Collectors.toList());
        if(!proknjizeniObracunZaposleni.isEmpty()){
            var ids = proknjizeniObracunZaposleni.stream().map(obracunZaposleni -> obracunZaposleni.getObracunZaposleniId().toString()).collect(Collectors.toList());
            throw new RuntimeException(String.format("Vec proknjizeni obracun Zaposleni: %s", String.join(",",ids)));
        }

        for (var obracunZaposleni:obracunZaposleniList) {
            proknjiziObracunZaposleni(obracunZaposleni, token);
        }

    }


    public void updateObracunZaradeNaziv(Long obracunZaradeId, String naziv) {
        Optional<Obracun> obracunOptional = obracunRepository.findById(obracunZaradeId);
        if (obracunOptional.isPresent()) {
            Obracun obracun = obracunOptional.get();
            obracun.setNaziv(naziv);
            save(obracun);
        }
    }
}
