package dev.mvc.region;

public class RegionVO {
  private int regionno;
  private String name;
  private int cnt;
  private String rdate;
  private int seqno;
  private String visible;
  
  public int getSeqno() {
    return seqno;
  }
  public void setSeqno(int seqno) {
    this.seqno = seqno;
  }
  public int getRegionno() {
    return regionno;
  }
  public String getVisible() {
    return visible;
  }
  public void setVisible(String visible) {
    this.visible = visible;
  }
  public void setRegionno(int regionno) {
    this.regionno = regionno;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getCnt() {
    return cnt;
  }
  public void setCnt(int cnt) {
    this.cnt = cnt;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  @Override
  public String toString() {
    return "RegionVO [regionno=" + regionno + ", name=" + name + ", cnt=" + cnt + ", rdate=" + rdate + ", seqno="
        + seqno + ", visible=" + visible + "]";
  }


}