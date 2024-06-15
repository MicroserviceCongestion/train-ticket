package trainFood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import trainFood.entity.TrainFood;

import java.util.List;

@Repository
public interface TrainFoodRepository extends CrudRepository<TrainFood, String> {

    @Override
    List<TrainFood> findAll();

    TrainFood findByTripId(String tripId);
}
