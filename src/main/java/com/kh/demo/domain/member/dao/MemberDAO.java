package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;
import java.util.Optional;

public interface MemberDAO {
  //회원 가입
  Member save(Member member);

  //회원 존재 유무
  boolean isExist(String email);

  //회원 조회 memberEmail
  Optional<Member> findByEmail(String email);

  //회원 조회 memberId
  Optional<Member> findByMemberId(Long memberId);

  //정보 수정
  int update(Member member);

  //회원 삭제
  int delete(Long memberId);
}
