package jpabook.jpashop.service;

import jpabook.jpashop.criteria.OrderSearch;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.order.OrderQueryDto;
import jpabook.jpashop.dto.orderItemDto.OrderItemDto;
import jpabook.jpashop.dto.orderItemDto.OrderItemQueryDto;
import jpabook.jpashop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class OrderService {

    @Autowired
    OrderRepositoryInterface orderRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ItemRepository itemRepository;

    @Transactional(readOnly = false)
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem =  OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId){
        orderRepository.findOrder(orderId).cancel();
    }

    public Order findOne(Long id){
        return orderRepository.findOrder(id);
    }

    public List<Order> findByCriteira(OrderSearch orderSearch){
        return orderRepository.findByCriteria(orderSearch);
    }

    public List<Order> findWithMemberDelivery() {
        return orderRepository.findWithMemberDelivery();
    }


    public List<Order> findWithMemberDelivery(int offset, int limit) {
        return orderRepository.findWithMemberDelivery(offset, limit);
    }

    public List<Order> findWithItem() throws Exception {
        return orderRepository.findWithItem();
    }

    public List<OrderQueryDto> findOrderQueryDto() {
        return orderRepository.findOrderQueryDto();
    }

    public List<OrderItemQueryDto> findOrderItemQueryDto(Long id) {
        return orderRepository.findOrderItemQueryDto(id);
    }

    public Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<OrderQueryDto> orders) {
        return orderRepository.findOrderItemMap(orders);
    }
}
