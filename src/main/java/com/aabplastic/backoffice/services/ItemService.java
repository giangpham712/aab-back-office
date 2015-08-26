package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.models.dto.BillOfMaterialsDto;
import com.aabplastic.backoffice.models.dto.ItemDto;

public interface ItemService {

    ItemDto getItemById(long id);

    Iterable<ItemDto> listItemsByType(ItemType itemType, String[] excludes);

    ItemDto create(ItemDto itemDto);

    ItemDto update(long id, ItemDto itemDto);

    BillOfMaterialsDto getBOMById(long id);

    BillOfMaterialsDto createBOM(BillOfMaterialsDto billOfMaterialsDto);

    Iterable<BillOfMaterialsDto> listBOMsForItem(long id);

    void deleteBOM(long id);
}
