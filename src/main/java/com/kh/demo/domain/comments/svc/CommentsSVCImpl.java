package com.kh.demo.domain.comments.svc;

import com.kh.demo.domain.comments.CommentsRepository;
import com.kh.demo.domain.comments.dao.CommentsDAO;
import com.kh.demo.domain.entity.Comments;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsSVCImpl implements CommentsSVC {

  private final CommentsDAO commentsDAO;
  private final CommentsRepository commentsRepository;

  @Override
  public Long save(Comments comment) {
    return commentsDAO.save(comment);
  }

  @Override
  public List<Comments> findAllByPostId(Long postId) {
    return commentsDAO.findAllByPostId(postId);
  }

  @Override
  public Optional<Comments> findById(Long id) {
    return commentsDAO.findById(id);
  }

  @Override
  public int updateById(Long id, Comments comment) {
    return commentsDAO.updateById(id, comment);
  }

  @Override
  public int deleteById(Long id) {
    return commentsDAO.deleteById(id);
  }

  @Override
  public int deleteByPostId(Long postId) {
    return commentsDAO.deleteByPostId(postId);
  }

  @Override
  public Page<Comments> findByPostIdWithPaging(Long postId, Pageable pageable) {
    return commentsRepository.findByPostId(postId, pageable);
  }
}
