package com.kh.demo.domain.web.form.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailForm {
  private Long postId;               // 게시글 ID
  private String title;              // 제목
  private String content;            // 내용
  private String author;             // 작성자
  private LocalDateTime creationDate; // 작성일 (LocalDateTime)
  private LocalDateTime modificationDate; // 수정일 (LocalDateTime)

}
