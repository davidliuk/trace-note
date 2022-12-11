package cn.neud.knownact.model.entity.post;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Data
@TableName("tag")
public class TagEntity implements Serializable {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
	private Long id;
    /**
     * 
     */
    private String name;
	private String icon;

    private Long postArticleCount;
    private Long concern_user_count;
}