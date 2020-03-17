package jpabook.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.QItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ItemRepositoryQdsl implements ItemRepositoryInterface {
    private final EntityManager em;

    private final JPAQueryFactory query;

    public ItemRepositoryQdsl(EntityManager em){
        this.em = em;
        query = new JPAQueryFactory(em);
    }


    public void save(Item item){
        if (item.getId() == null){
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return query
                .selectFrom(QItem.item)
                .where(QItem.item.id.eq(id))
                .fetchOne();
    }

    public List<Item> findAll(){
        return query
                .selectFrom(QItem.item)
                .fetch();
    }
}
