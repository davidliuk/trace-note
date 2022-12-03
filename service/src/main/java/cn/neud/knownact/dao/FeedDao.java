package cn.neud.knownact.dao;

import cn.neud.knownact.common.dao.BaseDao;
import cn.neud.knownact.model.entity.FeedEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Mapper
public interface FeedDao extends BaseDao<FeedEntity> {

    @Update("update feed " +
            "set rate = #{value} " +
            "where user_id = #{user_id} " +
            "and post_id = #{post_id}")
    int updateByItem(Long user_id, Long post_id, Double value);

    @Update("select count(id) " +
            "from feed " +
            "where user_id = #{user_id} " +
            "and post_id = #{post_id}")
    int selectByItem(Long user_id, Long post_id);
}