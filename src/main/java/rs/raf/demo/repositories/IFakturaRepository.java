package rs.raf.demo.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.Faktura;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFakturaRepository extends JpaRepository<Faktura, Long> {

    List<Faktura> findAll();

    List<Faktura> findAll(Specification<Faktura> spec);

    Optional<Faktura> findByFakturaId(Long fakturaId);

}
