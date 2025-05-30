DROP SEQUENCE comments_seq;
DROP TABLE comments;

CREATE TABLE comments (
  comment_id   NUMBER PRIMARY KEY,
  post_id      NUMBER NOT NULL,
  writer       VARCHAR2(100) NOT NULL,
  content      VARCHAR2(1000) NOT NULL,
  created_at   DATE DEFAULT SYSDATE,
  updated_at   DATE NOT NULL,
  CONSTRAINT fk_comment_post
    FOREIGN KEY (post_id)
    REFERENCES post(post_id)
    ON DELETE CASCADE
);


CREATE SEQUENCE comments_seq;

--게시글 조회
SELECT *
FROM post
ORDER BY creation_date;

INSERT INTO comments (comment_id, post_id, writer, content, created_at, updated_at)
VALUES (comments_seq.NEXTVAL, 3, '댓글작성자', '댓글 내용입니다.', sysdate, sysdate );

--게시글 댓글 조회
  SELECT * FROM comments
   WHERE post_id = 3
ORDER BY created_at;

--게시글 댓글 수정
UPDATE comments
   SET content = '수정된 댓글 내용',
       updated_at = SYSDATE
 WHERE comment_id = 3;

--게시글 댓글 삭제
DELETE FROM comments
 WHERE comment_id = 1;

--게시글 삭제
DELETE FROM post
WHERE post_id = 5;

--관련 댓글도 자동 삭제되었는지 확인
SELECT * FROM comments
WHERE post_id = 5;

COMMIT;

















