package jpabook.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QMember;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class MemberRepositoryQdsl implements MemberRepositoryInterface {
    private final EntityManager em;

    private final JPAQueryFactory query;

    public MemberRepositoryQdsl(EntityManager em){
        this.em = em;
        query = new JPAQueryFactory(em);
    }

    public void save(Member member){
        em.persist(member);
    }
    public Member findOne(Long id){
        return query
                .selectFrom(QMember.member)
                .where(QMember.member.id.eq(id))
                .fetchOne();
    }

    public List<Member> findAll(){
        return query
                .selectFrom(QMember.member)
                .fetch();
    }

    public List<Member> findByName(String name){
        return query
                .selectFrom(QMember.member)
                .where(QMember.member.name.eq(name))
                .fetch();
    }
}
