package dev.mvc.detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 개발자가 구현하는 부분
 * @author commo
 *
 */
public interface DetailProcInter {
  /**
   * 등록, 추상메소드
   * @param detailVO
   * @return
   */
  public int create(DetailVO detailVO);
  
  /**
   * 모든 카테고리의 등록된 글목록
   * @return
   */
  public ArrayList<DetailVO> list_all();
  
  /**
   * 카테고리별 등록된 글 목록
   * @param regionno
   * @return
   */
  public ArrayList<DetailVO> list_by_regionno(int regionno);
  
  /**
   * 조회
   * @param detailno
   * @return
   */
  public DetailVO read(int detailno);
  
  /**
   * map 등록, 수정, 삭제
   * @param 수정된 레코드 갯수
   * @return
   */
  public int map (HashMap<String, Object> map);
  
  /**
   * youtube 등록, 수정, 삭제
   * @param 수정된 레코드 갯수
   * @return
   */
  public int youtube (HashMap<String, Object> map);
  
  /**
   * 카테고리별 검색 목록
   * @param map
   * @return
   */
  public ArrayList<DetailVO> list_by_regionno_search(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count(HashMap<String, Object> hashMap);
  
  /**
   *  특정 카테고리의 검색 + 페이징된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<DetailVO> list_by_regionno_search_paging(DetailVO detailVO);
  
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param regionno          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명 
   * @param search_count 검색 레코드수
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int regionno, int now_page, String word, String list_file, int search_count);
  
  /**
   * 패스워드 검사
   * @param hashMap
   * @return 1: 패스워드 일치, 0: 패스워드 불일치
   */
  public int password_check(HashMap<String, Object> hashMap);
  
  /**
   * 글 정보 수정
   * @param detailVO
   * @return 처리된 레코드 갯수
   */
  public int update_text(DetailVO detailVO);
  
  /**
   * 파일 정보 수정
   * @param detailVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(DetailVO detailVO);
  
  /**
   * 삭제
   * @param detailno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int detailno);
  
  /**
   * FK regionno 값이 같은 레코드 갯수 산출
   * @param regionno
   * @return
   */
  public int count_by_regionno(int regionno);
  
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param regionno
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_regionno(int regionno);
}
