DROP TABLE mlogin CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
DROP TABLE mlogin;

-- manager의 m
/**********************************/
/* Table Name: 매니저 로그인 내역 */
/**********************************/
CREATE TABLE MLOGIN(
		MLOGINNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		MANAGERNO                     		NUMBER(5)		 NOT NULL,
		IP                            		VARCHAR2(15)		 NOT NULL,
		LOGINDATE                     		DATE		 NOT NULL,
  FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
);

COMMENT ON TABLE MLOGIN is '매니저 로그인 내역';
COMMENT ON COLUMN MLOGIN.MLOGINNO is '로그인 번호';
COMMENT ON COLUMN MLOGIN.MANAGERNO is '매니저 번호';
COMMENT ON COLUMN MLOGIN.IP is '접속 IP';
COMMENT ON COLUMN MLOGIN.LOGINDATE is '로그인 날짜';

DROP SEQUENCE mlogin_seq;

CREATE SEQUENCE mlogin_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

-- C
INSERT INTO mlogin(mloginno, managerno, ip, logindate)
VALUES (mlogin_seq.nextval, '1', '127.0.0.1', sysdate);
INSERT INTO mlogin(mloginno, managerno, ip, logindate)
VALUES (mlogin_seq.nextval, '1', '127.0.0.3', sysdate);

-- R 매니저 목록
SELECT mloginno, managerno, ip, logindate
FROM mlogin
ORDER BY logindate DESC;

-- R 2 매니저 개별 목록
SELECT mloginno, managerno, ip, logindate
FROM mlogin
WHERE managerno=1
ORDER BY logindate DESC;


-- D
DELETE FROM mlogin
WHERE mloginno = 2;

DELETE FROM mlogin;

commit;