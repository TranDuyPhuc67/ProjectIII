package Fashionstore.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import Fashionstore.entities.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findTop5ByOrderByCreateDateDesc();
    List<Blog> findByTitleContaining(String keyword);

    Page<Blog> findAll(Pageable pageable);
    Page<Blog> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase( String title, String content, Pageable pageable);
}
