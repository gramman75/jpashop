package jpabook.jpashop.controller.order;

import jpabook.jpashop.criteria.OrderSearch;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired MemberService memberService;

    @Autowired ItemService itemService;

    @Autowired OrderService orderService;

    @GetMapping("/order")
    public String orderForm(Model model){
        List<Member> members = memberService.findMember();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String orderCreate(@RequestParam Long memberId, @RequestParam Long itemId, @RequestParam int count){
        orderService.order(memberId, itemId, count);
        return "redirect:/order/list";
    }

    @GetMapping("/order/list")
    public String orderList(OrderSearch orderSearch, Model model){
        model.addAttribute("orderSearch", orderSearch);
        return "redirect:/order/listSearch";
    }

    @GetMapping("/order/listSearch")
    public String searchOrder(OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findByCriteira(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @GetMapping("/order/cancel/{id}")
    public String cancelOrder(@PathVariable Long id, OrderSearch orderSearch, Model model){
//        model.addAttribute("orderSearch", orderSearch);
        orderService.cancelOrder(id);
        return "redirect:/order/list";
    }
}
