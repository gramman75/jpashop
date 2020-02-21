package jpabook.jpashop.api;

import com.fasterxml.jackson.databind.ser.Serializers;
import jpabook.jpashop.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("query")
public class OrderSimpleApiControllerTest extends BaseApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrderService orderService;


    @Test
    public void 주문조회API() throws Exception {

        mockMvc.perform(get("/api/v3/simpleorders"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.count").value("9"));
    }
}