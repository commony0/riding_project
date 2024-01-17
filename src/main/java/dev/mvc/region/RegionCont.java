package dev.mvc.region;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.detail.Detail;
import dev.mvc.detail.DetailProcInter;
import dev.mvc.detail.DetailVO;
import dev.mvc.manager.ManagerProcInter;
import dev.mvc.tool.Tool;

@Controller
public class RegionCont {
  @Autowired // RegionProcInter interface 구현한 객체를 만들어 자동으로 할당해라.
  @Qualifier("dev.mvc.region.RegionProc")
  private RegionProcInter regionProc;
  
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc") // dev.mvc.manager.ManagerProc 라고 명명된 클래스
  private ManagerProcInter managerProc;
  
  @Autowired
  @Qualifier("dev.mvc.detail.DetailProc") // dev.mvc.manager.ManagerProc 라고 명명된 클래스
  private DetailProcInter detailProc;
  
  public RegionCont() {
    System.out.println("-> RegionCont created.");  
  }
  
  // FORM 출력, http://localhost:9092/region/create.do
//  @RequestMapping(value="/region/create.do", method = RequestMethod.GET)
//  @ResponseBody // 단순 문자열로 출력, jsp 파일명 조합이 발생하지 않음.
//  public String create() {
//    return "GET 방식 FORM 출력";
//  }
  // FORM 출력, http://localhost:9091/region/create.do
  @RequestMapping(value="/region/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/region/create"); // /WEB-INF/views/region/create.jsp
    
    return mav;
  }
  
  //FORM 데이터 처리, http://localhost:9092/region/create.do
  @RequestMapping(value="/region/create.do", method = RequestMethod.POST)
  public ModelAndView create(RegionVO regionVO) { // 자동으로 regionVO 객체 생성 및 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.regionProc.create(regionVO);
    System.out.println("-> cnt: " + cnt);
    
    if(cnt ==1 ) {
      //mav.addObject("code", "create_success"); // 키, 값 구조
      // mav.addObject("name", regionVO.getName()); // 카테고리 이름 jsp로 전송
      mav.setViewName("redirect:/region/list_all.do"); // 주소 자동 이동
    } else {
      mav.addObject("code", "create_fail");
      mav.setViewName("/region/msg"); // /WEB-INF/views/region/msg.jsp
    }
    
    mav.addObject("cnt", cnt);// request.setAttribute("cnt",cnt);
    
    return mav;
  }
  
  /**
   * 전체 목록
   * // http://localhost:9092/region/list_all.do
   * @return
   */
  @RequestMapping(value="/region/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if(this.managerProc.isManager(session) == true) {
      mav.setViewName("/region/list_all"); // /WEB-INF/views/region/list_all.jsp
      
      ArrayList<RegionVO> list = this.regionProc.list_all();
      mav.addObject("list", list);
    } else {
      mav.setViewName("/manager/login_need");
    }
    return mav;
  }
  
  /**
   * 조회
   * // http://localhost:9092/region/read.do?regionno=1
   * @return
   */
  @RequestMapping(value="/region/read.do", method = RequestMethod.GET)
  public ModelAndView read(int regionno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/region/read"); // /WEB-INF/views/region/read.jsp
    
    RegionVO regionVO = this.regionProc.read(regionno);
    mav.addObject("regionVO", regionVO);
    
    return mav;
  }
  
  /**
   * 수정폼
   * // http://localhost:9092/region/update.do?regionno=2
   * @return
   */
  @RequestMapping(value="/region/update.do", method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int regionno) { // int regionno = (int) request.getParameter = 
    ModelAndView mav = new ModelAndView();
    
    if(this.managerProc.isManager(session)==true) {
      mav.setViewName("/region/list_all_update"); // /WEB-INF/views/region/update.jsp
      
      RegionVO regionVO = this.regionProc.read(regionno); // 읽기와 동일
      mav.addObject("regionVO", regionVO);
      
      ArrayList<RegionVO> list = this.regionProc.list_all();
      mav.addObject("list",list);
    } else {
      mav.setViewName("/manager/login_need");
    }
    return mav;
  }
  /**
   * 수정 처리, http://localhost:9092/region/update.do
   * @param regionVO 수정할 내용
   * @return 수정된 레코드 갯수
   */
  @RequestMapping(value="/region/update.do", method = RequestMethod.POST)
  public ModelAndView update(RegionVO regionVO) { // 자동으로 regionVO 객체 생성
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.regionProc.update(regionVO);
    System.out.println("-> cnt: " + cnt);
    
    if(cnt ==1 ) {
      //mav.addObject("code", "update_success"); // 키, 값 구조
      //mav.addObject("name", regionVO.getName()); // 카테고리 이름 jsp로 전송
      mav.setViewName("redirect:/region/list_all.do"); 
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/region/msg"); // /WEB-INF/views/region/msg.jsp
    }
    mav.addObject("cnt", cnt);// request.setAttribute("cnt",cnt);
    
    return mav;
  }
  /**
   * 삭제폼
   * // http://localhost:9092/region/delete.do?regionno=2
   * @return
   */
  @RequestMapping(value="/region/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int regionno) { 
    ModelAndView mav = new ModelAndView();
    
    if(this.managerProc.isManager(session)==true) {
      mav.setViewName("/region/list_all_delete"); // /WEB-INF/views/region/list_all_delete.jsp
      
      RegionVO regionVO = this.regionProc.read(regionno); // 그대로 유지 - 삭제할 내용을 읽어와야 하니까
      mav.addObject("regionVO", regionVO);
      
      ArrayList<RegionVO> list = this.regionProc.list_all();
      mav.addObject("list", list);
      
   // 특정 카테고리에 속한 레코드 갯수를 리턴
      int count_by_regionno = this.detailProc.count_by_regionno(regionno);
      mav.addObject("count_by_regionno", count_by_regionno);
      
    } else {
      mav.setViewName("/manager/login_need");
    }
    return mav;
  }
  
