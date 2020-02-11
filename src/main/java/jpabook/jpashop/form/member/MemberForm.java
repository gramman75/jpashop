package jpabook.jpashop.form.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "이름은 필수입니다.")
    @NotBlank(message = "이름은 필수")
    private String name;

    private String city;

    private String street;

    private String zipcode;
}
