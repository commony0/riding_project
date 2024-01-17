/**********************************/
/* Table Name: 매니저 */
-- 개인 프로젝트에서는 개발자가 유일한 매니저로 처리됨.
/**********************************/
DROP TABLE MANAGER;

CREATE TABLE MANAGER(
    MANAGERNO    NUMBER(5)    NOT NULL,
    ID         VARCHAR(20)   NOT NULL UNIQUE, -- 아이디, 중복 안됨, 레코드를 구분 
    PASSWD     VARCHAR(15)   NOT NULL, -- 패스워드, 영숫자 조합
    MNAME      VARCHAR(20)   NOT NULL, -- 성명, 한글 10자 저장 가능
    MDATE      DATE          NOT NULL, -- 가입일    
    GRADE      NUMBER(2)     NOT NULL, -- 등급(1~10: 매니저, 정지 매니저: 20, 탈퇴 매니저: 99)    
    PRIMARY KEY (MANAGERNO)              -- 한번 등록된 값은 중복 안됨
);

COMMENT ON TABLE MANAGER is '매니저';
COMMENT ON COLUMN MANAGER.MANAGERNO is '매니저 번호';
COMMENT ON COLUMN MANAGER.ID is '아이디';
COMMENT ON COLUMN MANAGER.PASSWD is '패스워드';
COMMENT ON COLUMN MANAGER.MNAME is '성명';
COMMENT ON COLUMN MANAGER.MDATE is '가입일';
COMMENT ON COLUMN MANAGER.GRADE is '등급';

DROP SEQUENCE MANAGER_SEQ;

CREATE SEQUENCE MANAGER_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 99999            -- 최대값: 99999 --> NUMBER(5) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

-- CREATE
INSERT INTO manager(managerno, id, passwd, mname, mdate, grade)
VALUES(manager_seq.nextval, 'manager1', '2627', '매니저1', sysdate, 1);

INSERT INTO manager(managerno, id, passwd, mname, mdate, grade)
VALUES(manager_seq.nextval, 'manager2', '1234', '매니저2', sysdate, 1);

INSERT INTO manager(managerno, id, passwd, mname, mdate, grade)
VALUES(manager_seq.nextval, 'manager3', '1234', '매니저3', sysdate, 1);

commit;

-- READ: List
SELECT managerno, id, passwd, mname, mdate, grade FROM manager ORDER BY managerno ASC;


-- READ         
SELECT managerno, id, passwd, mname, mdate, grade 
FROM manager
WHERE managerno=1;

-- READ by id
SELECT managerno, id, passwd, mname, mdate, grade 
FROM manager
WHERE id='manager1';

-- UPDATE
UPDATE manager
SET passwd='1234', mname='매니저1', mdate=sysdate, grade=1
WHERE managerno=1;

COMMIT;

-- DELETE
DELETE FROM manager WHERE managerno=3;
         
-- 로그인, 1: 로그인 성공, 0: 로그인 실패
SELECT COUNT(*) as cnt
FROM manager
WHERE id='manager1' AND passwd='1234'; 
