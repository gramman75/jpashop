package jpabook.jpashop.api;

import jpabook.jpashop.criteria.OrderSearch;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.dto.Result;
import jpabook.jpashop.dto.order.OrderDto;
import jpabook.jpashop.dto.order.OrderQueryDto;
import jpabook.jpashop.dto.orderItemDto.OrderItemDto;
import jpabook.jpashop.dto.orderItemDto.OrderItemQueryDto;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    @Autowired
    private final OrderService orderService;

    @GetMapping("/api/v2/orders")
    public Result ordersV2(){
        List<Order> orders = orderService.findByCriteira(new OrderSearch());
        List<OrderDto> ordersDto = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());

        return new Result(ordersDto.size(), ordersDto);
    }

    @GetMapping("/api/v3/orders")
    public Result ordersV3() throws Exception{
        List<Order> orders = orderService.findWithItem();
        List<OrderDto> ordersDto = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());

        return new Result(ordersDto.size(), ordersDto);
    }

    @GetMapping("/api/v3.1/orders")
    public Result ordersV3_1(@RequestParam(value = "offset", defaultValue = "0") int offset,
                           @RequestParam(value = "limit", defaultValue = "100") int limit) throws Exception{
        List<Order> orders = orderService.findWithMemberDelivery(offset, limit);
        List<OrderDto> ordersDto = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());

        return new Result(ordersDto.size(), ordersDto);
    }

    @GetMapping("/api/v4/orders")
    public Result orderV4(){
        List<OrderQueryDto> orders = orderService.findOrderQueryDto();

        orders.forEach( orderDto ->{
            List<OrderItemQueryDto> orderItemDto= orderService.findOrderItemQueryDto(orderDto.getId());
            orderDto.setOrderItems(orderItemDto);
        });

        return new Result(orders.size(), orders);
    }

    @GetMapping("/api/v5/orders")
    public Result orderV5(){
        List<OrderQueryDto> orders = orderService.findOrderQueryDto();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderService.findOrderItemMap(orders);

        orders.stream().forEach(order -> order.setOrderItems(orderItemMap.get(order.getId())));

        return new Result(orders.size(), orders);
    }

}
