package alone.studenttesting.repository;

import alone.studenttesting.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByEnAnswer(String enAnswer);
    Optional<Answer> findByUaAnswer(String uaAnswer);

}
