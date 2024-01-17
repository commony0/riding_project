package dev.mvc.reply;

public class ReplyVO {
  
  private int replyno;
  private int detailno;
  private int cyclistno;
  private String content; 
  private String rdate;
  
  public int getReplyno() {
    return replyno;
  }
  public void setReplyno(int replyno) {
    this.replyno = replyno;
  }
  public int getDetailno() {
    return detailno;
  }
  public void setDetailno(int detailno) {
    this.detailno = detailno;
  }
  public int getCyclistno() {
    return cyclistno;
  }
  public void setCyclistno(int cyclistno) {
    this.cyclistno = cyclistno;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  
}
