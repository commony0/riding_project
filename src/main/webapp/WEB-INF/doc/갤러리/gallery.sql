DROP TABLE GALLERY CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
DROP TABLE GALLERY;

/**********************************/
/* Table Name: 갤러리 */
/**********************************/
CREATE TABLE GALLERY(
		GALLERYNO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		CYCLISTNO                     		NUMBER(10)		 NOT NULL,
		TITLE                         		VARCHAR2(1000)		 NOT NULL,
		CONTENT                       		CLOB		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		FILE1                         		VARCHAR2(50)		 NULL ,
		FILE1SAVED                    		VARCHAR2(50)		 NULL ,
		THUMB1                        		VARCHAR2(50)		 NULL ,
		SIZE1                         		NUMBER(10)		 NULL ,
  FOREIGN KEY (CYCLISTNO) REFERENCES CYCLIST (CYCLISTNO)
);

COMMENT ON TABLE GALLERY is '갤러리';
COMMENT ON COLUMN GALLERY.GALLERYNO is '갤러리 번호';
COMMENT ON COLUMN GALLERY.CYCLISTNO is '회원 번호';
COMMENT ON COLUMN GALLERY.TITLE is '제목';
COMMENT ON COLUMN GALLERY.CONTENT is '내용';
COMMENT ON COLUMN GALLERY.RDATE is '등록일';
COMMENT ON COLUMN GALLERY.FILE1 is '파일명';
COMMENT ON COLUMN GALLERY.FILE1SAVED is '저장된 파일명';
COMMENT ON COLUMN GALLERY.THUMB1 is '이미지 Preview';
COMMENT ON COLUMN GALLERY.SIZE1 is '파일 크기';

DROP SEQUENCE gallery_seq;

CREATE SEQUENCE gallery_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

INSERT INTO gallery(galleryno, cyclistno, title, content, rdate, file1, file1saved, thumb1, size1)
VALUES(gallery_seq.nextval, 1, '자전거 구매', '자전거 처음 타보는데 싼값에 구입했습니다.',sysdate, 
        'bike.jpg', 'bike_1.jpg', 'bike_t.jpg', 1000);