CREATE TABLE post(
	post_id           number(10) PRIMARY KEY,
	title             varchar2(30),
	content           varchar2(30),
	author            varchar2(30),
	creation_date     DATE,
	modification_date date
);

DROP TABLE post;

SELECT * FROM post;
COMMIT;

INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.nextval, '첫 번째 글', '첫 번째 내용입니다.', '홍길동', SYSDATE, SYSDATE);
INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.nextval, '두 번째 글', '두 번째 내용입니다.', '홍길서', SYSDATE, SYSDATE);
INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.nextval, '세 번째 글', '세 번째 내용입니다.', '홍길남', SYSDATE, SYSDATE);
INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.nextval, '네 번째 글', '네 번째 내용입니다.', '홍길북', SYSDATE, SYSDATE);
INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.nextval, '다섯 번째 글', '다섯 번째 내용입니다.', '고길동', SYSDATE, SYSDATE);
INSERT INTO post (post_id, title, content, author, creation_date, modification_date)
VALUES (post_post_id_seq.nextval, '여섯 번째 글', '여섯 번째 내용입니다.', '고길서', SYSDATE, SYSDATE);


--시퀸스

create sequence post_post_id_seq;
drop sequence POST_POST_ID_SEQ;
--게시글 조회
SELECT *
FROM post
WHERE post_id = 1;

--게시글 수정
UPDATE post
   SET title = '수정된 제목',
       content = '수정된 내용.',
       modification_date = SYSDATE
 WHERE post_id = 1;

--게시글 삭제
DELETE FROM post
WHERE post_id = 1;

--게시글 조회
SELECT *
FROM post
ORDER BY creation_date;
