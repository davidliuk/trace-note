package cn.neud.knownact.post.config;

import net.librec.data.model.JDBCDataModel;
import org.springframework.stereotype.Component;

import net.librec.conf.Configuration;

@Component
public class LibrecConfig {
    private static Configuration configuration;
    private static JDBCDataModel dataModel;

    static {
        configuration = new Configuration();
        configuration.set("dfs.data.dir", "./data");
        configuration.set("data.input.path", "/filmtrust/rating");
        configuration.set("data.column.format", "UIR");
        configuration.set("data.convert.jbdc.driverName", "com.mysql.cj.jdbc.Driver");
        configuration.set("data.convert.jbdc.URL", "jdbc:mysql://123.57.29.205/knownact?useUnicode=true&characterEncoding=UTF-8");
        configuration.set("data.convert.jbdc.user", "root");
        configuration.set("data.convert.jbdc.password", "DavidLiu7");
        configuration.set("data.convert.jbdc.tableName", "rate");
        configuration.set("data.convert.jbdc.userColName", "user_id");
        configuration.set("data.convert.jbdc.itemColName", "post_id");
        configuration.set("data.convert.jbdc.ratingColName", "rate");
        // configuration.set("data.convert.jbdc.datetimeColName", "");

        // set appender
        configuration.set("data.appender.class", "jdbcsocial");
        configuration.set("data.appender.jbdc.driverName", "com.mysql.cj.jdbc.Driver");
        configuration.set("data.appender.jbdc.URL", "jdbc:mysql://123.57.29.205/knownact?useUnicode=true&characterEncoding=UTF-8");
        configuration.set("data.appender.jbdc.user", "root");
        configuration.set("data.appender.jbdc.password", "DavidLiu7");
        configuration.set("data.appender.jbdc.tableName", "follow");
        configuration.set("data.appender.jbdc.userAColName", "follower");
        configuration.set("data.appender.jbdc.userBColName", "followee");
        configuration.set("data.appender.jbdc.ratingColName", "rate");
        configuration.set("rec.recommender.class", "trustsvd");
        configuration.set("rec.iterator.learnrate", "0.005");
        configuration.set("rec.iterator.learnrate.maximum", "-1");
        configuration.set("rec.iterator.maximum", "30");
        configuration.set("rec.user.regularization", "1.2");
        configuration.set("rec.item.regularization", "1.2");
        configuration.set("rec.social.regularization", "0.9");
        configuration.set("rec.bias.regularization", "1.2");
        configuration.set("rec.factor.number", "10");
        configuration.set("rec.learnrate.bolddriver", "false");
        configuration.set("rec.learnrate.decay", "1.0");
        configuration.set("rec.recommender.earlystop", "false");
        configuration.set("rec.recommender.verbose", "true");
        dataModel = new JDBCDataModel(configuration);
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static JDBCDataModel getDataModel() {
        return dataModel;
    }
}
