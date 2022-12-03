package cn.neud.knownact.model.excel;

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
public class FollowExcel {
    @Excel(name = "关注人")
    private Long follower;
    @Excel(name = "被关注人")
    private Long followee;
    @Excel(name = "信任程度，0-5，默认1")
    private Integer rate;

}