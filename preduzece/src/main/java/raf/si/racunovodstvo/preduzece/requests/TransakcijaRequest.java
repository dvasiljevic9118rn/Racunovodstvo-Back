package raf.si.racunovodstvo.preduzece.requests;

import lombok.Data;
import raf.si.racunovodstvo.preduzece.model.enums.TipDokumenta;
import raf.si.racunovodstvo.preduzece.model.enums.TipTransakcije;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class TransakcijaRequest {

    private Long dokumentId;
    @NotNull
    private String brojDokumenta;
    @NotNull
    private TipDokumenta tipDokumenta;
    @NotNull
    private String brojTransakcije;
    @NotNull
    private Date datumTransakcije;
    @NotNull
    private TipTransakcije tipTransakcije;
    @NotNull
    private Double iznos;
    private String sadrzaj;
    private String komentar;
    @NotNull
    private Long sifraTransakcije;
    private Long preduzeceId;
}