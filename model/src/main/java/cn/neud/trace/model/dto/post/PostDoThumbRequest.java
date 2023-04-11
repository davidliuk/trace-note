package cn.neud.trace.model.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * 点赞 / 取消点赞请求
 *
 * @author david
 */
@Data
public class PostDoThumbRequest implements Serializable {

    /**
     * 帖子 id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long postId;

    private static final long serialVersionUID = 1L;
}