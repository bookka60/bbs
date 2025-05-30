package com.kh.demo.domain.comments.dao;

import com.kh.demo.domain.entity.Comments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentsDAOImpl implements CommentsDAO {

  private final NamedParameterJdbcTemplate template;

  // 수동 매핑
  private RowMapper<Comments> commentsRowMapper() {
    return (rs, rowNum) -> {
      Comments comment = new Comments();
      comment.setCommentId(rs.getLong("comment_id"));
      comment.setPostId(rs.getLong("post_id"));
      comment.setWriter(rs.getString("writer"));
      comment.setContent(rs.getString("content"));
      comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
      comment.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
      return comment;
    };
  }

  //댓글작성 (저장)
  @Override
  public Long save(Comments comment) {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO comments (comment_id, post_id, writer, content, created_at, updated_at) ");
    sql.append("VALUES (comments_seq.nextval, :postId, :writer, :content, systimestamp, systimestamp) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
    KeyHolder keyHolder = new GeneratedKeyHolder();

    int rows = template.update(sql.toString(), param, keyHolder, new String[]{"comment_id"});
    return ((Number) keyHolder.getKeys().get("comment_id")).longValue();
  }

  //댓글 목록
  @Override
  public List<Comments> findAllByPostId(Long postId) {
    String sql = "SELECT * FROM comments WHERE post_id = :postId ORDER BY comment_id DESC";

    SqlParameterSource param = new MapSqlParameterSource().addValue("postId", postId);
    return template.query(sql, param, commentsRowMapper());
  }

  //댓글 조회(단건)
  @Override
  public Optional<Comments> findById(Long id) {
    String sql = "SELECT * FROM comments WHERE comment_id = :id";

    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
    Comments comment = null;
    try {
      comment = template.queryForObject(sql, param, commentsRowMapper());
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
    return Optional.of(comment);
  }

  //댓글 수정
  @Override
  public int updateById(Long id, Comments comment) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE comments ");
    sql.append("   SET content = :content, ");
    sql.append("       updated_at = systimestamp ");
    sql.append(" WHERE comment_id = :id ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("content", comment.getContent())
        .addValue("id", id);

    return template.update(sql.toString(), param);
  }

  //댓글 삭제
  @Override
  public int deleteById(Long id) {
    String sql = "DELETE FROM comments WHERE comment_id = :id";
    Map<String, Long> param = Map.of("id", id);
    return template.update(sql, param);
  }

  //게시글 삭제시 댓글 삭제
  @Override
  public int deleteByPostId(Long postId) {
    String sql = "DELETE FROM comments WHERE post_id = :postId";
    Map<String, Long> param = Map.of("postId", postId);
    return template.update(sql, param);
  }

}







