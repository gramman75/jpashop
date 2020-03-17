package jpabook.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QMember;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class QDSLRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public QDSLRepository(EntityManager em){
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Member> membersV1(){
        return query
                .selectFrom(QMember.member)
                .fetch();
    }


}