//삭제 처리, 수정 처리를 복사하여 개발, http://localhost:9092/region/delete.do
  // 자식 테이블 레코드 삭제 -> 부모 테이블 레코드 삭제
  /**
   * 게시판 삭제
   * @param session
   * @param regionno 삭제할 카테고리 번호
   * @return
   */
  @RequestMapping(value="/region/delete.do", method=RequestMethod.POST)
  public ModelAndView delete_proc(HttpSession session, int regionno) { // <form> 태그의 값이 자동으로 저장됨
    ModelAndView mav = new ModelAndView();
    
    if (this.managerProc.isManager(session) == true) {
      ArrayList<DetailVO> list = this.detailProc.list_by_regionno(regionno); // 자식 레코드 목록 읽기
      
      for(DetailVO detailVO : list) { // 자식 레코드 관련 파일 삭제
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
      
      this.detailProc.delete_by_regionno(regionno); // 자식 레코드 삭제     
            
      int cnt = this.regionProc.delete(regionno); // 카테고리 삭제
      
      if (cnt == 1) {
        mav.setViewName("redirect:/region/list_all.do");       // 자동 주소 이동, Spring 재호출
        
      } else {
        mav.addObject("code", "delete_fail");
        mav.setViewName("/region/msg"); // /WEB-INF/views/region/msg.jsp
      }
      
      mav.addObject("cnt", cnt);
      
    } else {
      mav.setViewName("/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
    }
    return mav;
  }

  /**
   * 우선 순위 높이기 10등 ->1등, http://localhost:9092/region/update_seqno_forward.do?regionno=1
   * @param regionno 수정할 레코드 번호
   * @return 
   */
  @RequestMapping(value="/region/update_seqno_forward.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_forward(int regionno) { 
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.regionProc.update_seqno_forward(regionno); // 수정처리
    System.out.println("-> cnt: " + cnt);
    
    if(cnt ==1 ) {
      mav.setViewName("redirect:/region/list_all.do"); 
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/region/msg"); // /WEB-INF/views/region/msg.jsp
      
    }
    mav.addObject("cnt", cnt);// request.setAttribute("cnt",cnt);
    
    return mav;
}
  /**
   * 우선 순위 낮추기 1등 ->10등, http://localhost:9091/region/update_seqno_backward.do?regionno=1
   * @param regionno 수정할 레코드 번호
   * @return 
   */
  @RequestMapping(value="/region/update_seqno_backward.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_backward(int regionno) { 
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.regionProc.update_seqno_backward(regionno); // 수정처리
    System.out.println("-> cnt: " + cnt);
    
    if(cnt ==1 ) {
      mav.setViewName("redirect:/region/list_all.do"); 
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/region/msg"); // /WEB-INF/views/region/msg.jsp
      
    }
    mav.addObject("cnt", cnt);// request.setAttribute("cnt",cnt);
    
    return mav;
  }
  
  /**
   * 카테고리 공개 설정, http://localhost:9091/region/update_visible_y.do?regionno=1
   * @param regionno 수정할 레코드 번호
   * @return 
   */
  @RequestMapping(value="/region/update_visible_y.do", method = RequestMethod.GET)
  public ModelAndView update_visible_y(int regionno) { 
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.regionProc.update_visible_y(regionno); // 수정처리
    System.out.println("-> cnt: " + cnt);
    
    if(cnt ==1 ) {
      mav.setViewName("redirect:/region/list_all.do"); 
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/region/msg"); // /WEB-INF/views/region/msg.jsp
      
    }
    mav.addObject("cnt", cnt);// request.setAttribute("cnt",cnt);
    
    return mav;
}
  
  /**
   * 카테고리 비공개 설정, http://localhost:9091/region/update_visible_n.do?regionno=1
   * @param regionno 수정할 레코드 번호
   * @return 
   */
  @RequestMapping(value="/region/update_visible_n.do", method = RequestMethod.GET)
  public ModelAndView update_visible_n(int regionno) { 
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.regionProc.update_visible_n(regionno); // 수정처리
    System.out.println("-> cnt: " + cnt);
    
    if(cnt ==1 ) {
      mav.setViewName("redirect:/region/list_all.do"); 
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/region/msg"); // /WEB-INF/views/region/msg.jsp
      
    }
    mav.addObject("cnt", cnt);// request.setAttribute("cnt",cnt);
    
    return mav;
}
  
}