package union.seosan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import union.seosan.entity.PartInventory;

@Repository
public interface PartInventoryRepository extends JpaRepository<PartInventory, String> {
}
