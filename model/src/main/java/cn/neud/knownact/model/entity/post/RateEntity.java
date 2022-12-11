package cn.neud.knownact.model.entity.post;

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
@TableName("rate")
public class RateEntity implements Serializable {

    /**
     * 用户id
     */
	private Long userId;
    /**
     * 帖子id
     */
	private Long postId;
    /**
     * 评分0-5
     */
	private Integer rate;
    /**
     * 
     */
	private Integer likes = 0;
    /**
     * 
     */
	private Integer dislike = 0;
    /**
     * 
     */
	private Integer favorite = 0;

    public int updateRate() {
        this.rate = 2 + this.likes + this.favorite - dislike;
        return rate;
    }
    
    public boolean switchLike() {
        if (likes == 0) {
            likes = 1;
            return true;
        }
        likes = 0;
        return false;
    }
    
    public boolean switchDislike() {
        if (dislike == 0) {
            dislike = 1;
            return true;
        }
        dislike = 0;
        return false;
    }

    public boolean switchFavorite() {
        if (favorite == 0) {
            favorite = 1;
            return true;
        }
        favorite = 0;
        return false;
    }
}