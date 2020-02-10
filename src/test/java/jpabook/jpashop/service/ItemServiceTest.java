package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    public void 아이템저장(){
        Item album = new Album();
        album.setName("album");
        Long saveId = itemService.saveItem(album);

        Assertions.assertEquals(saveId, itemService.findOne(saveId).getId());
    }

    @Test
    public void 전체조회(){
        Item album = new Album();
        Item movie = new Movie();

        itemService.saveItem(album);
        itemService.saveItem(movie);

        Assertions.assertEquals(2, itemService.findItems().size());

    }

    @Test
    public void 아이템DType(){
        Item album = new Album();
        Item movie = new Movie();
        Item book  = new Book();

        Long albumId = itemService.saveItem(album);
        Long movieId = itemService.saveItem(movie);
        Long bookId = itemService.saveItem(book);

        Assertions.assertEquals(album, itemService.findOne(albumId));
        Assertions.assertEquals(movie, itemService.findOne(movieId));
        Assertions.assertEquals(book, itemService.findOne(bookId));

    }

}