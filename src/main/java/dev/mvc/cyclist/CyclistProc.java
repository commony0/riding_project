package dev.mvc.cyclist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.cyclist.CyclistProc")

public class CyclistProc implements CyclistProcInter {
  @Autowired
  private CyclistDAOInter cyclistDAO;
  
  @Override
  public int checkID(String id) {
    int cnt = this.cyclistDAO.checkID(id);
    return cnt;
  }

  @Override
  public int create(CyclistVO cyclistVO) {
    int cnt = this.cyclistDAO.create(cyclistVO);
    return cnt;
  }

  @Override
  public ArrayList<CyclistVO> list() {
    ArrayList<CyclistVO> list = this.cyclistDAO.list();
    return list;
  }

  @Override
  public CyclistVO read(int cyclistno) {
    CyclistVO cyclistVO = this.cyclistDAO.read(cyclistno);
    return cyclistVO;
  }

  @Override
  public CyclistVO readById(String id) {
    CyclistVO cyclistVO = this.cyclistDAO.readById(id);
    return cyclistVO;
  }

  @Override
  public boolean isCyclist(HttpSession session) {
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    int grade = 99;
    
    // System.out.println("-> grade: " + session.getAttribute("grade"));
    if (session != null) {
      String id = (String)session.getAttribute("id");
      if (session.getAttribute("grade") != null) {
        grade = (int)session.getAttribute("grade");
      }
      
      if (id != null && grade <= 20){ // 관리자 + 회원
        sw = true;  // 로그인 한 경우
      }
    }
    
    return sw;
  }

  @Override
  public boolean isCyclistManager(HttpSession session) {
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    int grade = 99;
    
    // System.out.println("-> grade: " + session.getAttribute("grade"));
    if (session != null) {
      String id = (String)session.getAttribute("id");
      if (session.getAttribute("grade") != null) {
        grade = (int)session.getAttribute("grade");
      }
      
      if (id != null && grade <= 10){ // 관리자
        sw = true;  // 로그인 한 경우
      }
    }
    
    return sw;
  }

  @Override
  public int update(CyclistVO cyclistVO) {
    int cnt = this.cyclistDAO.update(cyclistVO);
    return cnt;
  }

  @Override
  public int delete(int cyclistno) {
    int cnt = this.cyclistDAO.delete(cyclistno);
    return cnt;
  }

  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.cyclistDAO.login(map);
    return cnt;
  }

  @Override
  public int passwd_check(HashMap<String, Object> map) {
    int cnt = this.cyclistDAO.passwd_check(map);
    return cnt;
  }

  @Override
  public int passwd_update(HashMap<String, Object> map) {
    int cnt = this.cyclistDAO.passwd_update(map);
    return cnt;
  }

}
