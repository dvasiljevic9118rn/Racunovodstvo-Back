package rs.raf.demo.model;

import lombok.*;
import rs.raf.demo.model.enums.TipFakture;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "fakturaId")
public class Faktura extends Dokument{

    @Column(nullable = false)
    private String brojFakture;
    @Column(nullable = false)
    private Date datumIzdavanja;
    @Column(nullable = false)
    private Date rokZaPlacanje;
    @Column(nullable = false)
    private Date datumPlacanja;
    @Column(nullable = false)
    private Double prodajnaVrednost;
    @Column
    private Double rabatProcenat;
    @Column
    private Double rabat;
    @Column(nullable = false)
    private Double porezProcenat;
    @Column(nullable = false)
    private Double porez;
    @Column(nullable = false)
    private Double iznos;
    @Column(nullable = false)
    private String valuta;
    @Column(nullable = false)
    private Double kurs;
    @Column(nullable = false)
    private Double naplata;
    @Column
    private String komentar;
    @Column
    @Enumerated(EnumType.STRING)
    private TipFakture tipFakture;
    @ManyToOne
    @JoinColumn(name = "preduzeceId")
    private Preduzece preduzece;
}