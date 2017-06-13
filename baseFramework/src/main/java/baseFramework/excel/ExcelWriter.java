package baseFramework.excel;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * @author chao.li
 * @date 2017/6/6
 */
public class ExcelWriter {
    private static final Logger logger = LogManager.getLogger(ExcelReader.class);
    /**
     * 时日类型的数据默认格式化方式
     */
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String defaultSheetName = "Sheet1";
    private File targetFile;
    private final Workbook workbook;


    public ExcelWriter(String targetFilePath) throws IOException, InvalidFormatException {
        this.targetFile = new File(targetFilePath);
        this.workbook = new XSSFWorkbook();
    }

    public ExcelWriter(File targetFile) throws IOException, InvalidFormatException {
        this.targetFile = targetFile;
        this.workbook = new XSSFWorkbook();
    }


    /**
     * 设置时间数据格式
     *
     * @param format 格式
     */
    public void setFormat(String format) {
        this.format = new SimpleDateFormat(format);
    }

    public <T> void addSheets(Map<String, List<T>> sheets) {
        for (Entry<String, List<T>> entry : sheets.entrySet()) {
            addSheet(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 将数据写入excel文件
     *
     * @param datas 数据列表
     * @param <T>   泛型
     * @return 写入结果
     */
    public <T> boolean addSheet(String sheetName, List<T> datas) {
        boolean result = false;
        if (null != datas && !datas.isEmpty()) {
            T model = datas.get(0);

            Map<Integer, Field> fieldMap = Maps.newHashMap();
            Map<Integer, String> titleMap = new TreeMap<Integer, String>();
            Field[] fields = model.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(MapperCell.class)) {
                    field.setAccessible(true);
                    MapperCell mapperCell = field.getAnnotation(MapperCell.class);
                    fieldMap.put(mapperCell.order(), field);
                    titleMap.put(mapperCell.order(), mapperCell.cellName());
                }
            }
            try {
                Sheet sheet = workbook.createSheet(sheetName);

                //生成标题行
                Row titleRow = sheet.createRow(0);
                for (Entry<Integer, String> entry : titleMap.entrySet()) {
                    Cell cell = titleRow.createCell(entry.getKey());
                    cell.setCellValue(entry.getValue());
                }


                //生成数据行
                for (int i = 0; i < datas.size(); i++) {
                    Row row = sheet.createRow(i + 1);
                    T data = datas.get(i);

                    for (Entry<Integer, Field> entry : fieldMap.entrySet()) {
                        Field field = entry.getValue();

                        Cell cell = row.createCell(entry.getKey());
                        Object value = field.get(data);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }

                }

                result = true;
            } catch (IllegalAccessException e) {
                logger.error("反射异常", e);
            } catch (Exception e) {
                logger.error("其他异常", e);
            }
        }
        return result;
    }

    public void writeAndClose() throws IOException {
        if (this.targetFile.exists()) {
            this.targetFile.delete();
        }

        FileOutputStream outputStream = null;
        try {
            this.targetFile.createNewFile();
            outputStream = new FileOutputStream(this.targetFile);
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }

            if (this.workbook != null) {
                this.workbook.close();
            }
        }
    }

}
