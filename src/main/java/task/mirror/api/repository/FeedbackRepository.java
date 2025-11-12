package task.mirror.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.mirror.api.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
