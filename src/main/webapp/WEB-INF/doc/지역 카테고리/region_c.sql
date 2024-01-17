/**********************************/
/* Table Name: 지역 */
/**********************************/
DROP TABLE REGION;

CREATE TABLE REGION(
		REGIONNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(30)		 NOT NULL,
		CNT                           		NUMBER(7)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL
);

COMMENT ON TABLE REGION is '지역';
COMMENT ON COLUMN REGION.REGIONNO is '지역 번호';
COMMENT ON COLUMN REGION.NAME is '지역 이름';
COMMENT ON COLUMN REGION.CNT is '관련 자료수';
COMMENT ON COLUMN REGION.RDATE is '등록일';

DROP SEQUENCE REGION_SEQ;

CREATE SEQUENCE REGION_SEQ
    START WITH 1    -- 시작번호
    INCREMENT BY 1  -- 증가값
    MAXVALUE 999999999  -- 최대값: 999999999
    CACHE 2         -- 2번은 메모리에서만 계산
    NOCYCLE;        -- 다시 1부터 생성되는 것을 방지

-- CREATE
INSERT INTO region(regionno, name, cnt, rdate) 
VALUES(region_seq.nextval, '경기도', 0, sysdate);
INSERT INTO region(regionno, name, cnt, rdate) 
VALUES(region_seq.nextval, '서울', 0, sysdate);
INSERT INTO region(regionno, name, cnt, rdate) 
VALUES(region_seq.nextval, '인천', 0, sysdate);

SELECT * FROM region;
SELECT regionno, name, cnt, rdate FROM region ORDER BY regionno ASC;

-- READ
SELECT regionno, name, cnt, rdate 
FROM region
WHERE regionno=1;

-- UPDATE
UPDATE region SET name= '인천시 부평구', cnt=1 WHERE regionno=3;

SELECT regionno, name, cnt, rdate 
FROM region
WHERE regionno=3;

-- DELETE
DELETE FROM region WHERE regionno=1;
DELETE FROM region WHERE regionno >=10;

COMMIT;
-- COUNT

SELECT * FROM region;