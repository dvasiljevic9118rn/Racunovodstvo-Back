package raf.si.racunovodstvo.preduzece.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class Knjizenje {

    private Long knjizenjeId;
    private String brojNaloga;
    private Date datumKnjizenja;
    private Dokument dokument;
    private List<Konto> konto;
    private String komentar;
}
