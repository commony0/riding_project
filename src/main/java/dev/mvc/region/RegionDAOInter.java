package dev.mvc.region;

import java.util.ArrayList;

public interface RegionDAOInter {
  /**
   *  등록, 추상메소드 -> Spring Boot이 구현함.
   * @param regionVO 객체
   * @return 등록된 레코드 갯수
   */
  public int create(RegionVO regionVO);
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<RegionVO> list_all();
  
  /**
   * 비회원/회원 SELECT LIST
   * @return
   */
  public ArrayList<RegionVO> list_all_y();
  
  /**
   * 조회, 수정폼
   * @param 
   * @return
   */
  public RegionVO read(int regionno);
  
  /**
   * 수정
   * @param regionVO
   * @return 수정된 레코드 갯수
   */
  public int update(RegionVO regionVO);
  
  /**
   * 삭제
   * @param regionno 삭제할 레코드 PK 번호
   * @return 삭제된 레코드 갯수
   */
  public int delete(int regionno);
  
  /**
   *  우선 순위 높이기 10등 ->1등
   * @param regionno
   * @return 수정된 레코드 갯수
   */
  public int update_seqno_forward(int regionno);
  
  /**
   *  우선 순위 낮추기 1등 ->10등
   * @param regionno
   * @return 수정된 레코드 갯수
   */
  public int update_seqno_backward(int regionno);
  
  /**
   *  카테고리 공개 설정
   * @param regionno
   * @return 
   */
  public int update_visible_y(int regionno);
  /**
   *  카테고리 비공개 설정
   * @param regionno
   * @return 
   */
  public int update_visible_n(int regionno);

}