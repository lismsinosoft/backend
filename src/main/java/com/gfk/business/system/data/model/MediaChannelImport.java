package com.gfk.business.system.data.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.ImportBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("import_media_channel")
public class MediaChannelImport extends ImportBase {

    @ExcelProperty(value = "modelnm")
    private String modelName;

    @ExcelProperty(value = "Platform")
    private String platform;

    @ExcelProperty(value = "media_PL")
    private String product;

    @ExcelProperty(value = "lgroup")
    private String lGroup;

    @ExcelProperty(value = "varlabel")
    private String varLabel;

    @ExcelProperty(value = "varnm")
    private String name;

    @ExcelProperty(value = "pie_overall")
    private BigDecimal pieOverall;

    @ExcelProperty(value = "pie_X2021")
    private BigDecimal pieFirstYear;

    @ExcelProperty(value = "pie_X2022")
    private BigDecimal pieLastYear;

    @ExcelProperty(value = "spend_overall")
    private BigDecimal spendOverall;

    @ExcelProperty(value = "spend_X2021")
    private BigDecimal spendFirstYear;

    @ExcelProperty(value = "spend_X2022")
    private BigDecimal spendLastYear;

    @ExcelProperty(value = "valueusd_overall")
    private BigDecimal valueUsdOverall;

    @ExcelProperty(value = "valueusd_X2021")
    private BigDecimal valueUsdFirstYear;

    @ExcelProperty(value = "valueusd_X2022")
    private BigDecimal valueUsdLastYear;

    @ExcelProperty(value = "driven_overall")
    private BigDecimal drivenOverall;

    @ExcelProperty(value = "driven_X2021")
    private BigDecimal drivenUsdFirstYear;

    @ExcelProperty(value = "driven_X2022")
    private BigDecimal drivenUsdLastYear;

    @ExcelProperty(value = "roi_overall")
    private BigDecimal roiOverall;

    @ExcelProperty(value = "roi_X2021")
    private BigDecimal roiFirstYear;

    @ExcelProperty(value = "roi_X2022")
    private BigDecimal roiLastYear;

    @ExcelIgnore
    private String line;

}
