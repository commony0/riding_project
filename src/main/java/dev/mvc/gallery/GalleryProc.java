package dev.mvc.gallery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.gallery.GalleryProc")
public class GalleryProc implements GalleryProcInter {
  @Autowired
  private GalleryDAOInter galleryDAO;

  @Override
  public int create(GalleryVO galleryVO) {
    int cnt = this.galleryDAO.create(galleryVO);
    return cnt;
  }

}
