package preserve.controller;

import com.alibaba.fastjson.JSONObject;
import edu.fudan.common.util.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import edu.fudan.common.entity.OrderTicketsInfo;
import preserve.service.PreserveService;

public class PreserveControllerTest {

    @InjectMocks
    private PreserveController preserveController;

    @Mock
    private PreserveService preserveService;
    private MockMvc mockMvc;
    private Response response = new Response();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(preserveController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/preserveservice/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to [ Preserve Service ] !"));
    }

    @Test
    public void testPreserve() throws Exception {
        OrderTicketsInfo oti = new OrderTicketsInfo();
        Mockito.when(preserveService.preserve(Mockito.any(OrderTicketsInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(oti);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/preserveservice/preserve").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assertions.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

}
