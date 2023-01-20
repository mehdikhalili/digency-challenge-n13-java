package com.mehdikhalili.digencychallenge.n13.repository;

import com.mehdikhalili.digencychallenge.n13.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
