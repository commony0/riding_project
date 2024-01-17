package dev.mvc.riding_v1sbm3c;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mvc.manager.ManagerProcInter;
import dev.mvc.manager.ManagerVO;

@SpringBootTest
public class ManagerTest {
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc") // dev.mvc.manager.ManagerProc 라고 명명된 클래스
  private ManagerProcInter managerProc;
  
  @Test
  public void testRead() {
    ManagerVO managerVO = this.managerProc.read(1);
    System.out.println(managerVO.toString());
  }
}
