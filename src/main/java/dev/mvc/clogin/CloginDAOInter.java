package dev.mvc.clogin;

import java.util.ArrayList;

/**
 * cyclist의 c
 * 
 */
public interface CloginDAOInter {
  
  public int create(CloginVO cloginVO);
  
  public ArrayList<CloginVO>list_all();
  
  public ArrayList<CloginVO>list_all_c(int cyclistno);
  
  public CloginVO read(int cloginno);
  
  public int delete(int cloginno);
  
}
