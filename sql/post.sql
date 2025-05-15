-- 시퀸스 삭제
DROP SEQUENCE post_post_id_seq;

-- 테이블 삭제
DROP TABLE post;

-- 테이블 생성
CREATE TABLE post(
    post_id           NUMBER(10) PRIMARY KEY,
    title             VARCHAR2(30),
    content           VARCHAR2(30),
    author            VARCHAR2(30),
    creation_date     DATE,
    modification_date DATE
);

-- 시퀸스 생성
CREATE SEQUENCE post_post_id_seq;

-- 데이터 삽입
INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.NEXTVAL, '첫 번째 글', '첫 번째 내용입니다.', '홍길동', SYSDATE, SYSDATE);

INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.NEXTVAL, '두 번째 글', '두 번째 내용입니다.', '홍길서', SYSDATE, SYSDATE);

INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.NEXTVAL, '세 번째 글', '세 번째 내용입니다.', '홍길남', SYSDATE, SYSDATE);

INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.NEXTVAL, '네 번째 글', '네 번째 내용입니다.', '홍길북', SYSDATE, SYSDATE);

INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.NEXTVAL, '다섯 번째 글', '다섯 번째 내용입니다.', '고길동', SYSDATE, SYSDATE);

INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.NEXTVAL, '여섯 번째 글', '여섯 번째 내용입니다.', '고길서', SYSDATE, SYSDATE);

-
COMMIT;

-- 게시글 조회 (예: post_id = 1)
SELECT *
FROM post
WHERE post_id = 1;

-- 게시글 수정 (post_id = 1)
UPDATE post
   SET title = '수정된 제목',
       content = '수정된 내용.',
       modification_date = SYSDATE
 WHERE post_id = 1;

-- 게시글 삭제 (post_id = 1)
DELETE FROM post
WHERE post_id = 1;

-- 전체 게시글 조회
SELECT *
FROM post
ORDER BY creation_date;
