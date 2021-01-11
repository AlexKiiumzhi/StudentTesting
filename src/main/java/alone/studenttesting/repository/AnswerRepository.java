package alone.studenttesting.repository;

import alone.studenttesting.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByEnAnswer(String enAnswer);
    Optional<Answer> findByUaAnswer(String uaAnswer);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM answer WHERE question_id =:questionId", nativeQuery = true)
    void deleteByQuestionId (Long questionId);
}
