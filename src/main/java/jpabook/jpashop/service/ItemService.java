package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.form.item.AlbumForm;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional(readOnly = false)
    public Long saveItem(Item item){
       itemRepository.save(item);
       return item.getId();
    }

    @Transactional
    public void updateItem(Long id, AlbumForm albumForm){
        Album album= (Album)itemRepository.findOne(id);

        album.setId(albumForm.getId());
        album.setName(albumForm.getName());
        album.setPrice(albumForm.getPrice());
        album.setStockQuantity(albumForm.getStockQuantity());
        album.setArtist(albumForm.getArtist());
        album.setEtc(albumForm.getEtc());

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }


}
