package cn.neud.trace.model.excel.post;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Data
public class TagExcel {
    @Excel(name = "")
    private Long id;
    @Excel(name = "")
    private String name;

    private String icon;

    private Long postArticleCount;
    private Long concern_user_count;

}