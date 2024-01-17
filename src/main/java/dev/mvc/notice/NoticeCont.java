package dev.mvc.notice;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.manager.ManagerProcInter;
import dev.mvc.tool.Tool;


@Controller
public class NoticeCont {

  @Autowired
  @Qualifier("dev.mvc.notice.NoticeProc") // 이름 지정
  private NoticeProcInter noticeProc;
  
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc") // 이름 지정
  private ManagerProcInter managerProc;
  
  public NoticeCont(){
    System.out.println("-> NoticeCont created.");
  }
  
  //FORM 출력, http://localhost:9092/notice/create.do
  @RequestMapping(value="/notice/create.do", method = RequestMethod.GET)
  public ModelAndView create(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (managerProc.isManager(session)) { // 작동을 안하는 것으로 보임
      mav.setViewName("/notice/create"); 
    } else {
      mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
    }
    return mav;
  }
  
  //FORM 데이터 처리, http://localhost:9092/notice/create.do
  @RequestMapping(value = "/notice/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, NoticeVO noticeVO) { // 자동으로 noticeVO 객체 생성 및 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    if (managerProc.isManager(session)) {
      int managerno = (int)session.getAttribute("managerno"); // FK
      noticeVO.setManagerno(managerno);
      int cnt = this.noticeProc.create(noticeVO); 
      
      System.out.println("-> cnt: " + cnt);
      
      if(cnt ==1 ) {
        mav.addObject("code", "create_success");
        mav.setViewName("redirect:/notice/msg.do"); // 주소 자동 이동
      } else {
        mav.addObject("code", "create_fail");
        mav.setViewName("redirect:/notice/msg.do"); // /WEB-INF/views/notice/msg.jsp 수정 필요
      }
      mav.addObject("cnt", cnt);
    } else {
      mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
      mav.setViewName("redirect:/notice/msg.do");  // 수정 필요
    }
    return mav;
  }
  
  /**
   * 새로고침 방지, EL에서 param으로 접근, POST -> GET -> /notice/msg.jsp
   * @return
   */
  @RequestMapping(value="/notice/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  /**
   * 전체 목록
   * // http://localhost:9092/notice/list_all.do
   * @return
   */
  @RequestMapping(value="/notice/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/notice/list_all"); // /WEB-INF/views/notice/list_all.jsp
    ArrayList<NoticeVO> list = this.noticeProc.list_all();
    for (NoticeVO noticeVO : list) {
      String title = noticeVO.getTitle();
      String content = noticeVO.getContent();
      
      title = Tool.convertChar(title);  // 특수 문자 처리
      content = Tool.convertChar(content); 
      
      noticeVO.setTitle(title);
      noticeVO.setContent(content);
    }
    
    
    mav.addObject("list", list);

    return mav;
  }
  
  /**
   * 조회
   * // http://localhost:9092/notice/read.do?noticeno=1
   * @return
   */
  @RequestMapping(value="/notice/read.do", method = RequestMethod.GET)
  public ModelAndView read(int noticeno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/notice/read"); // /WEB-INF/views/notice/read.jsp
    
    NoticeVO noticeVO = this.noticeProc.read(noticeno);
    
    String title = noticeVO.getTitle();
    String content = noticeVO.getContent();
    
    title = Tool.convertChar(title);  // 특수 문자 처리
    content = Tool.convertChar(content); 
    
    noticeVO.setTitle(title);
    noticeVO.setContent(content);
    
    mav.addObject("noticeVO", noticeVO);
    
    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9092/notice/update.do?noticeno=1
   * @return
   */
   @RequestMapping(value = "/notice/update.do", method = RequestMethod.GET)
   public ModelAndView update(HttpSession session, int noticeno) {
   ModelAndView mav = new ModelAndView();
    
   if (managerProc.isManager(session)) { // 관리자로 로그인한경우}
     mav.setViewName("/notice/update"); // /WEB-INF/views/notice/update.jsp
     NoticeVO noticeVO = this.noticeProc.read(noticeno);
     mav.addObject("noticeVO", noticeVO);
      
   } else {
       mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
       mav.setViewName("redirect:/notice/msg.do"); 
   }

    return mav; // forward
   }
   
   /**
    * 수정 처리
    * http://localhost:9092/notice/update.do
    * 
    * @return
    */
    @RequestMapping(value = "/notice/update.do", method = RequestMethod.POST)
    public ModelAndView update(HttpSession session, NoticeVO noticeVO) {
      ModelAndView mav = new ModelAndView();
     
      if (this.managerProc.isManager(session)) { // 관리자 로그인 확인
        int managerno = (int)session.getAttribute("managerno"); // FK
        noticeVO.setManagerno(managerno);
        int cnt = this.noticeProc.update(noticeVO); 
        //this.noticeProc.update(noticeVO);  // 글수정
        System.out.println("-> cnt: " + cnt);

        if(cnt == 1) {
          mav.addObject("noticeno", noticeVO.getNoticeno());
          mav.setViewName("redirect:/notice/read.do"); // 페이지 자동 이동
        } else {
          mav.addObject("code", "update_fail");
          mav.setViewName("redirect:/notice/msg.do"); // 페이지 자동 이동
        }       
        mav.addObject("cnt", cnt);
      } else { 
          mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
          mav.setViewName("redirect:/notice/msg.do");
      }

      return mav; // forward
    }
    
    /**
     * 삭제폼
     * // http://localhost:9092/notice/delete.do?noticeno=2
     * @return
     */
    @RequestMapping(value="/notice/delete.do", method = RequestMethod.GET)
    public ModelAndView delete(HttpSession session, int noticeno) { 
      ModelAndView mav = new ModelAndView();
      
      if(this.managerProc.isManager(session)==true) {
        mav.setViewName("/notice/delete"); // /WEB-INF/views/notice/delete.jsp
        
        NoticeVO noticeVO = this.noticeProc.read(noticeno); 
        mav.addObject("noticeVO", noticeVO);
        
//        ArrayList<NoticeVO> list = this.noticeProc.list_all();
//        mav.addObject("list", list);
        
      } else {
        mav.setViewName("/manager/login_need");
      }
      return mav;
    }
    
    /**
     * 삭제 처리, http://localhost:9092/notice/delete.do
     * @param noticeVO 삭제할 내용
     * @return 
     */
    @RequestMapping(value="/notice/delete.do", method = RequestMethod.POST)
    public ModelAndView delete_proc(HttpSession session,int noticeno) { // 자동으로 noticeVO 객체 생성
      ModelAndView mav = new ModelAndView();
      NoticeVO noticeVO = this.noticeProc.read(noticeno);
      
      if (this.managerProc.isManager(session)) { // 관리자 로그인 확인
        int managerno = (int)session.getAttribute("managerno"); // FK
        noticeVO.setManagerno(managerno);       // 보니까 누가 삭제했는지 알려주는 코드로 보임
        int cnt = this.noticeProc.delete(noticeno); 
        
        System.out.println("-> cnt: " + cnt);

        if(cnt == 1) {
          mav.setViewName("redirect:/notice/list_all.do"); // 페이지 자동 이동
        } else {
          mav.addObject("code", "delete_fail");
          mav.setViewName("redirect:/notice/msg.do"); // 페이지 자동 이동
        }       
        mav.addObject("cnt", cnt);
      } else { 
          mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
          mav.setViewName("redirect:/notice/msg.do");
      }

      return mav; // forward
    }
    
  
}
