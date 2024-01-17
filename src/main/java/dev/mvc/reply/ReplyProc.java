package dev.mvc.reply;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("dev.mvc.reply.ReplyProc")
public class ReplyProc implements ReplyProcInter {
  @Autowired
  private ReplyDAOInter replyDAO; 

  @Override
  public int create(ReplyVO replyVO) {
    int cnt = replyDAO.create(replyVO);
    return cnt;
  }

  @Override
  public ArrayList<ReplyVO> list() {
    ArrayList<ReplyVO> list = this.replyDAO.list();
    return list;
  }

  @Override
  public ArrayList<ReplyVO> list_by_detailno(int detailno) {
    ArrayList<ReplyVO> list = this.replyDAO.list_by_detailno(detailno);
    return list;
  }

  @Override
  public ArrayList<ReplyCyclistVO> list_by_detailno_id(int detailno) {
    ArrayList<ReplyCyclistVO> list = this.replyDAO.list_by_detailno_id(detailno);
    return list;
  }
  
  @Override
  public ReplyVO read(int replyno) {
    ReplyVO replyVO = this.replyDAO.read(replyno);
    return replyVO;
  }

  @Override
  public int delete(int replyno) {
    int cnt = this.replyDAO.delete(replyno);
    return cnt;
  }

  

}
