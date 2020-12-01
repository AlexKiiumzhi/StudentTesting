package alone.studenttesting.repository;


import alone.studenttesting.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findByEnName(String enName);
    Optional<Test> findByUaName(String uaName);

}
