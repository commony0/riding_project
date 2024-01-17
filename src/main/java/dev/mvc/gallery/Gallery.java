package dev.mvc.gallery;

import java.io.File;

public class Gallery {
  
//Windows, VMWare, AWS cloud 절대 경로 설정
  public static synchronized String getUploadDir() {
      String path = "";
      if (File.separator.equals("\\")) { // windows, 개발 환경의 파일 업로드 폴더
          // path = "C:/kd/deploy/riding_v3sbm3c/gallery/storage/";
          path="C:\\kd\\deploy\\riding_v3sbm3c\\gallery\\storage\\";
          // System.out.println("Windows 10: " + path);
          
      } else { // Linux, AWS, 서비스용 배치 폴더 
          // System.out.println("Linux");
          path = "/home/ubuntu/deploy/riding_v4sbm3c/gallery/storage/";
      }
      
      return path;
  }

}
