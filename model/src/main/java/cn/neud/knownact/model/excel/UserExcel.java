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
public class UserExcel {
    @Excel(name = "")
    private Long id;
    @Excel(name = "")
    private String account;
    @Excel(name = "")
    private String password;
    @Excel(name = "")
    private String phone;
    @Excel(name = "")
    private String nickname;
    @Excel(name = "")
    private String icon;
    @Excel(name = "")
    private String role;
    @Excel(name = "")
    private Date createTime;
    @Excel(name = "")
    private Date updateTime;
    @Excel(name = "")
    private Integer isDeleted;

}