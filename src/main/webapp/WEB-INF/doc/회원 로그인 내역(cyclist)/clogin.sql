DROP TABLE clogin CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
DROP TABLE clogin;

-- cyclist의 c
/**********************************/
/* Table Name: 회원 로그인 내역 */
/**********************************/
CREATE TABLE clogin(
		cloginno                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		cyclistno                     		NUMBER(10)		 NOT NULL,
		ip                            		VARCHAR2(15)		 NOT NULL,
		logindate                     		DATE		 NOT NULL,
  FOREIGN KEY (cyclistno) REFERENCES cyclist (cyclistno)
);

COMMENT ON TABLE clogin is '회원 로그인 내역';
COMMENT ON COLUMN clogin.cloginno is '로그인 번호';
COMMENT ON COLUMN clogin.cyclistno is '회원 번호';
COMMENT ON COLUMN clogin.ip is '접속 IP';
COMMENT ON COLUMN clogin.logindate is '로그인 날짜';

DROP SEQUENCE clogin_seq;

CREATE SEQUENCE clogin_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

-- C
INSERT INTO clogin(cloginno, cyclistno, ip, logindate)
VALUES (clogin_seq.nextval, '1', '127.0.0.1', sysdate);
INSERT INTO clogin(cloginno, cyclistno, ip, logindate)
VALUES (clogin_seq.nextval, '3', '127.0.0.3', sysdate);

-- List
SELECT cloginno, cyclistno, ip, logindate
FROM clogin
ORDER BY logindate DESC;

-- List_cyclist
SELECT cloginno, cyclistno, ip, logindate
FROM clogin
WHERE cyclistno=1
ORDER BY logindate DESC;

-- R
SELECT cloginno, cyclistno, ip, logindate
FROM clogin
WHERE cloginno = 1;

-- D
DELETE FROM clogin
WHERE cloginno = 2;

DELETE FROM clogin;

commit;


commit;
  