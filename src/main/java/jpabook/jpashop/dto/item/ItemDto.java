package jpabook.jpashop.dto.item;

import jpabook.jpashop.domain.item.Item;
import lombok.Data;

@Data
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    public ItemDto(Item item){
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();

    }
}
