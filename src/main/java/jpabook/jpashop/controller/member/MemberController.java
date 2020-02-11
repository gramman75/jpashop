package jpabook.jpashop.controller.member;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.form.member.MemberForm;
import jpabook.jpashop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/member/create")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "/member/joinMember";
    }

    @PostMapping("/member/create")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/member/joinMember";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member = new Member();
        member.setAddress(address);
        member.setName(memberForm.getName());
        try{
            memberService.join(member);
        } catch (Exception e){
            bindingResult.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
            return "/member/joinMember";
        }

        return "redirect:/";

    }
}
