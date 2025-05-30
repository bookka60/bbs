package com.kh.demo.domain.comments.dao;

import com.kh.demo.domain.entity.Comments;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Transactional
class CommentsDAOImplTest {

  @Autowired
  private CommentsDAO commentsDAO;

  @Test
  @DisplayName("댓글 등록")
  void save() {
    Comments comment = new Comments();
    comment.setPostId(6L);
    comment.setWriter("TEST");
    comment.setContent("댓글 내용");

    Long commentId = commentsDAO.save(comment);

    assertThat(commentId).isNotNull();
    assertThat(commentId).isGreaterThan(0);
  }

  @Test
  @DisplayName("게시글 ID로 댓글 조회")
  void findAllByPostId() {
    Comments comments = new Comments();
    comments.setPostId(6L);
    comments.setWriter("작성자1");
    comments.setContent("내용1");
    commentsDAO.save(comments);

    List<Comments> list = commentsDAO.findAllByPostId(6L);

    assertThat(list).isNotEmpty();
  }

  @Test
  @DisplayName("댓글 ID로 조회")
  void findById() {
    Comments comment = new Comments();
    comment.setPostId(6L);
    comment.setWriter("작성자2");
    comment.setContent("내용2");
    Long savedId = commentsDAO.save(comment);

    Optional<Comments> found = commentsDAO.findById(savedId);

    assertThat(found).isPresent();
    assertThat(found.get().getCommentId()).isEqualTo(savedId);
    assertThat(found.get().getWriter()).isEqualTo("작성자2");
    assertThat(found.get().getContent()).isEqualTo("내용2");
  }

  @Test
  @DisplayName("댓글 수정")
  void updateById() {
    Comments comments = new Comments();
    comments.setPostId(6L);
    comments.setWriter("작성자3");
    comments.setContent("원래 내용");

    Long id = commentsDAO.save(comments);

    Comments updatedComment = new Comments();
    updatedComment.setContent("수정된 내용");

    int affectedRows = commentsDAO.updateById(id, updatedComment);

    assertThat(affectedRows).isEqualTo(1);

    Optional<Comments> found = commentsDAO.findById(id);
    assertThat(found).isPresent();
    assertThat(found.get().getContent()).isEqualTo("수정된 내용");
  }

  @Test
  @DisplayName("댓글 삭제(단건)")
  void deleteById() {
    Comments comment = new Comments();
    comment.setPostId(6L);
    comment.setWriter("작성자4");
    comment.setContent("삭제할 댓글");

    Long id = commentsDAO.save(comment);

    commentsDAO.deleteById(id);

    Optional<Comments> found = commentsDAO.findById(id);
    assertThat(found).isEmpty(); // 삭제 후 댓글이 없어야 함
  }

  @Test
  @DisplayName("게시글 삭제 시 댓글도 자동 삭제")
  void deleteByPostId() {
    Long postId = 6L;

    Comments comments1 = new Comments();
    comments1.setPostId(postId);
    comments1.setWriter("작성자1");
    comments1.setContent("댓글1");

    Comments comments2 = new Comments();
    comments2.setPostId(postId);
    comments2.setWriter("작성자2");
    comments2.setContent("댓글2");

    commentsDAO.save(comments1);
    commentsDAO.save(comments2);

    commentsDAO.deleteByPostId(postId);

    List<Comments> remaining = commentsDAO.findAllByPostId(postId);
    assertThat(remaining).isEmpty();
  }
}
