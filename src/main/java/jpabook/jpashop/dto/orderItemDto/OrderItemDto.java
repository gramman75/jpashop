package jpabook.jpashop.dto.orderItemDto;

import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.dto.item.ItemDto;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private ItemDto item;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem){
        this.id = orderItem.getId();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
        this.item = new ItemDto(orderItem.getItem());
    }
}
