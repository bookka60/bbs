package com.kh.demo.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Comments {

  @Id
  private Long commentId;        // 댓글 ID
  private Long postId;           // 해당 게시글 ID
  private String writer;         // 작성자
  private String content;        // 내용
  private LocalDateTime createdAt;   // 작성일
  private LocalDateTime updatedAt;   // 수정일
}
