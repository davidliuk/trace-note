package cn.neud.trace.model.excel.feed;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Data
public class FeedExcel {
    @Excel(name = "")
    private Long id;
    @Excel(name = "")
    private Long userId;
    @Excel(name = "")
    private Long postId;
//    @Excel(name = "")
//    private Long tagId;
    @Excel(name = "")
    private Double rate;

}