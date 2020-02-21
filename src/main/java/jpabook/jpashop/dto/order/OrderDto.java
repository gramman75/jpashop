package jpabook.jpashop.dto.order;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.dto.Result;
import jpabook.jpashop.dto.orderItemDto.OrderItemDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Long id;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
//    private List<OrderItemDto> orderItems;
    private Result<OrderItemDto> orderItems;

    public OrderDto(Order order){
        this.id = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();

        List<OrderItemDto> orderItemDto = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());

        this.orderItems = new Result(orderItemDto.size(), orderItemDto);
    }
}
