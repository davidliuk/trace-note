package cn.neud.knownact.model.entity.post;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("post")
public class PostEntity implements Serializable {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
	private Long id;
    /**
     * 
     */
	private Long userId;
    /**
     * 
     */
	private String title;
    /**
     * 
     */
	private String content;
    /**
     *
     */
    private String brief;
    /**
     *
     */
    private String coverImage;
    /**
     * 
     */
	private Date createTime;
    /**
     * 
     */
	private Date updateTime;
    /**
     * 
     */
	private Long likes = 0L;
    /**
     * 
     */
	private Long dislike = 0L;
    /**
     * 
     */
	private Long favorite = 0L;
    /**
     * 
     */
	private Integer reviewStatus;
    /**
     * 
     */
	private Integer isDeleted = 0;

    public long like(boolean asc) {
        likes += asc? 1: -1;
        return likes;
    }

    public long dislike(boolean asc) {
        dislike += asc? 1: -1;
        return dislike;
    }

    public long favorite(boolean asc) {
        favorite += asc? 1: -1;
        return favorite;
    }
}