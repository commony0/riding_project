package dev.mvc.reply;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.cyclist.CyclistProcInter;
import dev.mvc.detail.DetailProcInter;
import dev.mvc.detail.DetailVO;
import dev.mvc.manager.ManagerProcInter;
import dev.mvc.tool.Tool;


@Controller
public class ReplyCont {
  
  @Autowired
  @Qualifier("dev.mvc.reply.ReplyProc") // 이름 지정
  private ReplyProcInter replyProc;
  
  @Autowired
  @Qualifier("dev.mvc.detail.DetailProc") //@Component("dev.mvc.detail.DetailProc")
  private DetailProcInter detailProc;
  
  @Autowired
  @Qualifier("dev.mvc.cyclist.CyclistProc") // 이름 지정
  private CyclistProcInter cyclistProc;
  
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc") // 이름 지정
  private ManagerProcInter managerProc;
  
  public ReplyCont(){
    System.out.println("-> ReplyCont created.");
  }
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * POST -> url -> GET -> 데이터 전송
   * @return
   */
  @RequestMapping(value="/reply/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  //FORM 출력, http://localhost:9092/reply/create.do?detailno=5
  @RequestMapping(value="/reply/create.do", method = RequestMethod.GET)
  public ModelAndView create(HttpSession session, int detailno) {
    ModelAndView mav = new ModelAndView();
    
    DetailVO detailVO = this.detailProc.read(detailno); 
    mav.addObject("detailVO", detailVO);

    mav.setViewName("/reply/create"); // /WEB-INF/views/reply/create.jsp


    return mav;
  }
  
  //FORM 데이터 처리, http://localhost:9092/reply/create.do
  @RequestMapping(value = "/reply/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, ReplyVO replyVO) { // 자동으로 regionVO 객체 생성 및 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    if (cyclistProc.isCyclist(session)) {
      int cyclistno = (int)session.getAttribute("cyclistno"); // cyclistno FK
      replyVO.setCyclistno(cyclistno);
      
      int cnt = this.replyProc.create(replyVO); 
      
      System.out.println("-> cnt: " + cnt);
      
      if(cnt ==1 ) {
        mav.setViewName("redirect:/detail/read.do?detailno=" + replyVO.getDetailno());
      } else {
        mav.addObject("code", "create_fail");
        mav.setViewName("/reply/msg"); // 
      }
      
//      mav.addObject("cnt", cnt); // 필요한가?
    } else {
      mav.addObject("url", "/cyclist/login_need"); // /WEB-INF/views/manager/login_need.jsp
      mav.setViewName("redirect:/reply/msg.do");  
    }

    return mav;
  }
  
  /**
   * 전체 목록/ 관리자 댓글 관리용
   * // http://localhost:9092/reply/list.do
   * @return
   */
  @RequestMapping(value="/reply/list.do", method = RequestMethod.GET)
  public ModelAndView list(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    if(this.managerProc.isManager(session)) {
      mav.setViewName("/reply/list"); // /WEB-INF/views/reply/list.jsp
      ArrayList<ReplyVO> list = this.replyProc.list();
      for (ReplyVO replyVO : list) {
        String content = replyVO.getContent();
        content = Tool.convertChar(content); 
        replyVO.setContent(content);  
      }
      
      mav.addObject("list", list);
    } else {
      mav.setViewName("/manager/login_need"); 
    }
    return mav;
  }
  
  /**
   * 게시글 댓글 목록
   * // http://localhost:9092/reply/list_by_detailno.do?detailno=5
   * @return
   */
  @RequestMapping(value="/reply/list_by_detailno.do", method = RequestMethod.GET)
  public ModelAndView list_by_detailno(ReplyVO replyVO) {
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("/reply/list_by_detailno"); // /WEB-INF/views/reply/list_by_detailno.jsp
    
    ArrayList<ReplyVO> list = this.replyProc.list_by_detailno(replyVO.getDetailno());
    
    for (ReplyVO vo : list) {
      String content = vo.getContent();
      content = Tool.convertChar(content); 
      vo.setContent(content);  
    }
    mav.addObject("list", list);
    
//    DetailVO detailVO = detailProc.read(replyVO.getDetailno());
//    mav.addObject("detailVO", detailVO);
    
    return mav;
  }
  
