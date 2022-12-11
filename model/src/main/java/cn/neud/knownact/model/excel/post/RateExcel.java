package cn.neud.knownact.model.excel.post;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Data
public class RateExcel {
    @Excel(name = "用户id")
    private Long userId;
    @Excel(name = "帖子id")
    private Long postId;
    @Excel(name = "评分0-5")
    private Integer rate;
    @Excel(name = "")
    private Integer like;
    @Excel(name = "")
    private Integer dislike;
    @Excel(name = "")
    private Integer favorite;

}