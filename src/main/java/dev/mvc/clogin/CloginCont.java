package dev.mvc.clogin;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
/**
 * cyclist의 c
 * 
 */
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.cyclist.CyclistProcInter;
import dev.mvc.manager.ManagerProcInter;

@Controller
public class CloginCont {

  @Autowired
  @Qualifier("dev.mvc.clogin.CloginProc")
  private CloginProcInter cloginProc;
  
  @Autowired
  @Qualifier("dev.mvc.cyclist.CyclistProc") // 이름 지정
  private CyclistProcInter cyclistProc;
  
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc") // 이름 지정
  private ManagerProcInter managerProc;
  
  public CloginCont(){
    System.out.println("-> CloginCont created.");

  }
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * POST -> url -> GET -> 데이터 전송
   * @return
   */
  @RequestMapping(value="/clogin/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  
  /**
   * 전체 목록
   * // http://localhost:9092/clogin/list_all.do
   * @return
   */
  @RequestMapping(value="/clogin/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    if(this.managerProc.isManager(session) == true) {
      mav.setViewName("/clogin/list_all"); // /WEB-INF/views/clogin/list_all.jsp
      
      ArrayList<CloginVO> list = this.cloginProc.list_all();
      mav.addObject("list", list);
    } else {
      mav.setViewName("/manager/login_need");
    }
    return mav;
  }
  
  /**
   * 사용자별 목록
   * // http://localhost:9092/clogin/list_all_c.do?cyclistno=1
   * @return
   */
  @RequestMapping(value="/clogin/list_all_c.do", method = RequestMethod.GET)
  public ModelAndView list_all_c(HttpSession session, HttpServletRequest request) {
    ModelAndView mav = new ModelAndView();
    int cyclistno = 0;
    if(this.cyclistProc.isCyclist(session) || this.managerProc.isManager(session) == true) {
      mav.setViewName("/clogin/list_all_c"); // /WEB-INF/views/clogin/list_all_c.jsp
      
            
      if (this.cyclistProc.isCyclist(session)) { // 회원으로 로그인
        // session을 사용하여 현재 로그인한 사용자의 cyclistno값만 읽음으로 다른 사용자의
        // 정보를 조회할 수 없음
        cyclistno = (int)session.getAttribute("cyclistno"); // 취사선택
         
      } else if (this.managerProc.isManager(session)) { // 관리자로 로그인
        // 관리자는 모든 회원의 정보를 조회 할 수 있어야함으로 parameter로 회원번호를 이용하여 조회
        cyclistno = Integer.parseInt(request.getParameter("cyclistno")); // 취사선택
         
      }
      
      ArrayList<CloginVO> list = this.cloginProc.list_all_c(cyclistno);
      mav.addObject("list", list);

    } else {
      mav.setViewName("/cyclist/login_need");
    }
    return mav;
  }
  
  /**
   * 삭제폼
   * // http://localhost:9092/clogin/delete.do?cloginno=2
   * @return
   */
  @RequestMapping(value="/clogin/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int cloginno) { 
    ModelAndView mav = new ModelAndView();
    
    if(this.managerProc.isManager(session)==true) {
      mav.setViewName("/clogin/delete"); // /WEB-INF/views/clogin/delete.jsp
      
      CloginVO cloginVO = this.cloginProc.read(cloginno); 
      mav.addObject("cloginVO", cloginVO);
      
//      ArrayList<CloginVO> list = this.cloginProc.list_all();
//      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/manager/login_need");
    }
    return mav;
  }
  /**
   * 삭제 처리, http://localhost:9092/clogin/delete.do
   * @param cloginVO 삭제할 내용
   * @return 
   */
  @RequestMapping(value="/clogin/delete.do", method = RequestMethod.POST)
  public ModelAndView delete_proc(HttpSession session,int cloginno) { // 자동으로 cloginVO 객체 생성
    ModelAndView mav = new ModelAndView();
//    CloginVO cloginVO = this.cloginProc.read(cloginno);
    
    if (this.managerProc.isManager(session)) { // 관리자 로그인 확인
//      int managerno = (int)session.getAttribute("managerno"); // FK
//      cloginVO.setManagerno(managerno); 
      
      int cnt = this.cloginProc.delete(cloginno); 
      System.out.println("-> cnt: " + cnt);

      if(cnt == 1) {
        mav.setViewName("redirect:/clogin/list_all.do"); // 페이지 자동 이동
      } else {
        mav.addObject("code", "delete_fail");
        mav.setViewName("redirect:/clogin/msg.do"); // 페이지 자동 이동
      }       
      mav.addObject("cnt", cnt);
    } else { 
        mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
        mav.setViewName("redirect:/clogin/msg.do");
    }

    return mav; // forward
  }
  
  
  
}
