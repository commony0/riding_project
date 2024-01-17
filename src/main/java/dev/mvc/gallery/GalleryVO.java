package dev.mvc.gallery;

import org.springframework.web.multipart.MultipartFile;

public class GalleryVO {
  
  private int galleryno;
  private int cyclistno;
  private String title = "";
  private String content = "";
  private String rdate;
  
  /**
  이미지 파일
  <input type='file' class="form-control" name='file1MF' id='file1MF' 
             value='' placeholder="파일 선택">
  */
  private MultipartFile file1MF;
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String size1_label = "";
  
  private String file1="";
  private String file1saved="";
  private String thumb1="";
  private long size1;
  
  public int getGalleryno() {
    return galleryno;
  }
  public void setGalleryno(int galleryno) {
    this.galleryno = galleryno;
  }
  public int getCyclistno() {
    return cyclistno;
  }
  public void setCyclistno(int cyclistno) {
    this.cyclistno = cyclistno;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
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
  public String getFile1() {
    return file1;
  }
  public void setFile1(String file1) {
    this.file1 = file1;
  }
  public String getFile1saved() {
    return file1saved;
  }
  public void setFile1saved(String file1saved) {
    this.file1saved = file1saved;
  }
  public String getThumb1() {
    return thumb1;
  }
  public void setThumb1(String thumb1) {
    this.thumb1 = thumb1;
  }
  public long getSize1() {
    return size1;
  }
  public void setSize1(long size1) {
    this.size1 = size1;
  }
  public MultipartFile getFile1MF() {
    return file1MF;
  }
  public void setFile1MF(MultipartFile file1mf) {
    file1MF = file1mf;
  }
  public String getSize1_label() {
    return size1_label;
  }
  public void setSize1_label(String size1_label) {
    this.size1_label = size1_label;
  }
  
  
  
  
}
