package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.models.Item;
import com.aabplastic.backoffice.models.dto.ItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByType(ItemType type);

    @Query(value = "SELECT i.id, i.display_name as displayName FROM items i WHERE i.display_name LIKE %:q%", nativeQuery = true)
    List<Object[]> searchByDisplayName(@Param("q") String q);
}
