package cn.neud.trace.model.dto.page;

import cn.neud.trace.model.constant.Constant;
import lombok.Data;

/**
 * 分页请求
 *
 * @author david
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = Constant.ASC;
}
