package com.kh.demo.domain.post.svc;

import com.kh.demo.domain.entity.Post;
import com.kh.demo.domain.post.dao.PostDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSVCImpl implements PostSVC{

  final private PostDAO postDAO;


  //post 등록
  @Override
  public Long save(Post post) {
    return postDAO.save(post);
  }

  //post 조회(여러건)
  @Override
  public List<Post> findAll() {
    return postDAO.findAll();
  }

  //post 조회(단건)
  @Override
  public Optional<Post> findById(Long id) {
    return postDAO.findById(id);
  }

  //post 수정
  @Override
  public int updateById(Long id, Post post) {
    return postDAO.updateById(id, post);
  }




  //post 삭제
  @Override
  public int deleteByIds(Long id) {
    return postDAO.deleteById(id);
  }




}
