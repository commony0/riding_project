DROP TABLE reply CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
DROP TABLE reply;

CREATE TABLE reply(
        replyno                            NUMBER(10)         NOT NULL PRIMARY KEY,
        detailno                           NUMBER(10)         NOT NULL , -- FK
        cyclistno                          NUMBER(10)         NOT NULL , -- FK
        content                            VARCHAR2(2000)     NOT NULL,
        rdate                              DATE               NOT NULL,
        FOREIGN KEY (detailno) REFERENCES detail (detailno),
        FOREIGN KEY (cyclistno) REFERENCES cyclist (cyclistno)
);

COMMENT ON TABLE reply is '댓글';
COMMENT ON COLUMN reply.replyno is '댓글 번호';
COMMENT ON COLUMN reply.detailno is '세부내용 번호';
COMMENT ON COLUMN reply.cyclistno is '회원 번호';
COMMENT ON COLUMN reply.content is '댓글 내용';
COMMENT ON COLUMN reply.rdate is '등록일';


DROP SEQUENCE reply_seq;

CREATE SEQUENCE reply_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지
  
-- 생성
INSERT INTO reply(replyno, detailno, cyclistno, content, rdate)
VALUES (reply_seq.nextval, '4', '1', '코스 추천 감사합니다.', sysdate);

INSERT INTO reply(replyno, detailno, cyclistno, content, rdate)
VALUES (reply_seq.nextval, '4', '3', '힘들었지만 보람찼습니다.', sysdate);

INSERT INTO reply(replyno, detailno, cyclistno, content, rdate)
VALUES (reply_seq.nextval, '5', '3', '힘들었지만 보람찼습니다.', sysdate);

COMMIT;

-----------------------------------------------------------------------------------------
 
-- 목록
 
SELECT replyno, detailno, cyclistno, content, rdate
FROM reply
ORDER BY replyno ASC;
     
     
-- 특정 댓글 조회
 
SELECT replyno, detailno, cyclistno, content, rdate
FROM reply
WHERE replyno = 7;

-- 사용자 댓글 조회

SELECT replyno, detailno, cyclistno, content, rdate
FROM reply
WHERE cyclistno = 3;

-- 게시글 댓글 조회

SELECT replyno, detailno, cyclistno, content, rdate
FROM reply
WHERE detailno = 4;
    
-- 수정
UPDATE reply 
SET content = '추천합니다.'
WHERE replyno=2;

COMMIT;

 
-- 삭제
1) 모두 삭제(모든 게시글의 모든 댓글 삭제)
DELETE FROM reply;
commit;
 
2) 특정 댓글 삭제
DELETE FROM reply
WHERE replyno = 2;
commit;

--------------------------------------------------------------------------------------------------
-- 

3) reply + cyclist join 목록
SELECT c.id, 
    r.replyno, r.detailno, r.cyclistno, r.content, r.rdate
FROM cyclist c,  reply r
WHERE c.cyclistno = r.cyclistno
ORDER BY r.replyno ASC;

4) reply + cyclist join + 특정 detailno 별 목록
SELECT c.id,
    r.replyno, r.detailno, r.cyclistno, r.content, r.rdate
FROM cyclist c,  reply r
WHERE (c.cyclistno = r.cyclistno) AND r.detailno=4
ORDER BY r.replyno ASC;



5) 삭제
-- 패스워드 검사
SELECT count(passwd) as cnt
FROM reply
WHERE replyno=1 AND passwd='1234';

 CNT
 ---
   1
   
-- 삭제
DELETE FROM reply
WHERE replyno=1;

6) contentsno에 해당하는 댓글 수 확인 및 삭제
SELECT COUNT(*) as cnt
FROM reply
WHERE contentsno=1;

 CNT
 ---
   1

DELETE FROM reply
WHERE contentsno=1;

7) memberno에 해당하는 댓글 수 확인 및 삭제
SELECT COUNT(*) as cnt
FROM reply
WHERE memberno=1;

 CNT
 ---
   1

DELETE FROM reply
WHERE memberno=1;
 
8) 삭제용 패스워드 검사
SELECT COUNT(*) as cnt
FROM reply
WHERE replyno=1 AND passwd='1234';

 CNT
 ---
   0

9) 삭제
DELETE FROM reply
WHERE replyno=1;






COMMIT;
