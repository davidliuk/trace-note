package cn.neud.knownact.feed.service.impl;

import cn.neud.knownact.common.exception.BusinessException;
import cn.neud.knownact.common.exception.ErrorCode;
import cn.neud.knownact.feed.dao.FeedDao;
import cn.neud.knownact.feed.config.LibrecConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.model.dto.FeedDTO;
import cn.neud.knownact.model.entity.FeedEntity;
import cn.neud.knownact.feed.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.data.model.JDBCDataModel;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.context.rating.TrustSVDRecommender;
import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.CosineSimilarity;
import net.librec.similarity.RecommenderSimilarity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Service
@Slf4j
public class FeedServiceImpl extends CrudServiceImpl<FeedDao, FeedEntity, FeedDTO> implements FeedService {

    @Override
    public QueryWrapper<FeedEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<FeedEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void train() {
        Configuration conf = LibrecConfig.getConfiguration();
        try {
            JDBCDataModel dataModel = new JDBCDataModel(conf);
            System.out.println("===================");
            System.out.println("Start Training Data Model");
            System.out.println("===================");
            dataModel.buildDataModel();

            // build recommender context
            RecommenderContext context = new RecommenderContext(conf, dataModel);

            // build similarity
            RecommenderSimilarity similarity = new CosineSimilarity();
            similarity.buildSimilarityMatrix(dataModel);
            context.setSimilarity(similarity);

            // build recommender
            Recommender recommender = new TrustSVDRecommender();
            recommender.setContext(context);

            // run recommender algorithm
            recommender.train(context);

//        // evaluate the recommended result
//        EvalContext evalContext = new EvalContext(conf, recommender, dataModel.getTestDataSet(), context.getSimilarity().getSimilarityMatrix(), context.getSimilarities());
//        RecommenderEvaluator ndcgEvaluator = new NormalizedDCGEvaluator();
//        ndcgEvaluator.setTopN(10);
//        double ndcgValue = ndcgEvaluator.evaluate(evalContext);
//        System.out.println("ndcg:" + ndcgValue);

            // 得到过滤后的结果
            List<RecommendedItem> recommendedItemList = recommender.getRecommendedList(recommender.recommendRating(context.getDataModel().getTestDataSet()));
            for (RecommendedItem item : recommendedItemList) {
                System.out.println(item.getItemId() + ',' + item.getUserId() + ',' + item.getValue());
            }
            saveResult(recommendedItemList);
        } catch (LibrecException e) {
            throw new BusinessException(ErrorCode.TRAIN_ERROR);
        } finally {
            conf.setBoolean("data.convert.read.ready", false);
            conf.setBoolean("data.appender.read.ready", false);
        }
    }

    // 保存推荐结果
    @Transactional
    void saveResult(List<RecommendedItem> recommendedList) throws LibrecException {
        baseDao.truncateTable();
        baseDao.saveBatch(recommendedList);
//        if (recommendedList != null && recommendedList.size() > 0) {
//            for (RecommendedItem recItem : recommendedList) {
//                FeedEntity feed = new FeedEntity();
//                Long userId = Long.valueOf(recItem.getUserId());
//                Long itemId = Long.valueOf(recItem.getItemId());
//                Double value = recItem.getValue();
//                if (baseDao.selectByItem(userId, itemId).size() == 0) {
//                    feed.setUserId(userId);
//                    feed.setPostId(itemId);
//                    feed.setRate(value);
//                    baseDao.insert(feed);
//                } else {
//                    baseDao.updateByItem(userId, itemId, value);
//                }
//            }
//        }
    }
}