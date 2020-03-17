package jpabook.jpashop.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.criteria.OrderSearch;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.dto.order.OrderQueryDto;
import jpabook.jpashop.dto.orderItemDto.OrderItemQueryDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.list;

@Repository
@Primary
public class OrderRepositoryQdsl implements OrderRepositoryInterface {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public OrderRepositoryQdsl(EntityManager em){
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public void save(Order order){
        em.persist(order);
    }

    public Order findOrder(Long id){
        return query
                .selectFrom(QOrder.order)
                .where(QOrder.order.id.eq(id))
                .fetchOne();

    }

    // search
    public List<Order> findByCriteria(OrderSearch orderSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);

        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        if (orderSearch.getOrderStatus() != null){
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        if (orderSearch.getMemberName() != null){
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName()+"%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);

        return query.getResultList();
    }

    public List<Order> findByQdsl(OrderSearch orderSearch){
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPAQueryFactory query = new JPAQueryFactory(em);
        return query.select(order)
                .from(order)
                .join(order.member, member)
                .where(
                        orderSearch.getOrderStatus() == null ? null : QOrder.order.status.eq(orderSearch.getOrderStatus()),
                        orderSearch.getMemberName() == null ? null : QMember.member.name.like("%" + orderSearch.getMemberName()+"%")
                )
                .fetch();
    }

    public List<Order> findWithMemberDelivery() {
        return query
                .selectFrom(QOrder.order)
                .innerJoin(QOrder.order.member, QMember.member).fetchJoin()
                .innerJoin(QOrder.order.delivery, QDelivery.delivery).fetchJoin()
                .fetch();
    }


    public List<Order> findWithMemberDelivery(int offset, int limit) {
         List<Order> orders = query
                .selectFrom(QOrder.order)
                .innerJoin(QOrder.order.member, QMember.member).fetchJoin()
                .innerJoin(QOrder.order.delivery, QDelivery.delivery).fetchJoin()
                 .offset(offset)
                 .limit(limit)
                .fetch();

         return orders;

//        return em.createQuery("select o from Order o " +
//                                        "join fetch o.member m " +
//                                        "join fetch o.delivery", Order.class)
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .getResultList();
    }

    public List<Order> findWithItem() throws Exception{
         List<Order> orders = query
                .selectFrom(QOrder.order)
                .innerJoin(QOrder.order.member, QMember.member).fetchJoin()
                .innerJoin(QOrder.order.delivery, QDelivery.delivery).fetchJoin()
                 .innerJoin(QOrder.order.orderItems, QOrderItem.orderItem)
                 .distinct()
                .fetch();

//         Map<Long, List<OrderItem>> orderItems = queryOrderItems(orders);
//
//         orders.stream()
//                 .forEach(order -> order.setOrderItems(orderItems.get(order.getId())));

         return orders;



//        return em.createQuery( "select distinct o from Order o " +
//                                        "join fetch o.member m " +
//                                        "join fetch o.delivery d " +
//                                        "join fetch o.orderItems oi " +
//                                        "join fetch oi.item i", Order.class).getResultList();
    }

    private Map<Long, List<OrderItem>> queryOrderItems(List<Order> orders){
         List<Long> orderMap = orders.stream()
                .map(order -> order.getId())
                .collect(Collectors.toList());

        Member member = new Member();

        return query
                 .selectFrom(QOrderItem.orderItem)
                 .where(QOrderItem.orderItem.id.in(orderMap))
                 .fetch().stream().collect(Collectors.groupingBy(OrderItem::getId));


    }

    public List<OrderQueryDto> findOrderQueryDto() {
        return query
                .select(Projections.constructor(OrderQueryDto.class, QOrder.order))
                .from(QOrder.order)
                .innerJoin(QOrder.order.member, QMember.member)
                .innerJoin(QOrder.order.delivery, QDelivery.delivery)
                .fetch();

//        return em.createQuery("select new jpabook.jpashop.dto.order.OrderQueryDto(o) from Order o " +
//                                        "join o.member m " +
//                                        "join o.delivery", OrderQueryDto.class).getResultList();
    }


    public List<OrderItemQueryDto> findOrderItemQueryDto(Long id) {
        return em.createQuery("select new jpabook.jpashop.dto.orderItemDto.OrderItemQueryDto(oi) from OrderItem oi " +
                                        "join oi.item i " +
                                        "where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", id)
                .getResultList();
    }

    public Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<OrderQueryDto> orders) {
        List<OrderItemQueryDto> orderItems = em.createQuery("select new jpabook.jpashop.dto.orderItemDto.OrderItemQueryDto(oi) from OrderItem oi " +
                                        "join oi.item i " +
                                        "where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orders.stream()
                    .map(order -> order.getId())
                    .collect(Collectors.toList()))
                .getResultList();

        return orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getId));
    }

}
