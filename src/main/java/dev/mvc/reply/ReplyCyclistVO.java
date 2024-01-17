package dev.mvc.reply;

public class ReplyCyclistVO {
  
  // cyclist
  private String id = "";
  
  // reply
  private int replyno;
  private int detailno;
  private int cyclistno;
  private String content; 
  private String rdate;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
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
