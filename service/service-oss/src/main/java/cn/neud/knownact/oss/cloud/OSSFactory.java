package cn.neud.knownact.oss.cloud;

import cn.neud.knownact.model.constant.Constant;
import cn.neud.knownact.common.utils.SpringContextUtils;

/**
 * 文件上传Factory
 * @author David l729641074@163.com
 */
public final class OSSFactory {
//    private static SysParamsService sysParamsService;
//
//    static {
//        OSSFactory.sysParamsService = SpringContextUtils.getBean(SysParamsService.class);
//    }

    public static AbstractCloudStorageService build(){
        //获取云存储配置信息
//        CloudStorageConfig config = sysParamsService.getValueObject(Constant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
        CloudStorageConfig config = new CloudStorageConfig();
        config.setType(3);
        config.setQcloudDomain("https://neusurvey-1309929060.cos.ap-beijing.myqcloud.com");
        config.setQcloudPrefix("/");
        config.setQcloudAppId(1309929060);
        config.setQcloudSecretId("AKIDiBOK0Q9bmhLktnRow4yd3D7k6BzVpsd6");
        config.setQcloudSecretKey("GFwNYTAdrrZ0B7nDNPCiyDKBl6B0av2t");
        config.setQcloudBucketName("neusurvey");
        config.setQcloudRegion("ap-beijing");
        if(config.getType() == Constant.CloudService.QINIU.getValue()){
            return new QiniuCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
            return new AliyunCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
            return new QcloudCloudStorageService(config);
        }

        return null;
    }

}
