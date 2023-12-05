import com.alibaba.excel.EasyExcel;
import com.qxbase.blog.data.entity.EssayBanner;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.Callable;

public class ExcelWriterTask implements Runnable {

    private List<EssayBanner> data;
    private String filePath;

    public ExcelWriterTask(List<EssayBanner> data, String filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running!");

        EasyExcel.write(filePath, EssayBanner.class)
                .sheet("sheet1")
                .doWrite(data);

//        // 创建新的Workbook
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = null;
//        sheet = workbook.getSheet("Sheet1");
//        if (sheet == null) {
//            sheet = workbook.createSheet("Sheet1");
//        }
//
//        // 获取类的字段
//        Class<?> clazz = data.get(0).getClass();
//        Field[] fields = clazz.getDeclaredFields();
//
////        // 写入表头
////        Row headerRow = sheet.createRow(0);
////        for (int i = 0; i < fields.length; i++) {
////            Cell cell = headerRow.createCell(i);
////            cell.setCellValue(fields[i].getName());
////        }
//
//        // 写入数据
//        for (int rowNum = 0; rowNum < data.size(); rowNum++) {
//            EssayBanner dataObject = data.get(rowNum);
//            Row row = sheet.createRow(Math.toIntExact(dataObject.getBannerId())); // Row索引从1开始，0是表头
//
//            for (int i = 0; i < fields.length; i++) {
//                Field field = fields[i];
//                field.setAccessible(true);
//                Cell cell = null;
//                cell = row.getCell(i);
//                if (cell == null) {
//                    cell = row.createCell(i);
//                }
//
//                // 获取对象的属性值并写入Excel
//                Object value = field.get(dataObject);
//                if (value != null) {
//                    String s = String.valueOf(value);
//                    cell.setCellValue(s);
//                }
//            }
//        }
//
//        // 写入到文件
//        try (FileOutputStream fileOut = new FileOutputStream(filePath + Thread.currentThread().getName() + ".xlsx")) {
//            workbook.write(fileOut);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        workbook.close();

        System.out.println(Thread.currentThread().getName() + " is over!");
    }


}
