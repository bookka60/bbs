package com.kh.demo.domain.comments.dao;

import com.kh.demo.domain.entity.Comments;

import java.util.List;
import java.util.Optional;

public interface CommentsDAO {

  /**
   * 댓글 등록
   * @param comment 등록할 댓글
   * @return 생성된 댓글 ID
   */
  Long save(Comments comment);

  /**
   * 특정 게시글의 전체 댓글 조회
   * @param postId 게시글 번호
   * @return 댓글 목록
   */
  List<Comments> findAllByPostId(Long postId);

  /**
   * 댓글 단건 조회
   * @param id 댓글 ID
   * @return 댓글 Optional
   */
  Optional<Comments> findById(Long id);

  /**
   * 댓글 수정
   * @param id 댓글 ID
   * @param comment 수정할 댓글 객체
   * @return 수정된 건수
   */
  int updateById(Long id, Comments comment);

  /**
   * 댓글 삭제 (단건)
   * @param id 댓글 ID
   * @return 삭제 건수
   */
  int deleteById(Long id);

  /**
   * 게시글에 달린 전체 댓글 삭제
   * @param postId 게시글 ID
   * @return 삭제된 댓글 건수
   */
  int deleteByPostId(Long postId);

  }
