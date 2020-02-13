package jpabook.jpashop.form.item;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public abstract class ItemForm {

    private Long id;

    @NotEmpty
    private String name;

    @NotNull(message = "필수")
    private Integer price;

    @NotNull
    private Integer stockQuantity;
}
