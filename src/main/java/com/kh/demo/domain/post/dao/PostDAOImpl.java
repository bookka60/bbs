package com.kh.demo.domain.post.dao;

import com.kh.demo.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PostDAOImpl implements PostDAO {

  private final NamedParameterJdbcTemplate template;

  RowMapper<Post> postRowMapper() {
    return (rs, rowNum)-> {
      Post post = new Post();
      post.setPostID(rs.getLong("post_id"));
      post.setTitle(rs.getString("title"));
      post.setContent(rs.getString("content"));
      post.setAuthor(rs.getString("author"));
      post.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
      post.setModificationDate(rs.getTimestamp("modification_date").toLocalDateTime());
      return post;
    };
  }

  /**
   * Post등록
   * @param post
   * @return
   */
  @Override
  public Long save(Post post) {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO post (post_id, title, content, author, creation_date, modification_date) ");
    sql.append("     VALUES (POST_POST_ID_SEQ.nextval, :title, :content, :author, :creationDate, :modificationDate)");

    // 자바 객체 필드명과 SQL 파라미터명을 자동 매칭
    SqlParameterSource param = new BeanPropertySqlParameterSource(post);

    // 생성된 key 값을 저장할 KeyHolder
    KeyHolder keyHolder = new GeneratedKeyHolder();

    // Oracle은 RETURNING INTO 문법을 지원하므로, 명시적으로 post_id 컬럼을 키로 지정
    long rows = template.update(sql.toString(), param, keyHolder, new String[]{"post_id"});
    log.info("rows={}", rows);

    // keyHolder에서 post_id 추출
    Number pidNumber = (Number) keyHolder.getKeys().get("post_id");
    long pid = pidNumber.longValue();

    return pid;
  }



  @Override
  public List<Post> findAll() {
    String sql = "SELECT * FROM post ORDER BY creation_date DESC";
    return template.query(sql, postRowMapper());
  }

  @Override
  public Optional<Post> findById(Long postId) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT post_id, title, content, author, creation_date, modification_date ");
    sql.append("  FROM post ");
    sql.append(" WHERE post_id = :postId ");

    SqlParameterSource param = new MapSqlParameterSource().addValue("postId", postId);

    Post post = null;
    try {
      post = template.queryForObject(sql.toString(), param, BeanPropertyRowMapper.newInstance(Post.class));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }

    return Optional.of(post);
  }


  /**
   * 게시글 수정
   * @param postId 게시글 번호
   * @param post 게시글 정보
   * @return 수정된 게시글 건수
   */
  @Override
  public int updateById(Long postId, Post post) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE post ");
    sql.append("   SET title = :title,  content = :content, author = :author ");
    sql.append("       modification_date = :modificationDate ");
    sql.append(" WHERE post_id = :postId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("title", post.getTitle())
        .addValue("content", post.getContent())
        .addValue("author", post.getAuthor())
        .addValue("modificationDate", post.getModificationDate())
        .addValue("postId", postId);

    int rows = template.update(sql.toString(), param);

    return rows;
  }

  @Override
  public int deleteById(Long postId) {
    String sql = "DELETE FROM post WHERE post_id = :postId";
    SqlParameterSource param = new MapSqlParameterSource().addValue("postId", postId);
    return template.update(sql, param);
  }
}
