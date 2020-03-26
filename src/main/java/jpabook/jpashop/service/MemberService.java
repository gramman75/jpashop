package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMember(){
        return memberRepository.findAll();
    }

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    private void validateDuplicateMember(Member member) {
        System.out.println("=== step 1 ===");
        if (memberRepository.findByName(member.getName()).size() != 0){
            System.out.println("=== step 2 ===");
            throw new IllegalStateException("중복되는 회원이 있습니다.");
        }
    }

    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
