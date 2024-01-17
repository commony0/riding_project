DROP TABLE notice CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
DROP TABLE notice;

CREATE TABLE notice(
        noticeno                            NUMBER(10)         NOT NULL PRIMARY KEY,
        managerno                           NUMBER(5)          NOT NULL, -- FK
        title                               VARCHAR2(1000)     NOT NULL,
        content                             CLOB        NOT NULL,
        rdate                               DATE               NOT NULL,
        FOREIGN KEY (managerno) REFERENCES manager (managerno)
);

COMMENT ON TABLE notice is '공지사항';
COMMENT ON COLUMN notice.noticeno is '공지사항 번호';
COMMENT ON COLUMN notice.managerno is '매니저 번호';
COMMENT ON COLUMN notice.title is '제목';
COMMENT ON COLUMN notice.content is '내용';
COMMENT ON COLUMN notice.rdate is '등록일';


DROP SEQUENCE notice_seq;

CREATE SEQUENCE notice_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지
  
-- 생성
INSERT INTO notice(noticeno, managerno, title, content, rdate)
VALUES (notice_seq.nextval, '1', '자전거 탈 때 필독', '자전거를 탈 때는 장비를 착용하기', sysdate);

INSERT INTO notice(noticeno, managerno, title, content, rdate)
VALUES (notice_seq.nextval, '1', '이벤트', '12월 30일 서울 OO에서 진행', sysdate);

COMMIT;

-----------------------------------------------------------------------------------------
 
-- 목록
 
SELECT noticeno, managerno, title, content, rdate
FROM notice
ORDER BY noticeno DESC;
     
     
-- 공지사항 조회
 
SELECT noticeno, managerno, title, content, rdate
FROM notice
WHERE noticeno = 1;

-- 매니저 번호 조회

SELECT noticeno, managerno, title, content, rdate
FROM notice
WHERE managerno = 1;
    
-- 수정
UPDATE notice 
SET title = '이벤트 진행중', content = 'XX시 까지 진행'
WHERE noticeno = 2;



COMMIT;

 
-- 삭제
1) 모두 삭제(모든 게시글의 모든 댓글 삭제)
DELETE FROM notice;
commit;
 
2) 특정 댓글 삭제
DELETE FROM notice
WHERE noticeno = 2;
commit;