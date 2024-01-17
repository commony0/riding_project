package dev.mvc.clogin;
/**
 * cyclist의 c
 * 
 */
public class CloginVO {
  
  private int cloginno;
  private int cyclistno;
  private String ip;
  private String logindate;
  
  public int getCloginno() {
    return cloginno;
  }
  public void setCloginno(int cloginno) {
    this.cloginno = cloginno;
  }
  public int getCyclistno() {
    return cyclistno;
  }
  public void setCyclistno(int cyclistno) {
    this.cyclistno = cyclistno;
  }
  public String getIp() {
    return ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public String getLogindate() {
    return logindate;
  }
  public void setLogindate(String logindate) {
    this.logindate = logindate;
  }

}
