package com.mehdikhalili.digencychallenge.n13.mapping;

import com.mehdikhalili.digencychallenge.n13.dto.CardDto;
import com.mehdikhalili.digencychallenge.n13.model.Card;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardMapper {

    private final DozerBeanMapper mapper;

    public Card toEntity(CardDto dto) {
        return mapper.map(dto, Card.class);
    }

    public CardDto toDto(Card entity) {
        return mapper.map(entity, CardDto.class);
    }

    public Page<CardDto> toDtoPage(Page<Card> page) {
        return page.map(this::toDto);
    }
}
