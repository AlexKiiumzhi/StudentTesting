package alone.studenttesting.repository;

import alone.studenttesting.entity.Question;

import alone.studenttesting.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByEnText(String enText);
    Optional<Question> findByUaText(String uaText);
}
