package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RunAs;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository rep;

    @Transactional
    @Test
    @Rollback(false)
    public void save() {
        Member member = new Member();
        member.setName("Kim");

        Long savedId = rep.save(member);
        Member findedMember = rep.find(savedId);

        Assertions.assertThat(findedMember.getId().equals(savedId));
        Assertions.assertThat(findedMember.getName().equals(member.getName()));


    }

    @org.junit.Test
    public void find() {
    }
}