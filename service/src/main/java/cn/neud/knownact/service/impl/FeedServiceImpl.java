package cn.neud.knownact.service.impl;

import cn.neud.knownact.config.LibrecConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.dao.FeedDao;
import cn.neud.knownact.model.dto.FeedDTO;
import cn.neud.knownact.model.entity.FeedEntity;
import cn.neud.knownact.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.data.model.JDBCDataModel;
import net.librec.eval.EvalContext;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.ranking.NormalizedDCGEvaluator;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.context.rating.TrustSVDRecommender;
import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.CosineSimilarity;
import net.librec.similarity.RecommenderSimilarity;
import net.librec.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Service
@Slf4j
public class FeedServiceImpl extends CrudServiceImpl<FeedDao, FeedEntity, FeedDTO> implements FeedService {

    @Override
    public QueryWrapper<FeedEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<FeedEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Async
    @Override
    public void train() throws Exception {
        Configuration conf = LibrecConfig.getConfiguration();
        JDBCDataModel dataModel = new JDBCDataModel(conf);
        dataModel.buildDataModel();

        System.out.println("===================");
        System.out.println(conf.get("data.appender.class"));
        System.out.println("===================");

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
        List<RecommendedItem> recommendedItemList = recommender.getRecommendedList(recommender.recommendRank());
        System.out.println(recommendedItemList);
        for (RecommendedItem item: recommendedItemList) {
            System.out.println(item);
            System.out.println(item.getItemId() + ',' + item.getUserId() + ',' + item.getValue());
        }
        saveResult(recommendedItemList);
    }

    // 保存推荐结果
    private void saveResult(List<RecommendedItem> recommendedList) throws LibrecException, IOException, ClassNotFoundException {
        if (recommendedList != null && recommendedList.size() > 0) {
            for (RecommendedItem recItem : recommendedList) {
                FeedEntity feed = new FeedEntity();
                Long userId = Long.valueOf(recItem.getUserId());
                Long itemId = Long.valueOf(recItem.getItemId());
                Double value = recItem.getValue();
                if (baseDao.selectByItem(userId, itemId) == 0) {
                    feed.setUserId(userId);
                    feed.setPostId(itemId);
                    feed.setRate(value);
                    baseDao.insert(feed);
                } else {
                    baseDao.updateByItem(userId, itemId, value);
                }
            }
        }
    }
}