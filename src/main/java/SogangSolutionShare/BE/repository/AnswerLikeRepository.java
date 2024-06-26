package SogangSolutionShare.BE.repository;

import SogangSolutionShare.BE.domain.AnswerLike;
import SogangSolutionShare.BE.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerLikeRepository extends JpaRepository<AnswerLike, Long> {
    Optional<AnswerLike> findByAnswerIdAndMemberId(Long answerId, Long memberId);

    @EntityGraph(attributePaths = {"answer"})
    Page<AnswerLike> findAllByMember(Member member, Pageable pageable);
}
