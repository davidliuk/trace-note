package cn.neud.trace.common.validator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.neud.trace.common.exception.ErrorCode;
import cn.neud.trace.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 校验工具类
 *
 * @author David l729641074@163.com
 * @since 1.0.0
 */
public class AssertUtils {

    public static void isBlank(String str, String... params) {
        isBlank(str, ErrorCode.NOT_NULL.getCode(), params);
    }

    public static void isBlank(String str, Integer code, String... params) {
        if(code == null){
            throw new BusinessException(ErrorCode.NOT_NULL.getCode(), "code");
        }

        if (StringUtils.isBlank(str)) {
            throw new BusinessException(code, params);
        }
    }

    public static void isNull(Object object, String... params) {
        isNull(object, ErrorCode.NOT_NULL.getCode(), params);
    }

    public static void isNull(Object object, Integer code, String... params) {
        if(code == null){
            throw new BusinessException(ErrorCode.NOT_NULL.getCode(), "code");
        }

        if (object == null) {
            throw new BusinessException(code, params);
        }
    }

    public static void isArrayEmpty(Object[] array, String... params) {
        isArrayEmpty(array, ErrorCode.NOT_NULL.getCode(), params);
    }

    public static void isArrayEmpty(Object[] array, Integer code, String... params) {
        if(code == null){
            throw new BusinessException(ErrorCode.NOT_NULL.getCode(), "code");
        }

        if(ArrayUtil.isEmpty(array)){
            throw new BusinessException(code, params);
        }
    }

    public static void isListEmpty(List<?> list, String... params) {
        isListEmpty(list, ErrorCode.NOT_NULL.getCode(), params);
    }

    public static void isListEmpty(List<?> list, Integer code, String... params) {
        if(code == null){
            throw new BusinessException(ErrorCode.NOT_NULL.getCode(), "code");
        }

        if(CollUtil.isEmpty(list)){
            throw new BusinessException(code, params);
        }
    }

    public static void isMapEmpty(Map map, String... params) {
        isMapEmpty(map, ErrorCode.NOT_NULL.getCode(), params);
    }

    public static void isMapEmpty(Map map, Integer code, String... params) {
        if(code == null){
            throw new BusinessException(ErrorCode.NOT_NULL.getCode(), "code");
        }

        if(MapUtil.isEmpty(map)){
            throw new BusinessException(code, params);
        }
    }
}