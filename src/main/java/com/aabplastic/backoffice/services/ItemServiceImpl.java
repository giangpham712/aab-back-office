package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.BillOfMaterials;
import com.aabplastic.backoffice.models.Item;
import com.aabplastic.backoffice.models.ItemAttribute;
import com.aabplastic.backoffice.models.dto.BillOfMaterialsDto;
import com.aabplastic.backoffice.models.dto.ItemAttributeDto;
import com.aabplastic.backoffice.models.dto.ItemDto;
import com.aabplastic.backoffice.repositories.BillOfMaterialsRepository;
import com.aabplastic.backoffice.repositories.ItemRepository;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BillOfMaterialsRepository billOfMaterialsRepository;

    @Override
    public ItemDto getItemById(long id) {
        Item item = itemRepository.findOne(id);
        if (item == null) {
            return null;
        }

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(Item.class, ItemDto.class)
                .byDefault().register();

        return mapperFactory.getMapperFacade().map(item, ItemDto.class);
    }

    //TODO Implement caching
    public Iterable<ItemDto> listItemsByType(ItemType type, String[] excludes) {

        List<Item> items = itemRepository.findByType(type);
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        ClassMapBuilder builder = mapperFactory.classMap(Item.class, ItemDto.class);

        for (String exclude : excludes) {
            builder.exclude(exclude);
        }

        builder.byDefault().register();

        return mapperFactory.getMapperFacade().mapAsList(items, ItemDto.class);
    }

    @Override
    public ItemDto create(ItemDto itemDto) {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        Item item = mapperFactory.getMapperFacade().map(itemDto, Item.class);
        Date now = new Date();

        if (item.getType() == ItemType.INVENTORY_ITEM) {

        }

        item.setCreatedAt(now);
        item.setUpdatedAt(now);
        item.setActive(true);

        final Item finalItem = item;
        item.getAttributes().stream()
                .filter(a -> a.getType() == finalItem.getType()).forEach(a -> a.setItem(finalItem));

        item = itemRepository.save(item);

        return mapperFactory.getMapperFacade().map(item, ItemDto.class);
    }

    @Override
    public ItemDto update(long id, ItemDto itemDto) {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        Item item = itemRepository.findOne(id);

        item.setItemNumber(itemDto.getItemNumber());
        item.setDisplayName(itemDto.getDisplayName());
        item.setDescription(itemDto.getDescription());
        item.setTrackInventory(itemDto.isTrackInventory());
        item.setQuantityOnHand(itemDto.getQuantityOnHand());

        Map<String, ItemAttributeDto> putAttributes = itemDto.getAttributes().stream().collect(Collectors.toMap(ItemAttributeDto::getAttributeKey, x -> x));

        Map<String, ItemAttribute> savedAttributes = item.getAttributes().stream().collect(Collectors.toMap(ItemAttribute::getAttributeKey, x -> x));


        for (String key : putAttributes.keySet()) {
            if (savedAttributes.get(key) == null) {
                // Add new attribute

                ItemAttribute newAttribute = mapperFactory.getMapperFacade().map(putAttributes.get(key), ItemAttribute.class);
                newAttribute.setItem(item);
                item.getAttributes().add(newAttribute);
            } else {
                // Update existing attribute
                savedAttributes.get(key).setAttributeValue(putAttributes.get(key).getAttributeValue());
            }
        }

        Date now = new Date();

        item.setUpdatedAt(now);

        item = itemRepository.save(item);

        return mapperFactory.getMapperFacade().map(item, ItemDto.class);
    }

    @Override
    public BillOfMaterialsDto getBOMById(long id) {

        BillOfMaterials bom = billOfMaterialsRepository.findOne(id);
        if (bom == null) {
            return null;
        }

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(BillOfMaterials.class, BillOfMaterialsDto.class)
                .exclude("item")
                .byDefault().register();

        return mapperFactory.getMapperFacade().map(bom, BillOfMaterialsDto.class);
    }

    @Override
    public BillOfMaterialsDto createBOM(BillOfMaterialsDto billOfMaterialsDto) {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        BillOfMaterials bom = mapperFactory.getMapperFacade().map(billOfMaterialsDto, BillOfMaterials.class);
        Date now = new Date();

        bom.setCreatedAt(now);
        bom.setUpdatedAt(now);

        bom.getItems().stream().forEach(x -> x.setBill(bom));

        billOfMaterialsRepository.save(bom);

        return mapperFactory.getMapperFacade().map(bom, BillOfMaterialsDto.class);
    }

    @Override
    public Iterable<BillOfMaterialsDto> listBOMsForItem(long id) {

        List<BillOfMaterials> boms = billOfMaterialsRepository.findByItemId(id);

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(BillOfMaterials.class, BillOfMaterialsDto.class)
                .exclude("item")
                .byDefault().register();

        return mapperFactory.getMapperFacade().mapAsList(boms, BillOfMaterialsDto.class);

    }

    @Override
    public void deleteBOM(long id) {

        billOfMaterialsRepository.delete(id);
    }

}
