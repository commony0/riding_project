package dev.mvc.riding_v1sbm3c;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mvc.region.RegionDAOInter;
import dev.mvc.region.RegionVO;

@SpringBootTest
class RidingV1sbm3cApplicationTests {
  @Autowired // 객체 생성후 자동 할당
  private RegionDAOInter regionDAO;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testCreate() {
	  RegionVO regionVO = new RegionVO();
	  regionVO.setName("인천");
	  
	  int cnt = this.regionDAO.create(regionVO);
	  System.out.println("-> cnt: " +cnt);
	}

}
