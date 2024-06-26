package train.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import train.entity.TrainType;
import train.repository.TrainTypeRepository;

public class TrainServiceImplTest {

    @InjectMocks
    private TrainServiceImpl trainServiceImpl;

    @Mock
    private TrainTypeRepository repository;

    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate1() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        Mockito.when(repository.save(Mockito.any(TrainType.class))).thenReturn(null);
        boolean result = trainServiceImpl.create(trainType, headers);
        Assertions.assertTrue(result);
    }

    @Test
    public void testCreate2() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString()).get()).thenReturn(trainType);
        boolean result = trainServiceImpl.create(trainType, headers);
        Assertions.assertFalse(result);
    }

    @Test
    public void testRetrieve1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        TrainType result = trainServiceImpl.retrieve("id", headers);
        Assertions.assertNull(result);
    }

    @Test
    public void testRetrieve2() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString()).get()).thenReturn(trainType);
        TrainType result = trainServiceImpl.retrieve("id", headers);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testUpdate1() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString()).get()).thenReturn(trainType);
        Mockito.when(repository.save(Mockito.any(TrainType.class))).thenReturn(null);
        boolean result = trainServiceImpl.update(trainType, headers);
        Assertions.assertTrue(result);
    }

    @Test
    public void testUpdate2() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        boolean result = trainServiceImpl.update(trainType, headers);
        Assertions.assertFalse(result);
    }

    @Test
    public void testDelete1() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString()).get()).thenReturn(trainType);
        Mockito.doNothing().doThrow(new RuntimeException()).when(repository).deleteById(Mockito.anyString());
        boolean result = trainServiceImpl.delete("id", headers);
        Assertions.assertTrue(result);
    }

    @Test
    public void testDelete2() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        boolean result = trainServiceImpl.delete("id", headers);
        Assertions.assertFalse(result);
    }

    @Test
    public void testQuery() {
        Mockito.when(repository.findAll()).thenReturn(null);
        Assertions.assertNull(trainServiceImpl.query(headers));
    }

}
