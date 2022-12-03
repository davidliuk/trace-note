package cn.neud.knownact.dao;

import cn.neud.knownact.common.dao.BaseDao;
import cn.neud.knownact.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {
	
}