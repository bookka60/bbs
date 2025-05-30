package com.kh.demo.domain.post.svc;

import com.kh.demo.domain.entity.Post;
import com.kh.demo.domain.post.PostRepository;
import com.kh.demo.domain.post.dao.PostDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSVCImpl implements PostSVC{

  final private PostDAO postDAO;
  private final PostRepository postRepository;


  //post 등록
  @Override
  public Long save(Post Post) {

    return postDAO.save(Post);
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
  public int updateById(Long id, Post Post) {

    return postDAO.updateById(id, Post);
  }


  //삭제 (여러건)
  @Override
  public int deleteByIds(List<Long> ids) {
    return postDAO.deleteByIds(ids);
  }

  //삭제 (단건)
  @Override
  public int deleteById(Long postId) {

    return postDAO.deleteById(postId);
  }

  @Override
  public Page<Post> findAll(Pageable pageable) {
    return postRepository.findAll(pageable);
  }

}
