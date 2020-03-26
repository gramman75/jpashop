package jpabook.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QMember;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class QuerydslBasicTest {

    @Autowired
    MemberRepository repository;

    @Autowired
    EntityManager em;

    JPAQueryFactory query;

    @Before
    public void init() {
        query = new JPAQueryFactory(em);
        Member m = new Member();
        m.setName("member1");
        repository.save(m);

    }
    @Test
    public void startJPQL(){
        Member member = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", "member1")
                .getSingleResult();

        assertEquals(member.getName(), "member1");
    }

    @Test
    public void startQuerydsl(){
        QMember m = new QMember("m");

        Member member = query.select(m)
                .from(m)
                .where(m.name.eq("member1"))
                .fetchOne();
        assertEquals(member.getName(),"member1");
    }
}
