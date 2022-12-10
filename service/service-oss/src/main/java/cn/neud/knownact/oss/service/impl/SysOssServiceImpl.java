package cn.neud.knownact.oss.service.impl;

import cn.neud.knownact.model.constant.Constant;
import cn.neud.knownact.model.dto.page.PageData;
import cn.neud.knownact.common.service.impl.BaseServiceImpl;
import cn.neud.knownact.oss.dao.SysOssDao;
import cn.neud.knownact.oss.service.SysOssService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.neud.knownact.oss.entity.SysOssEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class SysOssServiceImpl extends BaseServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

	@Override
	public PageData<SysOssEntity> page(Map<String, Object> params) {
		IPage<SysOssEntity> page = baseDao.selectPage(
			getPage(params, Constant.CREATE_DATE, false),
			new QueryWrapper<>()
		);
		return getPageData(page, SysOssEntity.class);
	}
}
