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
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PostDAOImpl implements PostDAO {

  private final NamedParameterJdbcTemplate template;

  RowMapper<Post> postRowMapper() {
    return (rs, rowNum)-> {
      Post Post = new Post();
      Post.setPostId(rs.getLong("post_id"));
      Post.setTitle(rs.getString("title"));
      Post.setContent(rs.getString("content"));
      Post.setAuthor(rs.getString("author"));
      Post.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
      Post.setModificationDate(rs.getTimestamp("modification_date").toLocalDateTime());
      return Post;
    };
  }

  /**
   * Post등록
   * @param Post
   * @return
   */
  @Override
  public Long save(Post Post) {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO post (post_id, title, content, author, creation_date, modification_date) ");
    sql.append("     VALUES (POST_POST_ID_SEQ.nextval, :title, :content, :author, :creationDate, :modificationDate)");


    SqlParameterSource param = new BeanPropertySqlParameterSource(Post);

    KeyHolder keyHolder = new GeneratedKeyHolder();

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

    Post Post = null;
    try {
      Post = template.queryForObject(sql.toString(), param, BeanPropertyRowMapper.newInstance(Post.class));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }

    return Optional.of(Post);
  }


  /**
   * 게시글 수정
   * @param postId 게시글 번호
   * @param Post 게시글 정보
   * @return 수정된 게시글 건수
   */
  @Override
  public int updateById(Long postId, Post Post) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE post ");
    sql.append("   SET title = :title, content = :content, author = :author, ");
    sql.append("       modification_date = :modificationDate ");
    sql.append(" WHERE post_id = :postId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("title", Post.getTitle())
        .addValue("content", Post.getContent())
        .addValue("author", Post.getAuthor())
        .addValue("modificationDate", Post.getModificationDate())
        .addValue("postId", postId);

    int rows = template.update(sql.toString(), param);

    return rows;
  }

  //삭제(단건)
  @Override
  public int deleteById(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM post ");
    sql.append(" WHERE post_id = :id ");

    Map<String, Long> param = Map.of("id",id);
    int rows = template.update(sql.toString(), param);
    return rows;
  }

  //삭제 (여러건)
  @Override
  public int deleteByIds(List<Long> ids) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM post ");
    sql.append(" WHERE post_id IN ( :ids ) ");

    Map<String, List<Long>> param = Map.of("ids",ids);
    int rows = template.update(sql.toString(), param);
    return rows;
  }
}
