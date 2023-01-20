package com.mehdikhalili.digencychallenge.n13.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String nomAr;
    private String prenom;
    private String prenomAr;
    @Column(unique = true)
    private String cin;
    private String profession;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private String type;
    private String image;
}
