package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.dto.ItemDto;

import java.util.List;

public interface AutocompleteService {
    List<ItemDto> getItems(String q);
}
