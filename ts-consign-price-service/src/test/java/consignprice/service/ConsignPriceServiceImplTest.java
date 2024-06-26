package consignprice.service;

import consignprice.entity.ConsignPrice;
import consignprice.repository.ConsignPriceConfigRepository;
import edu.fudan.common.util.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

public class ConsignPriceServiceImplTest {

    @InjectMocks
    private ConsignPriceServiceImpl consignPriceServiceImpl;

    @Mock
    private ConsignPriceConfigRepository repository;

    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPriceByWeightAndRegion1() {
        ConsignPrice priceConfig = new ConsignPrice(UUID.randomUUID().toString(), 1, 2.0, 3.0, 3.5, 4.0);
        Mockito.when(repository.findByIndex(0)).thenReturn(priceConfig);
        Response result = consignPriceServiceImpl.getPriceByWeightAndRegion(1.0, true, headers);
        Assertions.assertEquals(new Response<>(1, "Success", 3.0), result);
    }

    @Test
    public void testGetPriceByWeightAndRegion2() {
        ConsignPrice priceConfig = new ConsignPrice(UUID.randomUUID().toString(), 1, 2.0, 3.0, 3.5, 4.0);
        Mockito.when(repository.findByIndex(0)).thenReturn(priceConfig);
        Response result = consignPriceServiceImpl.getPriceByWeightAndRegion(3.0, true, headers);
        Assertions.assertEquals(new Response<>(1, "Success", 6.5), result);
    }

    @Test
    public void testGetPriceByWeightAndRegion3() {
        ConsignPrice priceConfig = new ConsignPrice(UUID.randomUUID().toString(), 1, 2.0, 3.0, 3.5, 4.0);
        Mockito.when(repository.findByIndex(0)).thenReturn(priceConfig);
        Response result = consignPriceServiceImpl.getPriceByWeightAndRegion(3.0, false, headers);
        Assertions.assertEquals(new Response<>(1, "Success", 7.0), result);
    }

    @Test
    public void testQueryPriceInformation() {
        ConsignPrice priceConfig = new ConsignPrice(UUID.randomUUID().toString(), 1, 2.0, 3.0, 3.5, 4.0);
        Mockito.when(repository.findByIndex(0)).thenReturn(priceConfig);
        Response result = consignPriceServiceImpl.queryPriceInformation(headers);
        String str = "The price of weight within 2.0 is 3.0. The price of extra weight within the region is 3.5 and beyond the region is 4.0\n";
        Assertions.assertEquals(new Response<>(1, "Success", str), result);
    }

    @Test
    public void testCreateAndModifyPrice1() {
        ConsignPrice config = new ConsignPrice(UUID.randomUUID().toString(), 1, 2.0, 3.0, 3.5, 4.0);
        Mockito.when(repository.findByIndex(0)).thenReturn(config);
        Mockito.when(repository.save(Mockito.any(ConsignPrice.class))).thenReturn(null);
        Response result = consignPriceServiceImpl.createAndModifyPrice(config, headers);
        Assertions.assertEquals(new Response<>(1, "Success", config), result);
    }

    @Test
    public void testCreateAndModifyPrice2() {
        ConsignPrice config = new ConsignPrice(UUID.randomUUID().toString(), 0, 2.0, 3.0, 3.5, 4.0);
        Mockito.when(repository.findByIndex(0)).thenReturn(null);
        Mockito.when(repository.save(Mockito.any(ConsignPrice.class))).thenReturn(null);
        Response result = consignPriceServiceImpl.createAndModifyPrice(config, headers);
        Assertions.assertEquals(new Response<>(1, "Success", config), result);
    }

    @Test
    public void testGetPriceConfig() {
        ConsignPrice config = new ConsignPrice();
        Mockito.when(repository.findByIndex(0)).thenReturn(config);
        Response result = consignPriceServiceImpl.getPriceConfig(headers);
        Assertions.assertEquals(new Response<>(1, "Success", config), result);
    }

}
