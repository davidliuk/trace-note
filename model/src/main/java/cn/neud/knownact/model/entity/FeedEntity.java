package cn.neud.knownact.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Data
@TableName("feed")
public class FeedEntity implements Serializable {

    /**
     * 
     */
	private Long id;
    /**
     * 
     */
	private Long userId;
    /**
     * 
     */
	private Long postId;
//    /**
//     *
//     */
//	private Long typeId;
    /**
     * 
     */
	private Double rate;
}