package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MemberApiControllerTest extends BaseApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Test
    public void 회원생성API() throws Exception{
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.set("name","aaa");

        mockMvc.perform(post("/api/v2/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\" : \"aaa\"}")
                    )
                .andExpect(status().isOk())
//                .andDo(print())
                .andExpect(jsonPath("$.id").value("1"));

    }

    @Test
    public void 회원수정API() throws Exception {
        Member member = new Member();
        member.setName("kim");
        Long memberId = memberService.join(member);

        mockMvc.perform(put("/api/v2/members/"+memberId.toString())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{\"name\" : \"lee\"}")
                       )
                .andExpect(status().isOk())
//                .andDo(print())
                .andExpect(jsonPath("$.name").value("lee"));
    }

    @Test
    public void 회원조회API() throws Exception {
        Member member = new Member();
        member.setName("kim");
        Member member1 = new Member();
        member1.setName("lee");
        Long memberId = memberService.join(member);
        Long memberId1 = memberService.join(member1);

        mockMvc.perform(get("/api/v3/members"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.count").value("2"))
                .andExpect(jsonPath("$.data[?(@.id=='"+memberId.toString()+"')].name").value("kim"));


    }

}