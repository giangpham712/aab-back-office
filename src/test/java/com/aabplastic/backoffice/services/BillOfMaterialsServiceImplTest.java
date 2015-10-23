package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.BillOfMaterials;
import com.aabplastic.backoffice.models.BillOfMaterialsItem;
import com.aabplastic.backoffice.repositories.BillOfMaterialsRepository;
import com.google.common.collect.Iterators;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BillOfMaterialsServiceImplTest extends TestCase {

    @Mock
    private BillOfMaterialsRepository billOfMaterialsRepository;

    @InjectMocks
    private BillOfMaterialsServiceImpl billOfMaterialsService;

    private List<BillOfMaterials> listBoms;

    private List<BillOfMaterialsItem> listBomItems;

    @Before
    public void setUp() {

        BillOfMaterials bom1 = new BillOfMaterials("BOM 1", "BOM 1 description");
        BillOfMaterials bom2 = new BillOfMaterials("BOM 2", "BOM 2 description");
        BillOfMaterials bom3 = new BillOfMaterials("BOM 3", "BOM 3 description");

        listBoms = new ArrayList<>();
        listBoms.add(bom1);
        listBoms.add(bom2);
        listBoms.add(bom3);

        listBomItems = new ArrayList<>();

        BillOfMaterialsItem bomItem1 = new BillOfMaterialsItem(1, 160);
        bomItem1.setId(1);
        BillOfMaterialsItem bomItem2 = new BillOfMaterialsItem(2, 250);
        bomItem2.setId(2);
        BillOfMaterialsItem bomItem3 = new BillOfMaterialsItem(3, 370);
        bomItem3.setId(3);
        BillOfMaterialsItem bomItem4 = new BillOfMaterialsItem(4, 410);
        bomItem4.setId(4);
        BillOfMaterialsItem bomItem5 = new BillOfMaterialsItem(5, 500);
        bomItem5.setId(5);

        listBomItems.add(bomItem1);
        listBomItems.add(bomItem2);
        listBomItems.add(bomItem3);
        listBomItems.add(bomItem4);
        listBomItems.add(bomItem5);
    }

    @Test
    public void testListBOMs() throws Exception {
        when(billOfMaterialsRepository.findAll()).thenReturn(listBoms);

        Iterable<BillOfMaterials> actual = billOfMaterialsService.listBOMs();

        assertEquals(listBoms.size(), Iterators.size(actual.iterator()));

        int index = 0;
        for (BillOfMaterials bom : actual) {
            assertEquals(listBoms.get(index).getName(), bom.getName());
            assertEquals(listBoms.get(index).getDescription(), bom.getDescription());
            index++;
        }
    }

    public void testGetBOMById() throws Exception {

    }

    public void testCreate() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

        BillOfMaterials bom1 = new BillOfMaterials("BOM 1", "BOM 1 description");

        List bomItems = new ArrayList<>();
        bomItems.add(listBomItems.get(1));
        bomItems.add(listBomItems.get(2));

        List spy = spy(bomItems);
        bom1.setItems(spy);

        BillOfMaterials bom1Updated = new BillOfMaterials("BOM 1 updated", "BOM 1 updated description");

        bom1Updated.getItems().add(listBomItems.get(0));
        bom1Updated.getItems().add(listBomItems.get(2));
        bom1Updated.getItems().add(listBomItems.get(3));

        when(billOfMaterialsRepository.findOne(1L)).thenReturn(bom1);

        ArgumentCaptor<BillOfMaterials> argument = ArgumentCaptor.forClass(BillOfMaterials.class);

        BillOfMaterials actual = billOfMaterialsService.update(1L, bom1Updated);

        verify(billOfMaterialsRepository).save(argument.capture());
        verify(spy, times(1)).remove(any(BillOfMaterialsItem.class));
        verify(spy, times(2)).add(any(BillOfMaterialsItem.class));

        assertEquals(argument.getValue().getItems().size(), bom1Updated.getItems().size());

    }
}