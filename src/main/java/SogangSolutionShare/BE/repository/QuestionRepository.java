package SogangSolutionShare.BE.repository;

import SogangSolutionShare.BE.domain.Category;
import SogangSolutionShare.BE.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByMemberId(Long memberId, Pageable pageable);
    Question findOneById(Long id);
    Page<Question> findByCategory(Category category, Pageable pageable);

    Page<Question> findByIdIn(List<Long> ids, Pageable pageable);
}
