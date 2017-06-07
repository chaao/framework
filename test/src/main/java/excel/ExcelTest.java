package excel;

import baseFramework.excel.FastExcel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;

/**
 * @author chao.li
 * @date 2017/6/6
 */
public class ExcelTest {
    private static final Logger logger = LogManager.getLogger(ExcelTest.class);

    public static void main(String[] args) {
        try {
            List<ExcelModel>  list = readExcel("D:\\download\\data.xlsx");
            writeExcel("D:\\download\\test.xlsx", list);
        } catch (IOException e) {
            logger.error("异常", e);
        } catch (InvalidFormatException e) {
            logger.error("异常", e);
        }

    }

    private static List<ExcelModel> readExcel(String filePath) throws IOException, InvalidFormatException {
        FastExcel fastExcel = new FastExcel(filePath, 1);
        fastExcel.setSheetName("活动信息数据");
        List<ExcelModel> list = fastExcel.parse(ExcelModel.class);
        if (null != list && !list.isEmpty()) {
            for (ExcelModel item : list) {
                logger.info("记录:{}", item.toString());
            }

        } else {
            logger.info("没有结果");
        }
        fastExcel.close();
        return list;


    }
    private static void writeExcel(String filePath, List<ExcelModel> list) throws IOException, InvalidFormatException {

        if (list != null && list.size() > 0) {
            FastExcel writeFile = new FastExcel(filePath,2);
            writeFile.setSheetName("write");
            boolean result = writeFile.createExcel(list);
            logger.debug("结果:{}", result);
            writeFile.close();
        }
    }
}
