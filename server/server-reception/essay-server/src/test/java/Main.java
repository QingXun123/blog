import com.alibaba.excel.EasyExcel;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.server.essay.service.IEssayInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Main {

    @Resource
    private IEssayInfoService essayInfoService;

    @Test
    public void test() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
                2,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(20),
                new CustomizableThreadFactory("Thread-pool"));
        threadPoolExecutor.execute(() -> {
            System.out.println("current thread name: " + Thread.currentThread().getName());
            long count = essayInfoService.count();
            for (int i = 0; i < count; i++) {
                EssayInfo byId = essayInfoService.getById(i + 1);
                synchronized (byId) {
                    String filename = "C:\\Users\\Public\\Desktop\\123.xlsx";
                    EasyExcel.write(filename, EssayInfo.class)
                            .sheet("123")
                            .doFill(byId);
                }
            }
            System.out.println("thread run over");
        });

//        threadPoolExecutor.shutdown();
    }
}

