package cn.neud.knownact.common.service;

import cn.neud.knownact.model.dto.page.PageData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 *  CRUD基础服务接口
 *
 * @author David l729641074@163.com
 */
public interface CrudService<T, D> extends BaseService<T> {

    PageData<D> page(Map<String, Object> params);

    List<D> list(Map<String, Object> params);

    D get(Long id);

    void save(D dto);

    void update(D dto);

    void delete(Long[] ids);

    void delete(Map<String, Object> params);

    Page<T> page(Page<T> page, QueryWrapper<T> queryWrapper);
}