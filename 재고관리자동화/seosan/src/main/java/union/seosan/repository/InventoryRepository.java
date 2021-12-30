package union.seosan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import union.seosan.entity.Inventory;
import union.seosan.entity.Part;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> selectInventoryByPartAndLot(Part part, String lot);
}
