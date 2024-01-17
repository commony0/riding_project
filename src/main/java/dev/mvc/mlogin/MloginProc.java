package dev.mvc.mlogin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.mlogin.MloginProc")
public class MloginProc implements MloginProcInter {

  @Autowired
  private MloginDAOInter mloginDAO;
  
  @Override
  public int create(MloginVO mloginVO) {
    int cnt = mloginDAO.create(mloginVO);
    return cnt;
  }

  @Override
  public ArrayList<MloginVO> list_all() {
    ArrayList<MloginVO> list = this.mloginDAO.list_all();
    return list;
  }

  @Override
  public ArrayList<MloginVO> list_all_m(int managerno) {
    ArrayList<MloginVO> list = this.mloginDAO.list_all_m(managerno);
    return list;
  }

  @Override
  public MloginVO read(int mloginno) {
    MloginVO mloginVO = this.mloginDAO.read(mloginno);
    return mloginVO;
  }

  @Override
  public int delete(int mloginno) {
    int cnt = this.mloginDAO.delete(mloginno);
    return cnt;
  }

}
