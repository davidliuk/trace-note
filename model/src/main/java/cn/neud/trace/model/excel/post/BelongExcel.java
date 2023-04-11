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
public class BelongExcel {
    @Excel(name = "")
    private Long postId;
    @Excel(name = "")
    private Long tagId;

}