/**********************************/
/* Table Name: 회원 */
/**********************************/
CREATE TABLE CYCLIST(
		CYCLISTNO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(30)		 NOT NULL,
		PASSWD                        		VARCHAR2(60)		 NOT NULL,
		MNAME                         		VARCHAR2(30)		 NOT NULL,
		TEL                           		VARCHAR2(14)		 NOT NULL,
		ZIPCODE                       		VARCHAR2(5)		 NULL ,
		ADDRESS1                      		VARCHAR2(80)		 NULL ,
		ADDRESS2                      		VARCHAR2(50)		 NULL ,
		MDATE                         		DATE		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL,
  CONSTRAINT SYS_C007906 UNIQUE (ID)
);

COMMENT ON TABLE CYCLIST is '회원';
COMMENT ON COLUMN CYCLIST.CYCLISTNO is '회원 번호';
COMMENT ON COLUMN CYCLIST.ID is '아이디';
COMMENT ON COLUMN CYCLIST.PASSWD is '패스워드';
COMMENT ON COLUMN CYCLIST.MNAME is '성명';
COMMENT ON COLUMN CYCLIST.TEL is '전화번호';
COMMENT ON COLUMN CYCLIST.ZIPCODE is '우편번호';
COMMENT ON COLUMN CYCLIST.ADDRESS1 is '주소1';
COMMENT ON COLUMN CYCLIST.ADDRESS2 is '주소2';
COMMENT ON COLUMN CYCLIST.MDATE is '가입일';
COMMENT ON COLUMN CYCLIST.GRADE is '등급';


/**********************************/
/* Table Name: 매니저 */
/**********************************/
CREATE TABLE MANAGER(
		MANAGERNO                     		NUMBER(5)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(20)		 NOT NULL,
		PASSWD                        		VARCHAR2(15)		 NOT NULL,
		MNAME                         		VARCHAR2(20)		 NOT NULL,
		MDATE                         		DATE		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL,
  CONSTRAINT SYS_C007474 UNIQUE (ID)
);

COMMENT ON TABLE MANAGER is '매니저';
COMMENT ON COLUMN MANAGER.MANAGERNO is '매니저 번호';
COMMENT ON COLUMN MANAGER.ID is '아이디';
COMMENT ON COLUMN MANAGER.PASSWD is '패스워드';
COMMENT ON COLUMN MANAGER.MNAME is '성명';
COMMENT ON COLUMN MANAGER.MDATE is '가입일';
COMMENT ON COLUMN MANAGER.GRADE is '등급';


/**********************************/
/* Table Name: 지역 */
/**********************************/
CREATE TABLE REGION(
		REGIONNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(30)		 NOT NULL,
		CNT                           		NUMBER(7)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		SEQNO                         		NUMBER(5)		 NOT NULL,
		VISIBLE                       		CHAR(1)		 NOT NULL
);

COMMENT ON TABLE REGION is '지역';
COMMENT ON COLUMN REGION.REGIONNO is '지역 번호';
COMMENT ON COLUMN REGION.NAME is '지역 이름';
COMMENT ON COLUMN REGION.CNT is '관련 자료수';
COMMENT ON COLUMN REGION.RDATE is '등록일';
COMMENT ON COLUMN REGION.SEQNO is '출력 순서';
COMMENT ON COLUMN REGION.VISIBLE is '출력 모드';


/**********************************/
/* Table Name: 세부내용 */
/**********************************/
CREATE TABLE DETAIL(
		DETAILNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		MANAGERNO                     		NUMBER(10)		 NOT NULL,
		REGIONNO                      		NUMBER(10)		 NOT NULL,
		TITLE                         		VARCHAR2(200)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
		RECOM                         		NUMBER(7)		 NOT NULL,
		CNT                           		NUMBER(7)		 NOT NULL,
		REPLYCNT                      		NUMBER(7)		 NOT NULL,
		PASSWD                        		VARCHAR2(15)		 NOT NULL,
		WORD                          		VARCHAR2(100)		 NULL ,
		RDATE                         		DATE		 NOT NULL,
		FILE1                         		VARCHAR2(100)		 NULL ,
		FILE1SAVED                    		VARCHAR2(100)		 NULL ,
		THUMB1                        		VARCHAR2(100)		 NULL ,
		SIZE1                         		NUMBER(10)		 NULL ,
		POINT                         		NUMBER(10)		 NULL ,
		MAP                           		VARCHAR2(1000)		 NULL ,
		YOUTUBE                       		VARCHAR2(1000)		 NULL ,
  FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO),
  FOREIGN KEY (REGIONNO) REFERENCES REGION (REGIONNO)
);

