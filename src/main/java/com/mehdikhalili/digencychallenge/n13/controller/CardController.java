package com.mehdikhalili.digencychallenge.n13.controller;

import com.mehdikhalili.digencychallenge.n13.dto.CardDto;
import com.mehdikhalili.digencychallenge.n13.service.CardService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService service;

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Page<CardDto>> getByPage(@PathVariable int pageNumber) {
        return ResponseEntity.ok(service.getByPage(pageNumber));
    }

    @PostMapping("/add")
    public ResponseEntity<CardDto> add(@RequestBody CardDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PutMapping("/edit")
    public ResponseEntity<CardDto> edit(@RequestBody CardDto dto) {
        return ResponseEntity.ok(service.edit(dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/export")
    public ResponseEntity<Resource> exportPDF(@PathVariable Long id) throws JRException, FileNotFoundException {
        return service.exportReport(id);
    }
}
