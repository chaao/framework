package excel;

import baseFramework.easy.properties.EasyPropertiesUtils;
import baseFramework.excel.FastExcel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author chao.li
 * @date 2017/6/6
 */
public class ExcelTest {
    private static final Logger logger = LogManager.getLogger(ExcelTest.class);

    public static void main(String[] args) {
        File file = EasyPropertiesUtils.createFile("excel/data.xlsx");
        System.out.println(file.getPath());
        try {
            createExcel(file.getPath());
        } catch (IOException e) {
            logger.error("异常", e);
        } catch (InvalidFormatException e) {
            logger.error("异常", e);
        }

    }

    private static void createExcel(String filePath) throws IOException, InvalidFormatException {
        FastExcel fastExcel = new FastExcel(filePath);
        fastExcel.setSheetName("活动信息数据");
        List<ExcelModel> list = fastExcel.parse(ExcelModel.class);
        if (null != list && !list.isEmpty()) {
            for (ExcelModel item : list) {
                logger.info("记录:{}", item.toString());
            }

//            FastExcel create = new FastExcel("E:/data2.xlsx");
//            create.setSheetName("活动信息数据");
//            boolean result = create.createExcel(list);
//            logger.debug("结果:{}", result);
//            create.close();
        } else {
            logger.info("没有结果");
        }
        fastExcel.close();
    }
}
