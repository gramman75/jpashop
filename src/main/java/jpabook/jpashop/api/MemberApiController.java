package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.member.MemberDto;
import jpabook.jpashop.dto.Result;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member){
        Long id = memberService.join(member);
        return  new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return  new CreateMemberResponse(id);
    }

    @PutMapping("/api/v1/members/{id}")
    public Member updateMemberV1(@PathVariable Long id, @RequestBody @Validated Member request){
        memberService.update(id, request.getName());
        return memberService.findOne(id);
    }


    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id, @RequestBody @Validated UpdateMemberRequest request){
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        List<Member> members = memberService.findMember();
        return members;
    }
    @GetMapping("/api/v2/members")
    public List<MemberDto> memberV2(){
        List<Member> members = memberService.findMember();

        List<MemberDto> dto =  members.stream()
                .map( member -> new MemberDto(member))
                .collect(Collectors.toList());

        return dto;
    }

    @GetMapping("/api/v3/members")
    public Result memberV3(){
         List<Member> members = memberService.findMember();

         List<MemberDto> dto =  members.stream()
                .map( member -> new MemberDto(member))
                .collect(Collectors.toList());

         return new Result(dto.size(), dto);

    }

    @Data
    static class CreateMemberRequest {

        @NotEmpty(message = "이름필수")
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;

    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor @NoArgsConstructor
    static class UpdateMemberRequest{

        @NotEmpty
        private String name;

    }
}
