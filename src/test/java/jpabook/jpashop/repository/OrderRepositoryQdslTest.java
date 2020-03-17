package jpabook.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.dto.order.OrderQueryDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("query")
public class OrderRepositoryQdslTest {
    @Autowired
    OrderRepositoryQdsl orderRepository;

    @Autowired
    EntityManager em;

    JPAQueryFactory query;

    @Before
    public void setup(){
        query = new JPAQueryFactory(em);
    }

    @Test
    public void fetchJoin(){
        List<Order> orders = orderRepository.findWithMemberDelivery();

    }
    @Test
    public void findOrderQueryDto(){
        List<OrderQueryDto> orders = orderRepository.findOrderQueryDto();
    }


}