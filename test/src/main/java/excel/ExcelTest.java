package excel;

import baseFramework.excel.ExcelReader;
import baseFramework.excel.ExcelWriter;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author chao.li
 * @date 2017/6/6
 */
public class ExcelTest {
    private static final Logger logger = LogManager.getLogger(ExcelTest.class);

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader("D:\\download\\test.xlsx");
        List<String> names = reader.getAllSheetName();
        System.out.println(names);

        Map<String, List<ExcelModel>> map = Maps.newLinkedHashMap();
        for (String name : names) {
            if (!StringUtils.contains(name, "Sheet")) {
                System.out.println(name);
                List<ExcelModel> models = reader.read(name, ExcelModel.class);
                map.put(name, models);
            }
        }

        reader.close();

        ExcelWriter writer = new ExcelWriter("D:\\download\\test1.xlsx");
        writer.addSheets(map);
        writer.writeAndClose();
    }

}

