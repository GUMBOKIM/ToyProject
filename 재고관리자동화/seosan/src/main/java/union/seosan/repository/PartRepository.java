package union.seosan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import union.seosan.entity.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, String> {
}
