package jpabook.jpashop.repository;

import jpabook.jpashop.criteria.OrderSearch;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;


    public void save(Order order){
        em.persist(order);
    }

    public Order findOrder(Long id){
        return em.find(Order.class, id);
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

    public List<Order> findWithMemberDelivery() {
        return em.createQuery("select o from Order o " +
                                        "join fetch o.member m " +
                                        "join fetch o.delivery", Order.class).getResultList();
    }


    public List<Order> findWithMemberDelivery(int offset, int limit) {
        return em.createQuery("select o from Order o " +
                                        "join fetch o.member m " +
                                        "join fetch o.delivery", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Order> findWithItem() throws Exception{
        return em.createQuery( "select distinct o from Order o " +
                                        "join fetch o.member m " +
                                        "join fetch o.delivery d " +
                                        "join fetch o.orderItems oi " +
                                        "join fetch oi.item i", Order.class).getResultList();
    }
}
