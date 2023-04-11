package cn.neud.trace.model.dto.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author david
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private static final long serialVersionUID = 1L;
}