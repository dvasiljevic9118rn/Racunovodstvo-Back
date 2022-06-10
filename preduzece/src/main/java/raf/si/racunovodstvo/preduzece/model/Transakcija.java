package raf.si.racunovodstvo.preduzece.model;

import lombok.Getter;
import lombok.Setter;
import raf.si.racunovodstvo.preduzece.model.enums.TipTransakcije;

import java.util.Date;

@Getter
@Setter
public class Transakcija {

    private String brojTransakcije;
    private Date datumTransakcije;
    private TipTransakcije tipTransakcije;
    private Double iznos;
    private String sadrzaj;
    private String komentar;
    private Long sifraTransakcije;
}
