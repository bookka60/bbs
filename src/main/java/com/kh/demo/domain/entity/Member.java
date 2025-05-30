package com.kh.demo.domain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Member {
  private Long memberId;
  private String email;
  private String passwd;
  private String nickname;
  private LocalDateTime cdate;
  private LocalDateTime udate;
}
