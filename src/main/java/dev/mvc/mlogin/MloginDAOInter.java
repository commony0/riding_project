package dev.mvc.mlogin;

import java.util.ArrayList;

/**
 * manager의 m
 * 
 */
public interface MloginDAOInter {
  
    public int create(MloginVO mloginVO);
    
    public ArrayList<MloginVO>list_all();
    
    public ArrayList<MloginVO>list_all_m(int managerno);
    
    public MloginVO read(int mloginno);
    
    public int delete(int mloginno);

}
