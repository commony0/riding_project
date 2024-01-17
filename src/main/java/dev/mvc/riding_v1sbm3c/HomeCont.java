package dev.mvc.riding_v1sbm3c;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.region.RegionProcInter;
import dev.mvc.region.RegionVO;

@Controller
public class HomeCont {
  @Autowired 
  @Qualifier("dev.mvc.region.RegionProc")
  private RegionProcInter regionProc;
  
  public HomeCont(){
    System.out.println("-> HomeCont Created.");
  }
  
  //http://localhost:9092/
  @RequestMapping(value = {"/", "/index.do"}, method=RequestMethod.GET)
  public ModelAndView home() {
    System.out.println("-> home() ver 2.0");
    
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/index"); // WEB-INF/view/index.jsp
    //    spring.mvc.view.prefix=/WEB-INF/views/
    //    spring.mvc.view.suffix=.jsp
    
    return mav;
  }
  
//http://localhost:9091/menu/top.do
 @RequestMapping(value= {"/menu/top.do"}, method=RequestMethod.GET)
 public ModelAndView top() {
   ModelAndView mav = new ModelAndView();

   ArrayList<RegionVO> list_top = this.regionProc.list_all_y();
   mav.addObject("list_top", list_top);
   
   mav.setViewName("/menu/top"); // /WEB-INF/views/menu/top.jsp
   
   return mav;
 }

}