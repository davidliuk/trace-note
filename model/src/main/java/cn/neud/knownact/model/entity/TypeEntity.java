package cn.neud.knownact.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Data
@TableName("type")
public class TypeEntity {

    /**
     * 
     */
	private Long id;
    /**
     * 
     */
	private String name;
}