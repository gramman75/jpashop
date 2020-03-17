package jpabook.jpashop.dto.order;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.dto.orderItemDto.OrderItemQueryDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderQueryDto {
    private Long id;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private String type;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Order order){
        this.type = "OrderQuueryDtoType";
        this.id = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = getAddress();
    }
}