  /**
   * 게시글 댓글 목록 id 포함
   * // http://localhost:9092/reply/list_by_detailno_id.do?detailno=5
   * @return
   */
  @RequestMapping(value="/reply/list_by_detailno_id.do", method = RequestMethod.GET)
  public ModelAndView list_by_detailno_id(ReplyCyclistVO replyCyclistVO) {
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("/reply/list_by_detailno_id"); // /WEB-INF/views/reply/list_by_detailno_id.jsp
    
    ArrayList<ReplyCyclistVO> list = this.replyProc.list_by_detailno_id(replyCyclistVO.getDetailno());
    
    for (ReplyCyclistVO vo : list) {
      String content = vo.getContent();
      content = Tool.convertChar(content); 
      vo.setContent(content);  
    }
    mav.addObject("list", list);
    
//    DetailVO detailVO = detailProc.read(replyVO.getDetailno());
//    mav.addObject("detailVO", detailVO);
    
    return mav;
  }
  
//  /**
//   * 삭제폼(javascript 방식으로 사용하여 필요가 없어짐)
//   * // http://localhost:9092/reply/delete.do?replyno=2
//   * @return
//   */
//  @RequestMapping(value="/reply/delete.do", method = RequestMethod.GET)
//  public ModelAndView delete(HttpSession session, int replyno) { 
//    ModelAndView mav = new ModelAndView();
//    
//    ReplyVO replyVO = this.replyProc.read(replyno);
//    mav.addObject("replyVO", replyVO);
//    mav.addObject("detailno", replyVO.getDetailno());
//    if(this.cyclistProc.isCyclist(session) || this.managerProc.isManager(session)==true) {
//      mav.setViewName("/reply/delete"); // /WEB-INF/views/reply/delete.jsp
//      if (this.cyclistProc.isCyclist(session)) { // 회원으로 로그인 경우
//        // session을 사용하여 현재 로그인한 사용자의 cyclistno값만 읽음
//        int cyclistno = (int)session.getAttribute("cyclistno"); 
//        
//        int replyCyclistno = replyVO.getCyclistno(); // 댓글을 작성한 회원 번호를 가져옴
//        
//        if (cyclistno != replyCyclistno) {
//          mav.addObject("code", "delete_fail_notc");
//          mav.addObject("msg", "삭제 권한이 없습니다."); // 권한 없음 메시지 추가
//          mav.setViewName("redirect:/reply/msg.do");
//          return mav;
//        }
//      }
////      ArrayList<ReplyVO> list = this.replyProc.list_all();
////      mav.addObject("list", list);
//      
//    } else {
//      mav.setViewName("/manager/login_need");
//    }
//    return mav;
//  }
  
//  /**
//   * 삭제 처리(javascript 방식을 사용하여 필요가 없어짐), http://localhost:9092/reply/delete.do
//   * @param replyVO 삭제할 내용
//   * @return 
//   */
//  @RequestMapping(value="/reply/delete.do", method = RequestMethod.POST)
//  public ModelAndView delete_proc(HttpSession session, int replyno, int detailno) { // 자동으로 replyVO 객체 생성
//    ModelAndView mav = new ModelAndView();
//    
//    if (this.cyclistProc.isCyclist(session) || this.managerProc.isManager(session)==true) { // 로그인 확인
//      
//      ReplyVO replyVO = this.replyProc.read(replyno);
//      int cnt = 0;
//      mav.addObject("detailno", replyVO.getDetailno());
//      
//      if (this.cyclistProc.isCyclist(session)) { // 회원으로 로그인 경우
//        // session을 사용하여 현재 로그인한 사용자의 cyclistno값만 읽음
//        int cyclistno = (int)session.getAttribute("cyclistno"); 
//        
//        int replyCyclistno = replyVO.getCyclistno(); // 댓글을 작성한 회원 번호를 가져옴
//        
//        if (cyclistno != replyCyclistno) {
//          mav.addObject("code", "delete_fail_notc");
//          mav.addObject("msg", "삭제 권한이 없습니다."); // 권한 없음 메시지 추가
//          mav.setViewName("redirect:/reply/msg.do");
//          return mav;
//        }
//      }
//      
//      cnt = this.replyProc.delete(replyno); 
//      
//      System.out.println("-> cnt: " + cnt);
//
//      if(cnt == 1) {
//        mav.setViewName("redirect:/detail/read.do?detailno=" + replyVO.getDetailno());
//      } else {
//        mav.addObject("code", "delete_fail");
//        mav.setViewName("redirect:/reply/msg.do"); // 페이지 자동 이동
//      }       
//      mav.addObject("cnt", cnt);
//    } else { 
//        mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
//        mav.setViewName("redirect:/reply/msg.do");
//    }
//
//    return mav; // forward
//  }
  
  /**
   * 삭제 처리, http://localhost:9092/reply/delete.do
   * @param replyVO 삭제할 내용
   * @return 
   */
  @RequestMapping(value="/reply/delete.do", method = RequestMethod.GET)
  public ModelAndView delete_proc(HttpSession session, int replyno, int detailno) { // 자동으로 replyVO 객체 생성
    ModelAndView mav = new ModelAndView();
    
    if (this.cyclistProc.isCyclist(session) || this.managerProc.isManager(session)==true) { // 로그인 확인
      
      ReplyVO replyVO = this.replyProc.read(replyno);
      int cnt = 0;
      
      if (this.cyclistProc.isCyclist(session)) { // 회원으로 로그인 경우
        // session을 사용하여 현재 로그인한 사용자의 cyclistno값만 읽음
        int cyclistno = (int)session.getAttribute("cyclistno"); 
        
        int replyCyclistno = replyVO.getCyclistno(); // 댓글을 작성한 회원 번호를 가져옴
        
        if (cyclistno != replyCyclistno) {
          mav.addObject("code", "delete_fail_notc");
          mav.addObject("msg", "삭제 권한이 없습니다."); // 권한 없음 메시지 추가
          mav.setViewName("redirect:/reply/msg.do");
          return mav;
        }
      }
      
      cnt = this.replyProc.delete(replyno); 
      
      System.out.println("-> cnt: " + cnt);

      if(cnt == 1) {
        mav.setViewName("redirect:/detail/read.do?detailno=" + replyVO.getDetailno());
      } else {
        mav.addObject("code", "delete_fail");
        mav.setViewName("redirect:/reply/msg.do"); // 페이지 자동 이동
      }       
      mav.addObject("cnt", cnt);
    } else { 
        mav.addObject("url", "/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
        mav.setViewName("redirect:/reply/msg.do");
    }

    return mav; // forward
  }
  
  

//  //  http://localhost:9092/reply/create.do
//  @ResponseBody
//  @RequestMapping(value = "/reply/create.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
//  public String create(ReplyVO replyVO) {
//    int cnt = replyProc.create(replyVO);
//    
//    JSONObject obj = new JSONObject();
//    obj.put("cnt",cnt);
// 
//    return obj.toString(); // {"cnt":1}
//
//  }
  

  
  
  
}
