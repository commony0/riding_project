package dev.mvc.detail;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.manager.ManagerProcInter;
import dev.mvc.region.RegionProcInter;
import dev.mvc.region.RegionVO;
import dev.mvc.reply.ReplyCyclistVO;
import dev.mvc.reply.ReplyProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class DetailCont {
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc") //@Component("dev.mvc.manager.ManagerProc")
  private ManagerProcInter managerProc;
  
  @Autowired
  @Qualifier("dev.mvc.region.RegionProc") //@Component("dev.mvc.region.RegionProc")
  private RegionProcInter regionProc;
  
  @Autowired
  @Qualifier("dev.mvc.detail.DetailProc") //@Component("dev.mvc.detail.DetailProc")
  private DetailProcInter detailProc;
  
  @Autowired
  @Qualifier("dev.mvc.reply.ReplyProc") //@Component("dev.mvc.detail.DetailProc")
  private ReplyProcInter replyProc;
  
  public DetailCont () {
    System.out.println("-> DetailCont created.");
  }
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * POST -> url -> GET -> 데이터 전송
   * @return
   */
  @RequestMapping(value="/detail/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  // 등록 폼, detail 테이블은 FK로 regionno를 사용함.
  // http://localhost:9092/detail/create.do  X
  // http://localhost:9092/detail/create.do?regionno=1 // regionno 변수를 보내는 목적이 무엇인가
  // http://localhost:9092/detail/create.do?regionno=2
  // http://localhost:9092/detail/create.do?regionno=3
  @RequestMapping(value="/detail/create.do", method = RequestMethod.GET)
  public ModelAndView create(int regionno) {
    ModelAndView mav = new ModelAndView();

    RegionVO regionVO = this.regionProc.read(regionno); 
    mav.addObject("regionVO", regionVO);
    
    mav.setViewName("/detail/create"); 
    
    return mav;
  }
  
  /**
   * 등록 처리 http://localhost:9092/detail/create.do?regionno=3
   * 
   * @return
   */
  @RequestMapping(value = "/detail/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, DetailVO detailVO) {
    ModelAndView mav = new ModelAndView();
    
    if (managerProc.isManager(session)) { // 관리자로 로그인한경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image
      String file1saved = "";   // 저장된 파일명, image
      String thumb1 = "";     // preview image

      String upDir =  Detail.getUploadDir();
      System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = detailVO.getFile1MF();
      
      file1 = mf.getOriginalFilename(); // 원본 순수 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 file1: " + file1);
      
      // 여기 파일 업로드 안되면 글 자체가 안올라가는 문제 해결 필요*********************
      if(Tool.checkUploadFile(file1) == true) {// 업로드 가능 파일인지 검사
        long size1 = mf.getSize();  // 파일 크기
        
        if (size1 > 0) { // 파일 크기 체크
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
          file1saved = Upload.saveFileSpring(mf, upDir); 
          
          if (Tool.isImage(file1saved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            thumb1 = Tool.preview(upDir, file1saved, 200, 150); 
          }
          
        }    
        
        detailVO.setFile1(file1);   // 순수 원본 파일명
        detailVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
        detailVO.setThumb1(thumb1);      // 원본이미지 축소판
        detailVO.setSize1(size1);  // 파일 크기
        // ------------------------------------------------------------------------------
        // 파일 전송 코드 종료
        // ------------------------------------------------------------------------------
        
        // Call By Reference: 메모리 공유, Hashcode 전달
        int managerno = (int)session.getAttribute("managerno"); // managerno FK
        detailVO.setManagerno(managerno);
        int cnt = this.detailProc.create(detailVO); 
        
        // ------------------------------------------------------------------------------
        // PK의 return
        // ------------------------------------------------------------------------------
        // System.out.println("--> detailno: " + detailVO.getDetailno());
        // mav.addObject("detailno", detailVO.getDetailno()); // redirect parameter 적용
        // ------------------------------------------------------------------------------
        
        if (cnt == 1) {
            mav.addObject("code", "create_success");
            // regionProc.increaseCnt(detailVO.getRegionno()); // 글수 증가
        } else {
          mav.addObject("code", "create_fail");
        }
        mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
        
        // System.out.println("--> regionno: " + detailVO.getRegionno());
        // redirect시에 hidden tag로 보낸것들이 전달이 안됨으로 request에 다시 저장
        mav.addObject("regionno", detailVO.getRegionno()); // redirect parameter 적용
        
        mav.addObject("url", "/detail/msg"); // msg.jsp, redirect parameter 적용
        mav.setViewName("redirect:/detail/msg.do"); 
        } else {
          mav.addObject("cnt", 0);
          mav.addObject("regionno", detailVO.getRegionno());
          mav.addObject("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
          mav.addObject("url", "/detail/msg"); // msg.jsp, redirect parameter 적용
          mav.setViewName("redirect:/detail/msg.do"); // Post -> Get - param...
        }
      } else {
        mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
        mav.setViewName("redirect:/detail/msg.do"); 
    }    
    return mav; // forward
  }
  
  /**
   * 전체 목록, 관리자만 사용 가능
   * // http://localhost:9092/detail/list_all.do
   * @return
   */
  @RequestMapping(value="/detail/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if(this.managerProc.isManager(session) == true) {
      mav.setViewName("/detail/list_all"); // /WEB-INF/views/detail/list_all.jsp
      
      ArrayList<DetailVO> list = this.detailProc.list_all();
      
   // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
      for (DetailVO detailVO : list) {
        String title = detailVO.getTitle();
        String content = detailVO.getContent();
        
        title = Tool.convertChar(title);  // 특수 문자 처리
        content = Tool.convertChar(content); 
        
        detailVO.setTitle(title);
        detailVO.setContent(content);  
      }
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
    }
    return mav;
  }
  
  /**
   * 목록 + 검색 + 페이징 지원
   * 검색하지 않는 경우
   * http://localhost:9092/detail/list_by_regionno.do?regionno=2&word=&now_page=1
   * 검색하는 경우
   * http://localhost:9092/detail/list_by_regionno.do?regionno=2&word=자전거&now_page=1
   * 
   * @param regionno
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/detail/list_by_regionno.do", method = RequestMethod.GET)
  public ModelAndView list_by_regionno(DetailVO detailVO) {
    ModelAndView mav = new ModelAndView();

    // 검색 목록
    ArrayList<DetailVO> list = detailProc.list_by_regionno_search_paging(detailVO);

    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (DetailVO vo : list) {
      String title = vo.getTitle();
      String content = vo.getContent();
      
      title = Tool.convertChar(title);  // 특수 문자 처리
      content = Tool.convertChar(content); 
      
      vo.setTitle(title);
      vo.setContent(content);  
    }
    
    mav.addObject("list", list);

    RegionVO regionVO = regionProc.read(detailVO.getRegionno());
    mav.addObject("regionVO", regionVO);
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("regionno", detailVO.getRegionno());
    hashMap.put("word", detailVO.getWord());
    
    int search_count = this.detailProc.search_count(hashMap);
    mav.addObject("search_count", search_count);
    
    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param regionno 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @param list_file 목록 파일명
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = detailProc.pagingBox(detailVO.getRegionno(), detailVO.getNow_page(), detailVO.getWord(), "list_by_regionno.do", search_count);
    mav.addObject("paging", paging);

    // mav.addObject("now_page", now_page);
    
    mav.setViewName("/detail/list_by_regionno");  // /detail/list_by_regionno.jsp

    return mav;
  }
  
  /**
   * 목록 + 검색 + 페이징 지원 +Grid
   * 검색하지 않는 경우
   * http://localhost:9092/detail/list_by_regionno_grid.do?regionno=2&word=&now_page=1
   * 검색하는 경우
   * http://localhost:9092/detail/list_by_regionno_grid.do?regionno=2&word=자전거&now_page=1
   * 
   * @param regionno
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/detail/list_by_regionno_grid.do", method = RequestMethod.GET)
  public ModelAndView list_by_regionno_grid(DetailVO detailVO) {
    ModelAndView mav = new ModelAndView();

    // 검색 목록
    ArrayList<DetailVO> list = detailProc.list_by_regionno_search_paging(detailVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (DetailVO vo : list) {
      String title = vo.getTitle();
      String content = vo.getContent();
      
      title = Tool.convertChar(title);  // 특수 문자 처리
      content = Tool.convertChar(content); 
      
      vo.setTitle(title);
      vo.setContent(content);  
    }
    
    mav.addObject("list", list);

    RegionVO regionVO = regionProc.read(detailVO.getRegionno());
    mav.addObject("regionVO", regionVO);
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("regionno", detailVO.getRegionno());
    hashMap.put("word", detailVO.getWord());
    
    int search_count = this.detailProc.search_count(hashMap);
    mav.addObject("search_count", search_count);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param regionno 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @param list_file 목록 파일명
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = detailProc.pagingBox(detailVO.getRegionno(), detailVO.getNow_page(), detailVO.getWord(), "list_by_regionno_grid.do", search_count);
    mav.addObject("paging", paging);

    // mav.addObject("now_page", now_page);
    
    mav.setViewName("/detail/list_by_regionno_grid");  // /detail/list_by_regionno.jsp

    return mav;
  }

  /**
   * 조회
   * // http://localhost:9092/detail/read.do?detailno=1
   * @return
   */
  @RequestMapping(value="/detail/read.do", method = RequestMethod.GET)
  public ModelAndView read(int detailno, ReplyCyclistVO replyCyclistVO) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/detail/read"); // /WEB-INF/views/detail/read.jsp
    
    DetailVO detailVO = this.detailProc.read(detailno);
    
    String title = detailVO.getTitle();
    String content = detailVO.getContent();
    
    title = Tool.convertChar(title);  // 특수 문자 처리
    content = Tool.convertChar(content); 
    
    detailVO.setTitle(title);
    detailVO.setContent(content);
    
    long size1 = detailVO.getSize1();
    String size1_label = Tool.unit(size1);
    detailVO.setSize1_label(size1_label);
    
    mav.addObject("detailVO", detailVO);
    
    RegionVO regionVO = this.regionProc.read(detailVO.getRegionno());
    mav.addObject("regionVO", regionVO);
    
    ArrayList<ReplyCyclistVO> list = this.replyProc.list_by_detailno_id(replyCyclistVO.getDetailno());
    
    for (ReplyCyclistVO vo : list) {
      String cont = vo.getContent();
      cont = Tool.convertChar(cont); 
      vo.setContent(cont);  
    }
    mav.addObject("list", list);
    
    return mav;
  }

  /**
   * 맵 등록/수정/삭제 폼
   * http://localhost:9092/detail/map.do?detailno=1
   * @return
   */
  @RequestMapping(value="/detail/map.do", method=RequestMethod.GET )
  public ModelAndView map(int detailno) {
    ModelAndView mav = new ModelAndView();

    DetailVO detailVO = this.detailProc.read(detailno); // map 정보 읽어 오기
    mav.addObject("detailVO", detailVO); // request.setAttribute("detailVO", detailVO);

    RegionVO regionVO = this.regionProc.read(detailVO.getRegionno()); // 그룹 정보 읽기
    mav.addObject("regionVO", regionVO); 

    mav.setViewName("/detail/map"); // /WEB-INF/views/detail/map.jsp
        
    return mav;
}
  
  /**
   * MAP 등록/수정/삭제 처리
   * http://localhost:9092/detail/map.do
   * @param detailVO
   * @return
   */
  @RequestMapping(value="/detail/map.do", method = RequestMethod.POST)
  public ModelAndView map_update(int detailno, String map) {
    ModelAndView mav = new ModelAndView();
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("detailno", detailno);
    hashMap.put("map", map);
    
    this.detailProc.map(hashMap);
    
    mav.setViewName("redirect:/detail/read.do?detailno=" + detailno); 
    // /webapp/WEB-INF/views/detail/read.jsp
    
    return mav;
  }
  /**
   * Youtube 등록/수정/삭제 폼
   * http://localhost:9092/detail/youtube.do?detailno=1
   * @return
   */
  @RequestMapping(value="/detail/youtube.do", method=RequestMethod.GET )
  public ModelAndView youtube(int detailno) {
    ModelAndView mav = new ModelAndView();

    DetailVO detailVO = this.detailProc.read(detailno); // youtube 정보 읽어 오기
    mav.addObject("detailVO", detailVO); // request.setAttribute("detailVO", detailVO);

    RegionVO regionVO = this.regionProc.read(detailVO.getRegionno()); // 그룹 정보 읽기
    mav.addObject("regionVO", regionVO); 

    mav.setViewName("/detail/youtube"); // /WEB-INF/views/detail/youtube.jsp
        
    return mav;
}
  
  /**
   * Youtube 등록/수정/삭제 처리
   * http://localhost:9092/detail/youtube.do
   * @param detailno 글 번호
   * @param youtube Youtube url의 소스 코드
   * @return
   */
  @RequestMapping(value="/detail/youtube.do", method = RequestMethod.POST)
  public ModelAndView youtube_update(int detailno, String youtube) {
    ModelAndView mav = new ModelAndView();
    
    if (youtube.trim().length() > 0) {  // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
      youtube = Tool.youtubeResize(youtube, 640); // youtube 영상의 크기를 width 기준 640 px로 변경 
    }
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("detailno", detailno);
    hashMap.put("youtube", youtube);

    this.detailProc.youtube(hashMap);
    
    mav.setViewName("redirect:/detail/read.do?detailno=" + detailno); 
    // /webapp/WEB-INF/views/detail/read.jsp
    
    return mav;
  }
  
  /**
  * 수정 폼
  * http://localhost:9092/detail/update_text.do?detailno=1
  * 
  * @return
  */
  @RequestMapping(value = "/detail/update_text.do", method = RequestMethod.GET)
  public ModelAndView update_text(HttpSession session, int detailno) {
  ModelAndView mav = new ModelAndView();
   
  if (managerProc.isManager(session)) { // 관리자로 로그인한경우}
    DetailVO detailVO = this.detailProc.read(detailno);
    mav.addObject("detailVO", detailVO);
     
    RegionVO regionVO = this.regionProc.read(detailVO.getRegionno());
    mav.addObject("regionVO", regionVO);
     
    mav.setViewName("/detail/update_text"); // /WEB-INF/views/detail/update_text.jsp
    // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
    // mav.addObject("content", content);
     
  } else {
      mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
      mav.setViewName("redirect:/detail/msg.do"); 
  }

   return mav; // forward
  }
 
  /**
  * 수정 처리
  * http://localhost:9092/detail/update_text.do?detailno=1
  * 
  * @return
  */
  @RequestMapping(value = "/detail/update_text.do", method = RequestMethod.POST)
  public ModelAndView update_text(HttpSession session, DetailVO detailVO) {
    ModelAndView mav = new ModelAndView();
   
    // System.out.println("-> word: " + detailVO.getWord());
   
    if (this.managerProc.isManager(session)) { // 관리자 로그인 확인
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("detailno", detailVO.getDetailno());
      hashMap.put("passwd", detailVO.getPasswd());
   
      if (this.detailProc.password_check(hashMap) == 1) { // 패스워드 일치
        this.detailProc.update_text(detailVO);  // 글수정
        
        // mav 객체 이용
        mav.addObject("detailno", detailVO.getDetailno());
        mav.addObject("regionno", detailVO.getRegionno());
          mav.setViewName("redirect:/detail/read.do"); // 페이지 자동 이동
      } else { // 패스워드 불일치
        mav.addObject("code", "passwd_fail"); 
        mav.addObject("cnt", 0); 
        mav.addObject("url","/detail/msg");
        mav.setViewName("redirect:/detail/msg.do");  // POST -> GET -> JSP 출력
      }    
    } else { // 정상적인 로그인이 아닌 경우 로그인 유도
        mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
        mav.setViewName("redirect:/detail/msg.do");
    }
   
    mav.addObject("now_page", detailVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 다시하번 데이터 저장 ★
   
    // URL에 파라미터의 전송
    // mav.setViewName("redirect:/detail/read.do?detailno=" + detailVO.getDetailno() + "&regionno=" + detailVO.getRegionno());             
   
    return mav; // forward
  }
  /**
  * 파일 수정 폼
  * http://localhost:9092/detail/update_file.do?detailno=1
  * 
  * @return
  */
  @RequestMapping(value = "/detail/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(HttpSession session, int detailno) {
    ModelAndView mav = new ModelAndView();
   
    if (this.managerProc.isManager(session)) {
      DetailVO detailVO = this.detailProc.read(detailno);
      mav.addObject("detailVO", detailVO);
     
      RegionVO regionVO = this.regionProc.read(detailVO.getRegionno());
      mav.addObject("regionVO", regionVO);
     
      mav.setViewName("/detail/update_file"); // /WEB-INF/views/detail/update_file.jsp
    } else {
      mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
      mav.setViewName("redirect:/detail/msg.do");
    }
    return mav; // forward
  }
 
  /**
   * 파일 수정 처리 http://localhost:9092/detail/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/detail/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, DetailVO detailVO) {
    ModelAndView mav = new ModelAndView();
     
    if (this.managerProc.isManager(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      DetailVO detailVO_old = detailProc.read(detailVO.getDetailno());
       
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = detailVO_old.getFile1saved();  // 실제 저장된 파일명
      String thumb1 = detailVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
         
      String upDir =  Detail.getUploadDir(); // C:/kd/deploy/resort_v2sbm3c/detail/storage/
       
      Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
      Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
           
      // -------------------------------------------------------------------
      // 파일 전송 시작
      // -------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image
  
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = detailVO.getFile1MF();
           
      file1 = mf.getOriginalFilename(); // 원본 파일명
      size1 = mf.getSize();  // 파일 크기
           
      if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
         
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
          thumb1 = Tool.preview(upDir, file1saved, 250, 200); 
        }
         
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        file1="";
        file1saved="";
        thumb1="";
        size1=0;
      }
           
      detailVO.setFile1(file1);
      detailVO.setFile1saved(file1saved);
      detailVO.setThumb1(thumb1);
      detailVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
           
      this.detailProc.update_file(detailVO); // Oracle 처리
 
      mav.addObject("detailno", detailVO.getDetailno());
      mav.addObject("regionno", detailVO.getRegionno());
      mav.setViewName("redirect:/detail/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/manager/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/detail/msg.do"); // GET
    }
  
    // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
    mav.addObject("now_page", detailVO.getNow_page());
     
    return mav; // forward
  }
  /**
   * 파일 삭제 폼
   * http://localhost:9092/detail/delete.do?detailno=1
   * 
   * @return
   */
  @RequestMapping(value = "/detail/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int detailno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.managerProc.isManager(session)) {
      DetailVO detailVO = this.detailProc.read(detailno);
      mav.addObject("detailVO", detailVO);
      
      RegionVO regionVO = this.regionProc.read(detailVO.getRegionno());
      mav.addObject("regionVO", regionVO);
      
      mav.setViewName("/detail/delete"); // /WEB-INF/views/detail/delete.jsp
    } else {
      mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
      mav.setViewName("redirect:/detail/msg.do");
    }
    return mav; // forward
  }
  
  /**
   * 삭제 처리 http://localhost:9092/detail/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/detail/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(DetailVO detailVO) {
    ModelAndView mav = new ModelAndView();
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    DetailVO detailVO_read = detailProc.read(detailVO.getDetailno());
        
    String file1saved = detailVO.getFile1saved();
    String thumb1 = detailVO.getThumb1();
    
    String uploadDir = Detail.getUploadDir();
    Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
    Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
        
    this.detailProc.delete(detailVO.getDetailno()); // DBMS 삭제
        
    // -------------------------------------------------------------------------------------
    // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
    // -------------------------------------------------------------------------------------    
    // 마지막 페이지의 마지막 10번째 레코드를 삭제후
    // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
    // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
    int now_page = detailVO.getNow_page();
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("regionno", detailVO.getRegionno());
    hashMap.put("word", detailVO.getWord());
    
    if (detailProc.search_count(hashMap) % Detail.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }
    // -------------------------------------------------------------------------------------

    mav.addObject("regionno", detailVO.getRegionno());
    mav.addObject("now_page", now_page);
    mav.setViewName("redirect:/detail/list_by_regionno.do"); 
    
    return mav;
  }
  
  //http://localhost:9092/detail/delete_by_regionno.do?regionno=1
  // 파일 삭제 -> 레코드 삭제
  @RequestMapping(value = "/detail/delete_by_regionno.do", method = RequestMethod.GET)
  public String delete_by_regionno(int regionno) {
    ArrayList<DetailVO> list = this.detailProc.list_by_regionno(regionno);
    
    for(DetailVO detailVO : list) {
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = detailVO.getFile1saved();
      String thumb1 = detailVO.getThumb1();
      
      String uploadDir = Detail.getUploadDir();
      Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
      Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
    }
    
    int cnt = this.detailProc.delete_by_regionno(regionno);
    System.out.println("-> count: " + cnt);
    
    return "";
  
  }
  
  /**
   * 가장 가까운 경로 추천
   * // http://localhost:9092/detail/recommend.do
   * @return
   */
  @RequestMapping(value="/detail/recommend.do", method = RequestMethod.GET)
  public ModelAndView recommend() {
ModelAndView mav = new ModelAndView();
    mav.setViewName("/detail/recommend"); 

    return mav;
  
  }
}