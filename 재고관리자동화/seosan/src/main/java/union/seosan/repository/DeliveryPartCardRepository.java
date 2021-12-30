package union.seosan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import union.seosan.entity.DeliveryPartCard;

@Repository
public interface DeliveryPartCardRepository extends JpaRepository<DeliveryPartCard, String> {
}
