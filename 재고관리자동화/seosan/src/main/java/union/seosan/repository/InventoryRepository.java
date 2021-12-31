package union.seosan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import union.seosan.entity.Inventory;
import union.seosan.entity.Part;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(value = "select coalesce(sum(i.stock), 0) from Inventory i where i.part = :part")
    int findInventoryTotalStock(@Param("part") Part part);

    // TODO : 220101 로트 순서 규칙 확인 필요
    List<Inventory> findInventoriesByPart(Part part);

    Optional<Inventory> findInventoryByPartAndLot(Part part, String lot);
}
