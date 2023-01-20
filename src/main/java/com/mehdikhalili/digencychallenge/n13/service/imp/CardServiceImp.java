package com.mehdikhalili.digencychallenge.n13.service.imp;

import com.mehdikhalili.digencychallenge.n13.dto.CardDto;
import com.mehdikhalili.digencychallenge.n13.exception.NotFoundException;
import com.mehdikhalili.digencychallenge.n13.mapping.CardMapper;
import com.mehdikhalili.digencychallenge.n13.repository.CardRepository;
import com.mehdikhalili.digencychallenge.n13.service.CardService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CardServiceImp implements CardService {

    private final CardRepository repository;
    private final CardMapper mapper;

    @Override
    public CardDto get(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Page<CardDto> getByPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 8, Sort.by("id").ascending());
        return mapper.toDtoPage(repository.findAll(pageable));
    }

    @Override
    public CardDto add(CardDto dto) {
        if (dto.getId() != null) dto.setId(null);
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public CardDto edit(CardDto dto) {
        if (!repository.existsById(dto.getId())) throw new NotFoundException();
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException();
        repository.deleteById(id);
    }

    @Override
    public ResponseEntity<Resource> exportReport(Long id) throws FileNotFoundException, JRException {
        CardDto card = this.get(id);
        card.setDateNaissanceStr();
        List<CardDto> list = new ArrayList<>();
        list.add(card);
        File file = ResourceUtils.getFile("classpath:reports/carte_professionnelle.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

        JRDesignStyle jrDesignStyle = new JRDesignStyle();
        /*Set the Encoding to UTF-8 for pdf and embed font to arial*/
        jrDesignStyle.setDefault(true);
        jrDesignStyle.setFontName("Arial");
        jrDesignStyle.setPdfEncoding("Identity-H");
        jrDesignStyle.setPdfEmbedded(true);
        jasperPrint.addStyle(jrDesignStyle);

        byte[] report = JasperExportManager.exportReportToPdf(jasperPrint);
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename("Carte Professionnelle - "+ card.getNom() +" "+ card.getPrenom() +".pdf")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .contentLength(report.length)
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(new ByteArrayResource(report));
    }

}
