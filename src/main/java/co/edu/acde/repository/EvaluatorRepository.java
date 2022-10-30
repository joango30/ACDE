package co.edu.acde.repository;

import co.edu.acde.domain.Evaluator;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Evaluator entity.
 */
@Repository
public interface EvaluatorRepository extends JpaRepository<Evaluator, Long> {
    default Optional<Evaluator> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Evaluator> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Evaluator> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct evaluator from Evaluator evaluator left join fetch evaluator.user left join fetch evaluator.documentType",
        countQuery = "select count(distinct evaluator) from Evaluator evaluator"
    )
    Page<Evaluator> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct evaluator from Evaluator evaluator left join fetch evaluator.user left join fetch evaluator.documentType")
    List<Evaluator> findAllWithToOneRelationships();

    @Query(
        "select evaluator from Evaluator evaluator left join fetch evaluator.user left join fetch evaluator.documentType where evaluator.id =:id"
    )
    Optional<Evaluator> findOneWithToOneRelationships(@Param("id") Long id);
}
