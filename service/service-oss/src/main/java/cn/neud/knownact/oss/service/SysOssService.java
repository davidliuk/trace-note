package cn.neud.knownact.oss.service;

import cn.neud.knownact.model.dto.page.PageData;
import cn.neud.knownact.common.service.BaseService;
import cn.neud.knownact.oss.entity.SysOssEntity;

import java.util.Map;

/**
 * 文件上传
 * 
 * @author David l729641074@163.com
 */
public interface SysOssService extends BaseService<SysOssEntity> {

	PageData<SysOssEntity> page(Map<String, Object> params);
}
