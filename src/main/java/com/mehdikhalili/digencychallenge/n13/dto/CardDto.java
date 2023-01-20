package com.mehdikhalili.digencychallenge.n13.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardDto {
    private Long id;
    private String nom;
    private String nomAr;
    private String prenom;
    private String prenomAr;
    private String cin;
    private String profession;
    private Date dateNaissance;
    @JsonIgnore
    private String dateNaissanceStr;
    private String type;
    private String image;

    public CardDto(String nom, String nomAr, String prenom, String prenomAr, String cin, String profession,
                   Date dateNaissance, String type, String image) {
        this.nom = nom;
        this.nomAr = nomAr;
        this.prenom = prenom;
        this.prenomAr = prenomAr;
        this.cin = cin;
        this.profession = profession;
        this.dateNaissance = dateNaissance;
        this.type = type;
        this.image = image;
    }

    public void setDateNaissanceStr() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.dateNaissanceStr = dateFormat.format(this.dateNaissance);
    }
}
