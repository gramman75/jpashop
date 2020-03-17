package jpabook.jpashop.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static jpabook.jpashop.domain.QMember.member;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("query")
public class QDSLRepositoryTest{
    @Autowired
    QDSLRepository qdslRepository;

    @Autowired
    EntityManager em;

    JPAQueryFactory query;

    @Before
    public void setup(){
        query = new JPAQueryFactory(em);
    }

    @Test
    public void 조건() {

        List<Member> member = query
                .selectFrom(QMember.member)
                .where(QMember.member.address.city.eq("서울").or(QMember.member.name.eq("kim")))
                .fetch();

        Assertions.assertEquals(3, member.size());
    }

    @Test
    public void innerjoin() {
        List<Member> member = query
                .selectFrom(QMember.member)
                .innerJoin(QMember.member.order, QOrder.order)
                .fetch();

        Assertions.assertEquals(6, member.size());

    }

    @Test
    public void 정렬(){
        List<Member> member = query
                .selectFrom(QMember.member)
                .innerJoin(QMember.member.order, QOrder.order)
                .orderBy(QMember.member.address.city.desc())
                .fetch();

        Assertions.assertEquals("서울", member.get(0).getAddress().getCity());
    }

    @Test
    public void Grouping(){
        List<Tuple> member = query.
                select(QMember.member.address.city, QMember.member.address.city.count())
                .from(QMember.member)
                .groupBy(QMember.member.address.city)
                .fetch();

        for (Tuple tuple : member) {
            System.out.println(tuple.get(0,String.class));
        }

        Assertions.assertEquals(4, member.size());

    }

    @Test
    public void 삭제(){
        Long result = query.delete(QMember.member).where(QMember.member.name.eq("")).execute();
        Assertions.assertEquals(1, result);
    }

    @Test
    public void 수정(){
        Long result = query
                .update(QMember.member)
                .where(QMember.member.name.isNull())
                .set(QMember.member.name, "Unknow")
                .execute();

        Assertions.assertEquals(3, result);

    }

    @Test
    public void 서브쿼리(){
        QMember subMember = new QMember("subMember");

        List<Member> members = query
                .selectFrom(member)
                .where(member.id.in(
                        JPAExpressions.select(subMember.id)
                                      .from(subMember)
                                      .where(subMember.address.city.eq("서울"))
                ))
                .fetch();

        Assertions.assertEquals(2,members.size());
    }

    @Test
    public void ExposingOriginalQuery(){
        Query jpaQuery = query.selectFrom(member).createQuery();
        List<Member> members = jpaQuery.getResultList();
        Assertions.assertEquals(7,members.size());

    }
}