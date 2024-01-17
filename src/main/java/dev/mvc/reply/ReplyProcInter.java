package dev.mvc.reply;

import java.util.ArrayList;

public interface ReplyProcInter {

  public int create(ReplyVO replyVO);
  
  public ArrayList<ReplyVO>list();
  
  public ArrayList<ReplyVO>list_by_detailno(int detailno);
  
  public ArrayList<ReplyCyclistVO>list_by_detailno_id(int detailno);
  
  public ReplyVO read(int replyno);
  
  public int delete(int replyno);
  
}
