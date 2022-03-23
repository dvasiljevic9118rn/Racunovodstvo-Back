package rs.raf.demo.services;

import org.springframework.data.jpa.domain.Specification;
import rs.raf.demo.model.Faktura;

import java.util.List;

public interface IFakturaService extends IService<Faktura, Long>{


    List<Faktura> findAll(Specification<Faktura> spec);

    List<Faktura> findUlazneFakture();

    List<Faktura> findIzlazneFakture();
}
