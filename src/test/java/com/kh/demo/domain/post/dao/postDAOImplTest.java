package com.kh.demo.domain.post.dao;

import com.kh.demo.domain.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class postDAOImplTest {

  private static final Logger log = LoggerFactory.getLogger(postDAOImplTest.class);
  @Autowired
  PostDAO postDAO; // 실제 PostDAO 빈 주입



  @Test
  @DisplayName("게시글 목록 조회")
  void findAll() {
    List<Post> list = postDAO.findAll();
    for (Post Post : list) {
      log.info("post={}", Post);

    }
  }

  @Test
  @DisplayName("게시글 등록")
  void save() {
    Post Post = new Post();
    Post.setTitle("테스트 제목");
    Post.setContent("테스트 내용");
    Post.setAuthor("홍길동");
    Post.setCreationDate(LocalDateTime.now());
    Post.setModificationDate(LocalDateTime.now());

    Long pid = postDAO.save(Post);
    log.info("등록된 게시글 ID={}", pid);
  }



  @Test
  @DisplayName("게시글 조회")
  void findById(){
    Long postId = 70L;
    Optional<Post> optionalPost = postDAO.findById(postId);
    Post findedPost = optionalPost.orElseThrow();
    log.info("findedPost={}", findedPost);

  }

  @Test
  @DisplayName("게시글 삭제(단건)")
  void deleteById() {
    Long postId = 73L; // 예시로 ID 4를 사용
    int rows = postDAO.deleteById(postId);
    log.info("삭제된 행 수={}", rows);
    Assertions.assertThat(rows).isEqualTo(1); // 1개의 게시글이 삭제되었는지 확인
  }

  @Test
  @DisplayName("게시글 수정")
  void updateById() {
    Long postId = 70L; // 예시 ID
    Post Post = new Post();
    Post.setTitle("수정된 제목");
    Post.setContent("수정된 내용");
    Post.setAuthor("수정된 작성자");
    Post.setModificationDate(LocalDateTime.now());

    int rows = postDAO.updateById(postId, Post);

    Optional<Post> optPost = postDAO.findById(postId);
    Post modifiedPost = optPost.orElseThrow();

    Assertions.assertThat(modifiedPost.getTitle()).isEqualTo("수정된 제목");
    Assertions.assertThat(modifiedPost.getContent()).isEqualTo("수정된 내용");
    Assertions.assertThat(modifiedPost.getAuthor()).isEqualTo("수정된 작성자");
  }
}
