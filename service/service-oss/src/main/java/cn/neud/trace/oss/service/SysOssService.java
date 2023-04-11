package cn.neud.trace.oss.service;

import cn.neud.trace.model.dto.page.PageData;
import cn.neud.trace.common.service.BaseService;
import cn.neud.trace.oss.entity.SysOssEntity;

import java.util.Map;

/**
 * 文件上传
 * 
 * @author David l729641074@163.com
 */
public interface SysOssService extends BaseService<SysOssEntity> {

	PageData<SysOssEntity> page(Map<String, Object> params);
}
