package jpabook.jpashop.api;

import jpabook.jpashop.criteria.OrderSearch;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.dto.Result;
import jpabook.jpashop.dto.order.SimpleOrderDto;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;

    @GetMapping("/api/v2/simpleorders")
    public Result ordersV2(){
        List<Order> orders = orderService.findByCriteira(new OrderSearch());

        List<SimpleOrderDto> collect = orders.stream()
                .map(order -> new SimpleOrderDto(order))
                .collect(Collectors.toList());

        return new Result(collect.size(),collect);
    }

    @GetMapping("/api/v3/simpleorders")
    public Result orderV3(){
        List<Order> orders = orderService.findWithMemberDelivery();

        List<SimpleOrderDto> collect = orders.stream()
                .map(order -> new SimpleOrderDto(order))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);


    }

}
