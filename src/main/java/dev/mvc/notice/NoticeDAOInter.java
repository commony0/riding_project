package dev.mvc.notice;

import java.util.ArrayList;

public interface NoticeDAOInter {

  public int create(NoticeVO noticeVO);
  
  public ArrayList<NoticeVO>list_all();
  
  public NoticeVO read(int noticeno);
  
  public int update(NoticeVO noticeVO);
  
  public int delete(int noticeno);
  
}
