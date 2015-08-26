package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Item;
import com.aabplastic.backoffice.models.dto.ItemDto;
import com.aabplastic.backoffice.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutocompleteServiceImpl implements AutocompleteService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<ItemDto> getItems(String q) {

        List<Object[]> items = itemRepository.searchByDisplayName(q);

        return items.stream().map(i -> {
            ItemDto itemDto = new ItemDto();
            itemDto.setId(((BigInteger) i[0]).longValue());
            itemDto.setDisplayName((String) i[1]);
            return itemDto;
        }).collect(Collectors.toList());
    }
}
