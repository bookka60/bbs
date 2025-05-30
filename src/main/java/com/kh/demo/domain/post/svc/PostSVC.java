package com.kh.demo.domain.post.svc;

import com.kh.demo.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostSVC {
  //post 등록
  Long save(Post Post);

  //post 조회(여러건)
  List<Post> findAll();

  //post 조회(단건)
  Optional<Post> findById(Long id);

  //post 수정
  int updateById(Long id, Post Post);

  //post 삭제(여러건)
  int deleteByIds(List<Long> ids);

  //post 삭제(단건)
  int deleteById(Long postId);

  Page<Post> findAll(Pageable pageable);

}
