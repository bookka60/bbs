package com.kh.demo.domain.comments.svc;

import com.kh.demo.domain.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentsSVC {

  Long save(Comments comment);

  List<Comments> findAllByPostId(Long postId);

  Optional<Comments> findById(Long id);

  int updateById(Long id, Comments comment);

  int deleteById(Long id);

  int deleteByPostId(Long postId);

  Page<Comments> findByPostIdWithPaging(Long postId, Pageable pageable);

}
