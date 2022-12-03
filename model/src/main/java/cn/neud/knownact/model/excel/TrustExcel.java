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
public class TrustExcel {
    @Excel(name = "")
    private Integer userA;
    @Excel(name = "")
    private Integer userB;
    @Excel(name = "")
    private Integer rate;
    @Excel(name = "")
    private Integer like;
    @Excel(name = "")
    private Integer dislike;
    @Excel(name = "")
    private Integer favorite;

}