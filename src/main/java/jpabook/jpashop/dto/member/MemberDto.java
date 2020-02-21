package jpabook.jpashop.dto.member;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberDto {
    public MemberDto(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.address = member.getAddress();
    }

    private Long id;
    private String name;

    private Address address;

}
