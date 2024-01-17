package dev.mvc.mlogin;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.manager.ManagerProcInter;

@Controller
public class MloginCont {

  @Autowired
  @Qualifier("dev.mvc.mlogin.MloginProc")
  private MloginProcInter mloginProc;
  
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc") // 이름 지정
  private ManagerProcInter managerProc;
  
  public MloginCont(){
    System.out.println("-> MloginCont created.");
  }
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * POST -> url -> GET -> 데이터 전송
   * @return
   */
  @RequestMapping(value="/mlogin/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  /**
   * 전체 목록
   * // http://localhost:9092/mlogin/list_all.do
   * @return
   */
  @RequestMapping(value="/mlogin/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    if(this.managerProc.isManager(session) == true) {
      mav.setViewName("/mlogin/list_all"); // /WEB-INF/views/mlogin/list_all.jsp
      
      ArrayList<MloginVO> list = this.mloginProc.list_all();
      mav.addObject("list", list);
    } else {
      mav.setViewName("/manager/login_need");
    }
    return mav;
  }
  
  /**
   * 매니저별 목록
   * // http://localhost:9092/mlogin/list_all_m.do?managerno=1
   * @return
   */
  @RequestMapping(value="/mlogin/list_all_m.do", method = RequestMethod.GET)
  public ModelAndView list_all_m(HttpSession session, int managerno) {
    ModelAndView mav = new ModelAndView();
    if(this.managerProc.isManager(session) == true) {
      mav.setViewName("/mlogin/list_all_m"); // /WEB-INF/views/mlogin/list_all_m.jsp
      
      ArrayList<MloginVO> list = this.mloginProc.list_all_m(managerno);
      mav.addObject("list", list);
      mav.addObject("managerno", managerno);
    } else {
      mav.setViewName("/manager/login_need");
    }
    return mav;
  }
  
  /**
   * 삭제폼
   * // http://localhost:9092/mlogin/delete.do?mloginno=6
   * @return
   */
  @RequestMapping(value="/mlogin/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int mloginno) { 
    ModelAndView mav = new ModelAndView();
    
    if(this.managerProc.isManager(session)==true) {
      mav.setViewName("/mlogin/delete"); // /WEB-INF/views/mlogin/delete.jsp
      
      MloginVO mloginVO = this.mloginProc.read(mloginno); 
      mav.addObject("mloginVO", mloginVO);
      
//      ArrayList<MloginVO> list = this.mloginProc.list_all();
//      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/manager/login_need");
    }
    return mav;
  }
  
  /**
   * 삭제 처리, http://localhost:9092/mlogin/delete.do
   * @param mloginVO 삭제할 내용
   * @return 
   */
  @RequestMapping(value="/mlogin/delete.do", method = RequestMethod.POST)
  public ModelAndView delete_proc(HttpSession session,int mloginno) { // 자동으로 mloginVO 객체 생성
    ModelAndView mav = new ModelAndView();
//    MloginVO mloginVO = this.mloginProc.read(mloginno);
    
    if (this.managerProc.isManager(session)) { // 관리자 로그인 확인
//      int managerno = (int)session.getAttribute("managerno"); // FK
//      mloginVO.setManagerno(managerno);       
      int cnt = this.mloginProc.delete(mloginno); 
      
      System.out.println("-> cnt: " + cnt);

      if(cnt == 1) {
        mav.setViewName("redirect:/mlogin/list_all.do"); // 페이지 자동 이동
      } else {
        mav.addObject("code", "delete_fail");
        mav.setViewName("redirect:/mlogin/msg.do"); // 페이지 자동 이동
      }       
      mav.addObject("cnt", cnt);
    } else { 
        mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
        mav.setViewName("redirect:/mlogin/msg.do");
    }

    return mav; // forward
  }
  
  
  
}
