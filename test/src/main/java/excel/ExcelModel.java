package excel;

import baseFramework.excel.MapperCell;

/**
 * @author chao.li
 * @date 2017/6/6
 */
public class ExcelModel {
    @MapperCell(cellName = "名称", order = 0)
    private String name;

    @MapperCell(cellName = "联系电话", order = 1)
    private String phone;

    @MapperCell(cellName = "地址", order = 2)
    private String address;

    @MapperCell(cellName = "一级分类ID", order = 3)
    private int type;

    @MapperCell(cellName = "经度", order = 4)
    private double lat;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExcelModel{");
        sb.append("name='").append(name).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", type=").append(type);
        sb.append(", lat=").append(lat);
        sb.append('}');
        return sb.toString();
    }
}
