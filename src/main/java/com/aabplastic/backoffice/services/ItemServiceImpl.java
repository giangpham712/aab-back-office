package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.*;
import com.aabplastic.backoffice.models.dto.BillOfMaterialsDto;
import com.aabplastic.backoffice.models.dto.BillOfMaterialsItemDto;
import com.aabplastic.backoffice.models.dto.ItemAttributeDto;
import com.aabplastic.backoffice.models.dto.ItemDto;
import com.aabplastic.backoffice.repositories.BillOfMaterialsRepository;
import com.aabplastic.backoffice.repositories.ItemRepository;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {


    @Override
    public ItemDto getItemById(long id) {
        return null;
    }

    @Override
    public Iterable<ItemDto> listItemsByType(ItemType itemType, String[] excludes) {
        return null;
    }

    @Override
    public ItemDto create(ItemDto itemDto) {
        return null;
    }

    @Override
    public ItemDto update(long id, ItemDto itemDto) {
        return null;
    }

    @Override
    public BillOfMaterialsDto getBOMById(long id) {
        return null;
    }

    @Override
    public BillOfMaterialsDto createBOM(BillOfMaterialsDto billOfMaterialsDto) {
        return null;
    }

    @Override
    public Iterable<BillOfMaterialsDto> listBOMsForItem(long id) {
        return null;
    }

    @Override
    public void deleteBOM(long id) {

    }

    @Override
    public BillOfMaterialsDto updateBOM(long id, BillOfMaterialsDto billOfMaterialsDto) {
        return null;
    }
}
