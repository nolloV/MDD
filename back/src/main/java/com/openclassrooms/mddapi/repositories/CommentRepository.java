package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByArticleId(Long articleId);
}
