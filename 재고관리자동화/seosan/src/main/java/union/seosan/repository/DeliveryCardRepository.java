package union.seosan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import union.seosan.entity.DeliveryCard;

@Repository
public interface DeliveryCardRepository extends JpaRepository<DeliveryCard, String> {
}
