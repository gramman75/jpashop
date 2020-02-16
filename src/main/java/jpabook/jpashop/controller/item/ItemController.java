package jpabook.jpashop.controller.item;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.form.item.AlbumForm;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        return "redirect:/item/list";
    }

    @GetMapping("/item/list")
    public String list(Model model){
        model.addAttribute("albums", itemService.findItems());
        return "item/listItem";
    }

    @GetMapping("/item/update/{itemId}")
    public String updateForm(@PathVariable Long itemId, Model model){
        Album album = (Album)itemService.findOne(itemId);
        AlbumForm albumForm = new AlbumForm();
        albumForm.setId(album.getId());
        albumForm.setName(album.getName());
        albumForm.setPrice(album.getPrice());
        albumForm.setStockQuantity(album.getStockQuantity());
        albumForm.setArtist(album.getArtist());
        albumForm.setEtc(album.getEtc());

        model.addAttribute("albumForm", albumForm);

        return "item/updateItem";
    }

    @PostMapping("/item/update")
    public String update(@Validated AlbumForm albumForm, @RequestParam Long id,  BindingResult result){
        if (result.hasErrors()){
            return "item/updateItem";
        }

        itemService.updateItem(id, albumForm);

//        Album album = (Album)itemService.findOne(albumForm.getId());
//
//        album.setId(albumForm.getId());
//        album.setName(albumForm.getName());
//        album.setPrice(albumForm.getPrice());
//        album.setStockQuantity(albumForm.getStockQuantity());
//        album.setArtist(albumForm.getArtist());
//        album.setEtc(albumForm.getEtc());

        return "redirect:/item/list";
    }

}
