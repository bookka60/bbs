-- 회원 테이블 생성
CREATE TABLE member (
  member_id number(10),                         -- 내부 관리 아이디 (PK)
  email     varchar2(50),                       -- 로그인 이메일 (UK, NOT NULL)
  passwd    varchar2(12),                       -- 비밀번호
  nickname  varchar2(30),                       -- 별칭
  cdate     timestamp DEFAULT systimestamp,     -- 생성일시
  udate     timestamp DEFAULT systimestamp      -- 수정일시
);

-- 기본키
ALTER TABLE member ADD CONSTRAINT member_member_id_pk PRIMARY KEY (member_id);

-- 제약조건
ALTER TABLE member MODIFY email CONSTRAINT member_email_uk UNIQUE;
ALTER TABLE member MODIFY email CONSTRAINT member_email_nn NOT NULL;

-- 시퀀스 생성
CREATE SEQUENCE member_member_id_seq;

-- 샘플 데이터
INSERT INTO member (member_id, email, passwd, nickname)
VALUES (member_member_id_seq.nextval, 'bookka1@kh.com', '1234', '테스터1');

INSERT INTO member (member_id, email, passwd, nickname)
VALUES (member_member_id_seq.nextval, 'bookka2@kh.com', '1234', '테스터2');

COMMIT;


