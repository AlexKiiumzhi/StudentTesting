package alone.studenttesting.repository;


import alone.studenttesting.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findByEnName(String enName);
    Optional<Test> findByUaName(String uaName);


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_test (user_id, test_id, mark) VALUES (?1, ?2 , null)", nativeQuery = true)
    Integer addUserTest (Long userId, Long testId);

}
