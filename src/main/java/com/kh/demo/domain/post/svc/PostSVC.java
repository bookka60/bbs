package com.kh.demo.domain.post.svc;

import com.kh.demo.domain.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostSVC {
  //post 등록
  Long save(Post post);

  //post 조회(여러건)
  List<Post> findAll();

  //post 조회(단건)
  Optional<Post> findById(Long id);

  //post 수정
  int updateById(Long id, Post post);

  //post 삭제
  int deleteByIds(Long id);
}
