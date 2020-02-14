package jpabook.jpashop.aop.item;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.form.item.AlbumForm;
import jpabook.jpashop.service.ItemService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ItemExecution {

    @Autowired
    ItemService itemService;

    @Pointcut("execution(* jpabook.jpashop.controller.item.ItemController.update*(..))")
    private void itemUpdate(){}

    @Before("itemUpdate() && args(albumForm,..)")
    public void validate(AlbumForm albumForm){
        System.out.println("=== Before Item Update ===");
        System.out.println("=== albumForm.id ==" + albumForm.getId());
        Long itemId = albumForm.getId();

        Album album = (Album)itemService.findOne(itemId);
        if (album.getEtc() != null && album.getEtc().equals("수정불가")){
            throw new IllegalStateException("수정불가합니다.");
        }
    }

    @ExceptionHandler(IllegalStateException.class)
    public @ResponseBody
    Map<String, String> handle(IllegalStateException e){
        Map<String, String> result = new HashMap<>();
        result.put("message", e.getMessage());
        return result;
    }
}
