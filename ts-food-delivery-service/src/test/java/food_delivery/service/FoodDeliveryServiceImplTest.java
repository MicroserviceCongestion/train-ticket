package food_delivery.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class FoodDeliveryServiceImplTest {
    @InjectMocks
    private FoodDeliveryServiceImpl foodDeliveryServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}
