package jpabook.jpashop.repository;

import jpabook.jpashop.criteria.OrderSearch;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.dto.order.OrderQueryDto;
import jpabook.jpashop.dto.orderItemDto.OrderItemQueryDto;

import java.util.List;
import java.util.Map;

public interface OrderRepositoryInterface {
    void save(Order order);

    Order findOrder(Long id);

    List<Order> findByCriteria(OrderSearch orderSearch);

    List<Order> findByQdsl(OrderSearch orderSearch);

    List<Order> findWithMemberDelivery();

    List<Order> findWithMemberDelivery(int offset, int limit);

    List<Order> findWithItem() throws Exception;

    List<OrderQueryDto> findOrderQueryDto();


    List<OrderItemQueryDto> findOrderItemQueryDto(Long id);

    Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<OrderQueryDto> orders);
}
