package dev.mvc.mlogin;
/**
 * manager의 m
 * 
 */
public class MloginVO {

  private int mloginno;
  private int managerno;
  private String ip;
  private String logindate;
  
  public int getMloginno() {
    return mloginno;
  }
  public void setMloginno(int mloginno) {
    this.mloginno = mloginno;
  }
  public int getManagerno() {
    return managerno;
  }
  public void setManagerno(int managerno) {
    this.managerno = managerno;
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
