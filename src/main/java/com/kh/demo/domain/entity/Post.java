package com.kh.demo.domain.entity;

import lombok.Data;
import java.time.LocalDateTime;  // 날짜 타입을 LocalDateTime으로 변경

@Data
public class Post {
  private Long postID;               // 게시글 ID
  private String title;              // 제목
  private String content;            // 내용
  private String author;             // 작성자
  private LocalDateTime creationDate; // 작성일 (LocalDateTime)
  private LocalDateTime modificationDate; // 수정일 (LocalDateTime)
}