COMMENT ON TABLE DETAIL is '세부내용';
COMMENT ON COLUMN DETAIL.DETAILNO is '세부내용 번호';
COMMENT ON COLUMN DETAIL.MANAGERNO is '매니저 번호';
COMMENT ON COLUMN DETAIL.REGIONNO is '지역 번호';
COMMENT ON COLUMN DETAIL.TITLE is '제목';
COMMENT ON COLUMN DETAIL.CONTENT is '내용';
COMMENT ON COLUMN DETAIL.RECOM is '추천수';
COMMENT ON COLUMN DETAIL.CNT is '조회수';
COMMENT ON COLUMN DETAIL.REPLYCNT is '댓글수';
COMMENT ON COLUMN DETAIL.PASSWD is '패스워드';
COMMENT ON COLUMN DETAIL.WORD is '검색어';
COMMENT ON COLUMN DETAIL.RDATE is '등록일';
COMMENT ON COLUMN DETAIL.FILE1 is '메인 이미지';
COMMENT ON COLUMN DETAIL.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN DETAIL.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN DETAIL.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN DETAIL.POINT is '포인트';
COMMENT ON COLUMN DETAIL.MAP is '지도';
COMMENT ON COLUMN DETAIL.YOUTUBE is 'Youtube 영상';


/**********************************/
/* Table Name: 댓글 */
/**********************************/
CREATE TABLE REPLY(
		REPLYNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		DETAILNO                      		NUMBER(10)		 NOT NULL,
		CYCLISTNO                     		NUMBER(10)		 NOT NULL,
		CONTENT                       		VARCHAR2(2000)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
  FOREIGN KEY (CYCLISTNO) REFERENCES CYCLIST (CYCLISTNO),
  FOREIGN KEY (DETAILNO) REFERENCES DETAIL (DETAILNO)
);

COMMENT ON TABLE REPLY is '댓글';
COMMENT ON COLUMN REPLY.REPLYNO is '댓글 번호';
COMMENT ON COLUMN REPLY.DETAILNO is '세부내용 번호';
COMMENT ON COLUMN REPLY.CYCLISTNO is '회원 번호';
COMMENT ON COLUMN REPLY.CONTENT is '댓글 내용';
COMMENT ON COLUMN REPLY.RDATE is '등록일';


/**********************************/
/* Table Name: 공지사항 */
/**********************************/
CREATE TABLE NOTICE(
		NOTICENO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		MANAGERNO                     		NUMBER(5)		 NOT NULL,
		TITLE                         		VARCHAR2(1000)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
  FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
);

COMMENT ON TABLE NOTICE is '공지사항';
COMMENT ON COLUMN NOTICE.NOTICENO is '공지사항 번호';
COMMENT ON COLUMN NOTICE.MANAGERNO is '매니저 번호';
COMMENT ON COLUMN NOTICE.TITLE is '제목';
COMMENT ON COLUMN NOTICE.CONTENT is '내용';
COMMENT ON COLUMN NOTICE.RDATE is '등록일';


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


/**********************************/
/* Table Name: 회원 로그인 내역 */
/**********************************/
CREATE TABLE CLOGIN(
		CLOGINNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		CYCLISTNO                     		NUMBER(10)		 NOT NULL,
		IP                            		VARCHAR2(15)		 NOT NULL,
		LOGINDATE                     		DATE		 NOT NULL,
  FOREIGN KEY (CYCLISTNO) REFERENCES CYCLIST (CYCLISTNO)
);

COMMENT ON TABLE CLOGIN is '회원 로그인 내역';
COMMENT ON COLUMN CLOGIN.CLOGINNO is '로그인 번호';
COMMENT ON COLUMN CLOGIN.CYCLISTNO is '회원 번호';
COMMENT ON COLUMN CLOGIN.IP is '접속 IP';
COMMENT ON COLUMN CLOGIN.LOGINDATE is '로그인 날짜';


