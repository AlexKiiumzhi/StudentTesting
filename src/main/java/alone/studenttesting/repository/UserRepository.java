package alone.studenttesting.repository;

import alone.studenttesting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update user_test set mark =:mark where user_id =:userId AND test_id =:testId", nativeQuery = true)
    void setMark (int mark ,Long userId, Long testId);

    @Transactional
    @Query(value = "SELECT mark FROM user_test WHERE user_id =:userId AND test_id =:testId", nativeQuery = true)
    Integer getTestMarks (Long userId, Long testId);
}
