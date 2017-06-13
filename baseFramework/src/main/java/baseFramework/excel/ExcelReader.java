package baseFramework.excel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author chao.li
 * @date 2017/6/6
 */
public class ExcelReader implements Closeable {
    private static final Logger logger = LogManager.getLogger(ExcelReader.class);
    /**
     * 时日类型的数据默认格式化方式
     */
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String defaultSheetName = "Sheet1";

    private final Workbook workbook;


    public ExcelReader(String excelFilePath) throws IOException, InvalidFormatException {
        File file = new File(excelFilePath);
        if (!file.exists()) {
            throw new IOException("文件不存在");
        }
        this.workbook = WorkbookFactory.create(file);
    }

    public ExcelReader(File file) throws IOException, InvalidFormatException {
        if (!file.exists()) {
            throw new IOException("文件不存在");
        }
        this.workbook = WorkbookFactory.create(file);
    }

    public List<String> getAllSheetName() {
        int count = this.workbook.getNumberOfSheets();
        List<String> names = Lists.newArrayList();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                String name = this.workbook.getSheetName(i);
                names.add(name);
            }
        }
        return names;
    }


    public <T> List<T> read(String sheetName, Class<T> clazz) {
        List<T> resultList = Lists.newArrayList();
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            int titleRowIndex = 0;
            if (null != sheet) {
                Row headRow = sheet.getRow(titleRowIndex);

                Map<String, Field> fieldMap = Maps.newHashMap();
                Map<String, String> titleMap = Maps.newHashMap();

                Field[] fields = clazz.getDeclaredFields();
                //这里开始处理映射类型里的注解
                for (Field field : fields) {
                    if (field.isAnnotationPresent(MapperCell.class)) {
                        field.setAccessible(true);
                        MapperCell mapperCell = field.getAnnotation(MapperCell.class);
                        fieldMap.put(mapperCell.cellName(), field);
                    }
                }

                for (Cell title : headRow) {
                    CellReference cellRef = new CellReference(title);
                    //key=A/B/C/D/E/F...
                    titleMap.put(cellRef.getCellRefParts()[2], title.getRichStringCellValue().getString());
                }

                for (int i = titleRowIndex + 1; i <= sheet.getLastRowNum(); i++) {
                    Row dataRow = sheet.getRow(i);
                    if (dataRow != null) {
                        T obj = clazz.newInstance();
                        for (Cell cell : dataRow) {
                            CellReference cellRef = new CellReference(cell);
                            String cellTag = cellRef.getCellRefParts()[2];
                            String name = titleMap.get(cellTag);
                            Field field = fieldMap.get(name);
                            if (null != field) {
                                field.setAccessible(true);
                                setField(cell, obj, field);
                            }
                        }
                        resultList.add(obj);
                    }
                }
            } else {
                throw new RuntimeException("sheetName:" + sheetName + " is not exist");
            }
        } catch (InstantiationException e) {
            logger.error("初始化异常", e);
        } catch (IllegalAccessException e) {
            logger.error("初始化异常", e);
        } catch (ParseException e) {
            logger.error("时间格式化异常:{}", e);
        } catch (Exception e) {
            logger.error("其他异常", e);
        }
        return resultList;
    }

    private void setField(Cell cell, Object obj, Field field) throws IllegalAccessException, ParseException {
        switch (cell.getCellTypeEnum()) {
            case BLANK:
                break;
            case BOOLEAN:
                field.setBoolean(obj, cell.getBooleanCellValue());
                break;
            case ERROR:
                field.setByte(obj, cell.getErrorCellValue());
                break;
            case FORMULA:
                field.set(obj, cell.getCellFormula());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    if (field.getType().getName().equals(Date.class.getName())) {
                        field.set(obj, cell.getDateCellValue());
                    } else {
                        field.set(obj, format.format(cell.getDateCellValue()));
                    }
                } else {
                    Type type = field.getType();
                    Double numericValue = cell.getNumericCellValue();

                    Object value;
                    if (type == Integer.class || type == int.class) {
                        value = numericValue.intValue();
                    } else if (type == Short.class || type == short.class) {
                        value = numericValue.shortValue();
                    } else if (type == Float.class || type == float.class) {
                        value = numericValue.floatValue();
                    } else if (type == Byte.class || type == byte.class) {
                        value = numericValue.byteValue();
                    } else if (type == Long.class || type == long.class) {
                        value = numericValue.longValue();
                    } else if (type == Double.class || type == double.class) {
                        value = numericValue.doubleValue();
                    } else if (type == String.class) {
                        String s = String.valueOf(numericValue);
                        if (s.contains("E")) {
                            s = s.trim();
                            BigDecimal bigDecimal = new BigDecimal(s);
                            s = bigDecimal.toPlainString();
                        }
                        //防止整数判定为浮点数
                        if (s.endsWith(".0"))
                            s = s.substring(0, s.indexOf(".0"));
                        value = s;
                    } else {
                        value = numericValue;
                    }
                    field.set(obj, value);
                }
                break;
            case STRING:
                if (field.getType().getName().equals(Date.class.getName())) {
                    field.set(obj, format.parse(cell.getRichStringCellValue().getString()));
                } else {
                    field.set(obj, cell.getRichStringCellValue().getString());
                }
                break;
            default:
                field.set(obj, cell.getStringCellValue());
                break;
        }

    }

    /**
     * 设置时间数据格式
     *
     * @param format 格式
     */
    public void setFormat(String format) {
        this.format = new SimpleDateFormat(format);
    }


    @Override
    public void close() throws IOException {
        this.workbook.close();
    }

}
