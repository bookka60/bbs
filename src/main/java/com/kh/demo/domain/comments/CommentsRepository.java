package com.kh.demo.domain.comments;

import com.kh.demo.domain.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
  Page<Comments> findByPostId(Long postId, Pageable pageable);
}
