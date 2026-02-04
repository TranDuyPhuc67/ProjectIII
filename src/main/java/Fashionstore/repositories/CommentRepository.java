package Fashionstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import Fashionstore.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findTop5ByOrderByCreateDateDesc();
    List<Comment> findByBlog_BlogIdOrderByCreateDateDesc(Integer blogId);
}
