package cn.neud.trace.note.utils;

import cn.hutool.core.util.StrUtil;
import cn.neud.trace.note.constant.RegexPatternConstant;

/**
 * @author David
 */
public class RegexUtils {
    /**
     * 是否是无效手机格式
     *
     * @param phone 要校验的手机号
     * @return true:符合，false：不符合
     */
    public static boolean isPhoneInvalid(String phone) {
        return mismatch(phone, RegexPatternConstant.PHONE_REGEX);
    }

    /**
     * 是否是无效邮箱格式
     *
     * @param email 要校验的邮箱
     * @return true:符合，false：不符合
     */
    public static boolean isEmailInvalid(String email) {
        return mismatch(email, RegexPatternConstant.EMAIL_REGEX);
    }

    /**
     * 是否是无效验证码格式
     *
     * @param code 要校验的验证码
     * @return true:符合，false：不符合
     */
    public static boolean isCodeInvalid(String code) {
        return mismatch(code, RegexPatternConstant.VERIFY_CODE_REGEX);
    }

    // 校验是否不符合正则格式
    private static boolean mismatch(String str, String regex) {
        if (StrUtil.isBlank(str)) {
            return true;
        }
        return !str.matches(regex);
    }
}
