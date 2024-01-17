package dev.mvc.gallery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.cyclist.CyclistProcInter;
import dev.mvc.cyclist.CyclistVO;
import dev.mvc.region.RegionVO;

@Controller
public class GalleryCont {
  @Autowired
  @Qualifier("dev.mvc.cyclist.CyclistProc")
  private CyclistProcInter cyclistProc;
  
  @Autowired
  @Qualifier("dev.mvc.gallery.GalleryProc")
  private GalleryProcInter galleryProc;
  
  public GalleryCont() {
    System.out.println("-> GalleryCont created");
  }
  // 등록 폼 // 이후 수료후 모두 완료할것(중요), detail을 참고하여 만들것 , 댓글을 게시글에 표기하게 만들시 region에 사용하던 방식을 사용하기
  // http://localhost:9092/gallery/create.do?cyclistno=1
  @RequestMapping(value="/gallery/create.do", method = RequestMethod.GET)
  public ModelAndView create(int cyclistno) {
    ModelAndView mav = new ModelAndView();

    CyclistVO cyclistVO = this.cyclistProc.read(cyclistno); 
    mav.addObject("cyclistVO", cyclistVO);
    
    mav.setViewName("/gallery/create"); 
    
    return mav;
  }  
}
