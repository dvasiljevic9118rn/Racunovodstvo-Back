package raf.si.racunovodstvo.knjizenje.model;

import lombok.Getter;
import lombok.Setter;
import raf.si.racunovodstvo.knjizenje.model.enums.TipFakture;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "fakturaId")
public class Faktura extends Dokument {

    @Column(nullable = false, unique = true)
    private String brojFakture;
    @Column(nullable = false)
    private Date datumIzdavanja;
    @Column(nullable = false)
    private Date rokZaPlacanje;
    @Column
    private Date datumPlacanja;
    @Column(nullable = false)
    private Double prodajnaVrednost;
    @Column
    private Double rabatProcenat;
    @Column
    private Double rabat;
    @Column(nullable = false)
    private Double porezProcenat;
    @Column
    private Double porez;
    @Column
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

}