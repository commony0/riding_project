package dev.mvc.region;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Controller가 사용하는 이름
@Component("dev.mvc.region.RegionProc")
public class RegionProc implements RegionProcInter {
  @Autowired // 객체를 만들어 자동으로 할당하라
  private RegionDAOInter regionDAO;

  @Override
  public int create(RegionVO regionVO) {
    int cnt = this.regionDAO.create(regionVO);
    return cnt;
  }
  @Override
  public ArrayList<RegionVO> list_all() {
    ArrayList<RegionVO> list = this.regionDAO.list_all();
    return list;
  }
  @Override
  public ArrayList<RegionVO> list_all_y() {
    ArrayList<RegionVO> list = this.regionDAO.list_all_y();
    return list;
  }
  
  @Override
  public RegionVO read(int regionno) {
    RegionVO regionVO = this.regionDAO.read(regionno);
    return regionVO;
  }
  @Override
  public int update(RegionVO regionVO) {
    int cnt = this.regionDAO.update(regionVO);
    return cnt;
  }
  @Override
  public int delete(int regionno) {
    int cnt = this.regionDAO.delete(regionno);
    return cnt;
  }
  @Override
  public int update_seqno_forward(int regionno) {
    int cnt = this.regionDAO.update_seqno_forward(regionno);    
    return cnt;
  }
  @Override
  public int update_seqno_backward(int regionno) {
    int cnt = this.regionDAO.update_seqno_backward(regionno);    
    return cnt;
  }
  @Override
  public int update_visible_y(int regionno) {
    int cnt = this.regionDAO.update_visible_y(regionno);    
    return cnt;
  }
  @Override
  public int update_visible_n(int regionno) {
    int cnt = this.regionDAO.update_visible_n(regionno);    
    return cnt;
  }
  
}