package com.mehdikhalili.digencychallenge.n13.service;

import com.mehdikhalili.digencychallenge.n13.dto.CardDto;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;

public interface CardService {

    CardDto get(Long id);
    Page<CardDto> getByPage(int pageNumber);
    CardDto add(CardDto dto);
    CardDto edit(CardDto dto);
    void delete(Long id);
    ResponseEntity<Resource> exportReport(Long id) throws FileNotFoundException, JRException;
}
