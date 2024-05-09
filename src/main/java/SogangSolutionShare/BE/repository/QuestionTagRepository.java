package SogangSolutionShare.BE.repository;


import SogangSolutionShare.BE.domain.QuestionTag;
import SogangSolutionShare.BE.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {
    List<QuestionTag> findByQuestionId(Long questionId);
    List<QuestionTag> findByTagIn(List<Tag> tags);
}
