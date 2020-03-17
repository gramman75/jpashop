package jpabook.jpashop.dto.orderItemDto;

import jpabook.jpashop.domain.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemQueryDto {
    private Long id;
    private String name;
    private int price;
    private int orderQuantity;

    public OrderItemQueryDto(OrderItem orderItem){
        this.id = orderItem.getId();
        this.name = orderItem.getItem().getName();
        this.price = orderItem.getOrderPrice();
        this.orderQuantity = orderItem.getCount();
    }
}
