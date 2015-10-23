package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.BillOfMaterials;
import com.aabplastic.backoffice.models.BillOfMaterialsItem;
import com.aabplastic.backoffice.repositories.BillOfMaterialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BillOfMaterialsServiceImpl implements BillOfMaterialsService {

    @Autowired
    private BillOfMaterialsRepository bomRepository;

    @Override
    public Iterable<BillOfMaterials> listBOMs() {
        Iterable<BillOfMaterials> boms = bomRepository.findAll();
        return boms;
    }

    @Override
    public BillOfMaterials getBOMById(long id) {
        BillOfMaterials bom = bomRepository.findOne(id);
        return bom;
    }

    @Override
    public BillOfMaterials create(BillOfMaterials bom) {
        Date now = new Date();

        bom.setCreatedAt(now);
        bom.setUpdatedAt(now);

        bom.getItems().stream().forEach(x -> {
            x.setBill(bom);
        });

        BillOfMaterials created = bomRepository.save(bom);
        return created;
    }

    @Override
    public BillOfMaterials update(long id, BillOfMaterials bom) {
        BillOfMaterials updated = bomRepository.findOne(id);

        if (updated == null) {
            throw new ResourceNotFoundException(String.format("Bill of materials with id %d cannot be found", id));
        }

        updated.setName(bom.getName());
        updated.setDescription(bom.getDescription());

        List<BillOfMaterialsItem> putBOMItems = bom.getItems();
        Map<Long, BillOfMaterialsItem> map1 = putBOMItems.stream().filter(x -> x.getId() != 0).collect(Collectors.toMap(BillOfMaterialsItem::getId, Function.identity()));

        List<BillOfMaterialsItem> bomItems = updated.getItems();
        Map<Long, BillOfMaterialsItem> map2 = bomItems.stream().collect(Collectors.toMap(BillOfMaterialsItem::getId, Function.identity()));

        Set<Long> ids = new HashSet<>();

        // Remove items
        bomItems.stream()
                .filter(x -> !map1.containsKey(x.getId()))
                .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                .forEach(y -> {
                    bomItems.remove(y);
                    ids.add(y.getId());
                });

        // Add items
        final BillOfMaterials finalUpdated = updated;
        putBOMItems.stream().filter(x -> !map2.containsKey(x.getId())).forEach(y -> {
            bomItems.add(y);
            y.setBill(finalUpdated);

            ids.add(y.getId());
        });

        // Update items
        bomItems.stream()
                .filter(x -> !ids.contains(x.getId()))
                .forEach(y -> {
                    BillOfMaterialsItem putBOMItem = map1.get(y.getId());

                    y.setMaterialId(putBOMItem.getMaterialId());
                    y.setQuantity(putBOMItem.getQuantity());

                });

        Date now = new Date();
        updated.setUpdatedAt(now);

        updated = bomRepository.save(finalUpdated);
        return updated;
    }
}
