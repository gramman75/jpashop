package jpabook.jpashop.controller.member;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.form.item.AlbumForm;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @RequestMapping("/item/create")
    public String createForm(Model model){
        model.addAttribute("albumForm", new AlbumForm());
        return "item/createItem";
    }

    @PostMapping("/item/create")
    public String create(@Validated AlbumForm albumForm, BindingResult result){
        if (result.hasErrors()){
            return "item/createItem";
        }

        Album album = new Album();
        album.setId(albumForm.getId());
        album.setName(albumForm.getName());
        album.setPrice(albumForm.getPrice());
        album.setStockQuantity(album.getStockQuantity());
        album.setEtc(albumForm.getEtc());

        itemService.saveItem(album);

        return "/item/listItem";
    }

    @GetMapping("/item/list")
    public String list(Model model){
        model.addAttribute("albums", itemService.findItems());
        return "item/listItem";
    }
}
