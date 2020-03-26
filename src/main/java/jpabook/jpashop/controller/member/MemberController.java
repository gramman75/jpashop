package jpabook.jpashop.controller.member;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.form.member.MemberForm;
import jpabook.jpashop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/member/create")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "member/joinMember";
    }

    @PostMapping("/member/create")
    public String create(@Validated MemberForm memberForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "member/joinMember";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member = new Member();
        member.setAddress(address);
        member.setName(memberForm.getName());
        try{
            memberService.join(member);
        } catch (Exception e){
            e.printStackTrace();
//            FieldError nameFileError = new FieldError("memberForm", "name", "중복 아이디입니다.");
            FieldError nameFileError = new FieldError("memberForm"
                    , "name"
                    , messageSource.getMessage("dupid", null , Locale.KOREA));
            bindingResult.addError(nameFileError);
            return "member/joinMember";
        }

        return "redirect:/";

    }

    @GetMapping("/member/list")
    public String list(Model model){
        model.addAttribute("members",memberService.findMember());
        return "member/memberList";
    }
}
