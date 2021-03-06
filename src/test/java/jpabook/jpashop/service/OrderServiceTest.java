package jpabook.jpashop.service;

import jpabook.jpashop.criteria.OrderSearch;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;
 // 주문 정상 테스트
    // 재고 차감c

    private Member makeMember(){
        Member member = new Member();
        member.setName("kim");
        Address address = new Address("city", "street", "zipcode");
        member.setAddress(address);
        memberService.join(member);

        return member;
    }

    private Item makeItem(){
        Item item = new Album();
        item.setName("album");
        item.setStockQuantity(15);
        item.setPrice(1000);
        itemService.saveItem(item);

        return item;

    }
    @Test
    public void 상품주문(){
        Member member = this.makeMember();
        Item album = this.makeItem();

        Long orderId = orderService.order(member.getId(), album.getId(), 10);
        Order order = orderService.findOne(orderId);

        assertEquals("주문의 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        assertEquals("주문종류 일치", 1, order.getOrderItems().size());
        assertEquals("총 주문 금액 일치", 10000, order.getTotalPrice());
        assertEquals("재고수량 감소", 5, album.getStockQuantity());


    }

    // 음수제고 테스트
    @Test(expected= NotEnoughStockException.class)
    public void 음수재고(){
        Member member = this.makeMember();
        Item album = this.makeItem();

        Long orderId = orderService.order(member.getId(), album.getId(), 30);
        Order order = orderService.findOne(orderId);

        fail("음수재고 테스트 실패.");
    }

    @Test
    public void 주문취소(){
        Member member = this.makeMember();
        Item album = this.makeItem();

        Long orderId = orderService.order(member.getId(), album.getId(), 10);
        Order order = orderService.findOne(orderId);
        order.cancel();

        assertEquals("주문의 상태는 CANCEL", OrderStatus.CANCEL, order.getStatus());
        assertEquals("재고수량 증가", 15, album.getStockQuantity());
    }

    @Test
    public void 검색_STATUS(){
        // orderStatus만
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        Member member = this.makeMember();
        Item album = this.makeItem();

        Long orderId = orderService.order(member.getId(), album.getId(), 10);
        Order order = orderService.findOne(orderId);
        List<Order> findByStatus = orderService.findByCriteira(orderSearch);
        assertEquals("주문상태로만 조회", 1, findByStatus.size());
    }

        @Test
    public void 검색_Name(){
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("ki");

        Member member = this.makeMember();
        Item album = this.makeItem();

        Long orderId = orderService.order(member.getId(), album.getId(), 10);
        Order order = orderService.findOne(orderId);
        List<Order> findByStatus = orderService.findByCriteira(orderSearch);
        assertEquals("이름으로만 조회", 1, findByStatus.size());
    }

        @Test
    public void 검색_ALL(){
        // orderStatus만
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orderSearch.setMemberName("ki");

        Member member = this.makeMember();
        Item album = this.makeItem();

        Long orderId = orderService.order(member.getId(), album.getId(), 10);
        Order order = orderService.findOne(orderId);
        List<Order> findByStatus = orderService.findByCriteira(orderSearch);
        assertEquals("전체조건 조회", 1, findByStatus.size());
    }

        @Test
    public void 검색_NONE(){
        // orderStatus만
        OrderSearch orderSearch = new OrderSearch();

        Member member = this.makeMember();
        Item album = this.makeItem();

        Long orderId = orderService.order(member.getId(), album.getId(), 10);
        Order order = orderService.findOne(orderId);
        List<Order> findByStatus = orderService.findByCriteira(orderSearch);
        assertEquals("조건 미입력 조회", 1, findByStatus.size());
    }
}