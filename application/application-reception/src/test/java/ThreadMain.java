import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qxbase.blog.application.BlogReceptionApplication;
import com.qxbase.blog.data.entity.EssayBanner;
import com.qxbase.blog.server.essay.service.IEssayBannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest(classes = BlogReceptionApplication.class)
@RunWith(SpringRunner.class)
public class ThreadMain {

    @Resource
    private IEssayBannerService essayBannerService;

    @Test
    public void test() throws IOException, InterruptedException {
        // 获取数据
        List<EssayBanner> data = essayBannerService.list();

        // 定义Excel文件路径
        String filePath = "C:\\Users\\QingXun\\OneDrive\\桌面\\";
        List<String> paths = new ArrayList<>();
//        File file = new File(filePath);
//        if (!file.exists()) {
//            file.createNewFile();
//        }

        // 定义线程池
        int threadPoolSize = 2; // 根据需要设置线程池大小
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        Future<?> future = null;

        // 将数据分割成子列表，每个子列表交由一个线程处理
        int batchSize = data.size() / threadPoolSize;
        for (int i = 0; i < threadPoolSize; i++) {
            int fromIndex = i * batchSize;
            int toIndex = (i == threadPoolSize - 1) ? data.size() : (i + 1) * batchSize;
            List<EssayBanner> subList = data.subList(fromIndex, toIndex);

            String path = filePath + i + ".xlsx";
            paths.add(path);

            // 启动线程执行写入任务
            future = executorService.submit(new ExcelWriterTask(subList, filePath + i + ".xlsx"));
        }

//         提交任务
//        Future<?> future = executorService.submit(new ExcelWriterTask(data, filePath));

        // 关闭线程池
        executorService.shutdown();

        // 等待任务完成
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        // 获取任务执行结果
        try {
            future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

//        // 合并excel文件
//        ExcelWriter excelWriter = EasyExcel
//                .write(filePath + "finalFile.xlsx", EssayBanner.class)
//                .build();
        ExcelListener excelListener = new ExcelListener();
        for (String path : paths) {
            EasyExcel.read(path, EssayBanner.class, excelListener)
                    .sheet()
                    .doRead();
        }
        EasyExcel.write(filePath + "finalFile.xlsx", EssayBanner.class)
                .sheet("sheet1")
                .doWrite(excelListener.getDataList());
    }
}

class ExcelListener extends AnalysisEventListener<EssayBanner> {

    private ExcelWriter excelWriter;

    private List<EssayBanner> essayBanners = new ArrayList<>();

    public ExcelListener(ExcelWriter excelWriter) {
        this.excelWriter = excelWriter;
    }

    public ExcelListener() {
    }

    @Override
    public void invoke(EssayBanner data, AnalysisContext context) {
        System.out.println("Original Data: " + data);

        // 使用 context 来获取实际的数据
        EssayBanner rowData = (EssayBanner) context.readRowHolder().getCurrentRowAnalysisResult();

        System.out.println("Row Data: " + rowData);

        if (rowData != null) {
            essayBanners.add(rowData);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // do nothing
//        WriteSheet writeSheet = EasyExcel.writerSheet(context.readSheetHolder().getSheetName()).sheetName("sheet1").build();
//        excelWriter.write(essayBanners, writeSheet);
    }

    public List<EssayBanner> getDataList() {
        return essayBanners;
    }
}