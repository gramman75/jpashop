package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입(){
        Member member = new Member();
        Address address = new Address("city","street","zipcode");
        member.setName("Member A");
        member.setAddress(address);

        Long savedId = memberService.join(member);
        Member findMember = memberRepository.findOne(savedId);

        Assertions.assertEquals(member, findMember);


    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원(){
        Member member1 = new Member();
        member1.setName("kim");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("kim");
        memberService.join(member2);

        fail("중복에러 발생.");

    }
}