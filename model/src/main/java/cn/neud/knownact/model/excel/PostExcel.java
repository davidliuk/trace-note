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
public class PostExcel {
    @Excel(name = "")
    private Long id;
    @Excel(name = "")
    private Long userId;
    @Excel(name = "")
    private String title;
    @Excel(name = "")
    private String content;
    @Excel(name = "")
    private Date createTime;
    @Excel(name = "")
    private Date updateTime;
    @Excel(name = "")
    private Long like;
    @Excel(name = "")
    private Long dislike;
    @Excel(name = "")
    private Long favorite;
    @Excel(name = "")
    private Integer reviewStatus;
    @Excel(name = "")
    private Integer isDeleted;

}