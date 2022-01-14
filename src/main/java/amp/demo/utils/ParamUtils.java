package amp.demo.utils;

import amp.demo.ExceptionHandler.MsgEnum;
import amp.demo.exception.BusinessException;
import amp.demo.utils.AlertCapable;
import amp.demo.utils.JudgeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 参数工具类 <br><br>
 *
 * <ul>
 * <li><b>指定参数校验方法并获取 推荐 {@link #getValidParam(Object, Predicate, AlertCapable)}</li>
 * <li>单个参数校验非空白并获取 推荐Lambda配合 {@link #getNotBlank(Object, Function)}, {@link #getNotBlank(Object, Function, AlertCapable)}</li>
 * <li>单个参数校验非空并获取 推荐Lambda配合 {@link #getNotNull(Object, Function)}, {@link #getNotNull(Object, Function, AlertCapable)}</li>
 * <br>
 * <li><b>参数校验</b> 推荐 {@link #isValid(Object, Function, Predicate, AlertCapable)}<br>
 * 需要 <b>创建参数数组</b> {@link #createParams(Object[])}</li>
 * <li>参数校验非空白 推荐 {@link #isNotBlankAll(String...)}, {@link #isNotBlankAll(AlertCapable, String...)} </li>
 * <li>参数校验非空 推荐 {@link #isNotNull(Object[])} )}, {@link #isNotNull(AlertCapable, Object[])}</li>
 * </ul>
 *
 * <p>
 * Example:
 * <ul>
 *     <li>{@code ParamUtils.getNotBlank(queryUserBankCardDO, LoanUserBankCardDO::getMobileNo); }</li>
 *     <li>{@code ParamUtils.getNotNull(queryUserBankCardDO, p -> p.getMobileNo()); }</li>
 *     <li>(不推荐) <s>{@code ParamUtils.getNotBlank(queryUserBankCardDO.getMobileNo()); }</s></li>
 *     <li>{@code ParamUtils.isNotBlankAll(queryUserBankCardDO, p -> ParamUtils.createParams(p.getMobileNo())); }</li>
 *     <li>(不推荐) <s>{@code ParamUtils.isNotNull(queryUserBankCardDO.getMobileNo()); }</s></li>
 *     <li>{@code ParamUtils.isValid(queryUserBankCardDO, p -> ParamUtils.createParams(p.getMobileNo()),
 *                 JudgeUtils::isNotBlankAll, MsgEnum.SYSTEM_ERROR); }</li>
 * </ul>
 * </p>
 *
 * @author JyCyun
 * @date 2021/11/23 16:28:02
 * @version 1.2
 */
public class ParamUtils {

    private final static char CHAR_LOWER_A = 'a';
    private final static char CHAR_LOWER_Z = 'z';

    /**
     * 创建参数数组 <br>
     * 与校验方法配合使用
     * <p>
     * Example: <br>
     * <ul>
     * <li>{@code ParamUtils.isNotBlankAll(loanOrderDO,
     *      p -> ParamUtils.createParams(p.getMobileNo())); }</li>
     * <li>{@code ParamUtils.isNotNull(loanOrderDO,
     *      p -> ParamUtils.createParams(p.getUserNo(), p.getMobileNo())); }</li>
     * <li>{@code ParamUtils.isValid(queryUserBankCardDO,
     *      p -> ParamUtils.createParams(p.getMobileNo()),
     *      JudgeUtils::isNotBlankAll, MsgEnum.SERIALNO_IS_NULL); }</li>
     * </ul>
     * </p>
     *
     * @see #isNotBlankAll(Object, Function)
     * @see #isNotBlankAll(Object, Function, AlertCapable)
     * @see #isNotNull(Object, Function)
     * @see #isNotNull(Object, Function, AlertCapable)
     * @see #isValid(Object, Function, Predicate, AlertCapable)
     * @param params 可变参数
     * @param <V> 参数类型
     * @return 参数数组
     * @since 1.0
     */
    @SafeVarargs
    public static <V> V[] createParams(V... params) {
        return params;
    }

    //getValidParam

    /**
     * 获取有效的参数值 <br>
     * 无效值将抛出指定异常
     *
     * @param param 参数值
     * @param validation 校验方法
     * @param alertCapable 错误码
     * @param <V> 值类型
     * @return 有效的参数值
     * @since 1.0
     */
    public static <V> V getValidParam(V param, Predicate<V> validation, AlertCapable alertCapable) {
        return Optional.ofNullable(param).filter(validation)
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    //isValid

    /**
     * 校验参数值 (by Optional, <b>推荐</b>)<br>
     * 有任一空白值将抛出指定异常
     * <p>
     * Example:
     * <pre> {@code ParamUtils.isValid(queryUserBankCardDO,
     *      p -> ParamUtils.createParams(p.getMobileNo()),
     *      JudgeUtils::isNotBlankAll, MsgEnum.SERIALNO_IS_NULL); }</pre></p>
     *
     * @param object 对象
     * @param paramsFunction 参数提供方法
     * @param validation 校验方法
     * @param alertCapable 错误码
     * @param <T> 对象类型
     * @param <V> 参数类型
     * @since 1.0
     */
    public static <T, V> void isValid(T object, Function<T, V[]> paramsFunction,
                                      Predicate<V> validation, AlertCapable alertCapable) {
        Optional.ofNullable(object).map(paramsFunction).filter(p -> Arrays.stream(p).allMatch(validation))
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    //getNotNull

    /**
     * 获取非空参数值 <br>
     * 空白值将抛出 参数为空 {@link MsgEnum#SERIALNO_IS_NULL} 异常
     * <p>
     * Example: <br>
     * <pre>{@code String mobileNo = ParamUtils.getNotNull(loanOrderDO.getMobileNo()); }</pre>
     * </p>
     *
     * @see #getNotNull(Object, AlertCapable)
     * @see #getNotNull(Object, Function)
     * @see #getNotNull(Object, Function, AlertCapable)
     * @param param 参数值
     * @param <V> 值类型
     * @return 有效的参数值
     */
    public static <V> V getNotNull(V param) {
        return getNotNull(param, MsgEnum.SERIALNO_IS_NULL);
    }

    /**
     * 获取非空参数值 <br>
     * 空值将抛出指定异常
     *
     * @see #getNotNull(Object)
     * @see #getNotNull(Object, Function)
     * @see #getNotNull(Object, Function, AlertCapable)
     * @param param 参数值
     * @param alertCapable 错误码
     * @param <V> 值类型
     * @return 有效的参数值
     */
    public static <V> V getNotNull(V param, AlertCapable alertCapable) {
        return Optional.ofNullable(param).filter(JudgeUtils::isNotNull)
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    /**
     * 获取非空参数值 (by {@link Optional}, for Lambda, <b>推荐</b>) <br>
     * 空值将抛出 参数为空 {@link MsgEnum#SERIALNO_IS_NULL} 异常
     * <p>
     * Example:
     * <pre> {@code String mobileNo = ParamUtils.getNotNull(loanOrderDO,
     *      LoanOrderDO::getMobileNo); }</pre>
     * </p>
     *
     * @see #getNotNull(Object)
     * @see #getNotNull(Object, AlertCapable)
     * @see #getNotNull(Object, Function)
     * @param param 参数值
     * @param function 参数提供方法
     * @param <T> 对象
     * @param <V> 值类型
     * @return 有效的参数值
     */
    public static <T, V> V getNotNull(T param, Function<T, V> function) {
        return getNotNull(param, function, MsgEnum.SERIALNO_IS_NULL);

    }

    /**
     * 获取非空参数值 (by {@link Optional}, for Lambda, <b>推荐</b>) <br>
     * 空值将抛出指定异常
     *
     * @see #getNotNull(Object)
     * @see #getNotNull(Object, AlertCapable)
     * @see #getNotNull(Object, Function)
     * @param param 参数值
     * @param function 参数提供方法
     * @param alertCapable 错误码
     * @param <T> 对象
     * @param <V> 值类型
     * @return 有效的参数值
     */
    public static <T, V> V getNotNull(T param, Function<T, V> function, AlertCapable alertCapable) {
        return Optional.ofNullable(function.apply(param)).filter(JudgeUtils::isNotNull)
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    //getNotNullOptional

    /**
     * 获取非空参数值 (by {@link Optional}, for Lambda, <b>推荐</b>) <br>
     * 空值将抛出 参数为空 {@link MsgEnum#SERIALNO_IS_NULL} 异常
     * <p>
     * Example:
     * <pre> {@code String mobileNo = ParamUtils.getNotNullOptional(loanOrderDO,
     *      p -> p.map(LoanOrderDO::getMobileNo)); }</pre>
     * </p>
     *
     * @see #getNotNullOptional(Object, Function, AlertCapable)
     * @param param 参数值
     * @param function 参数提供方法
     * @param <T> 对象
     * @param <V> 值类型
     * @return 有效的参数值
     */
    public static <T, V> V getNotNullOptional(T param, Function<Optional<T>, Optional<V>> function) {
        return getNotNullOptional(param, function, MsgEnum.SERIALNO_IS_NULL);

    }

    /**
     * 获取非空参数值 (by {@link Optional}, for Lambda, <b>推荐</b>) <br>
     * 空值将抛出指定异常
     *
     * @see #getNotNull(Object)
     * @see #getNotNull(Object, AlertCapable)
     * @see #getNotNull(Object, Function)
     * @param param 参数值
     * @param function 参数提供方法
     * @param alertCapable 错误码
     * @param <T> 对象
     * @param <V> 值类型
     * @return 有效的参数值
     */
    public static <T, V> V getNotNullOptional(T param, Function<Optional<T>, Optional<V>> function, AlertCapable alertCapable) {
        return function.apply(Optional.ofNullable(param)).filter(JudgeUtils::isNotNull)
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    //getNotBlank

    /**
     * 获取非空白字符串参数 <br>
     * 空白值将抛出 参数为空 {@link MsgEnum#SERIALNO_IS_NULL} 异常
     * <p>
     * Example: <br>
     * <pre>{@code String mobileNo = ParamUtils.getNotBlank(loanOrderDO.getMobileNo()); }</pre>
     * </p>
     *
     * @see #getNotBlank(String, AlertCapable)
     * @see #getNotBlank(Object, Function)
     * @see #getNotBlank(Object, Function, AlertCapable)
     * @param param 参数值
     * @return 有效的参数值
     */
    public static String getNotBlank(String param) {
        return getNotBlank(param, MsgEnum.SERIALNO_IS_NULL);
    }

    /**
     * 获取非空白字符串参数 <br>
     * 空白值将抛出指定异常
     *
     * @see #getNotBlank(String)
     * @see #getNotBlank(Object, Function)
     * @see #getNotBlank(Object, Function, AlertCapable)
     * @param param 参数值
     * @param alertCapable 错误码
     * @return 有效的参数值
     */
    public static String getNotBlank(String param, AlertCapable alertCapable) {
        return Optional.ofNullable(param).filter(JudgeUtils::isNotBlankAll)
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    /**
     * 获取非空白字符串参数 (by {@link Optional}, for Lambda, <b>推荐</b>) <br>
     * 空白值将抛出 参数为空 {@link MsgEnum#SERIALNO_IS_NULL} 异常
     * <p>
     * Example:
     * <pre> {@code String mobileNo = ParamUtils.getNotBlank(loanOrderDO,
     *      LoanOrderDO::getMobileNo); }</pre>
     * </p>
     *
     * @see #getNotBlank(String)
     * @see #getNotBlank(String, AlertCapable)
     * @see #getNotBlank(Object, Function, AlertCapable)
     * @param param 参数值
     * @param function 参数提供方法
     * @param <T> 对象类型
     * @return 有效的参数值
     */
    public static <T> String getNotBlank(T param, Function<T, String> function) {
        return getNotBlank(param, function, MsgEnum.SERIALNO_IS_NULL);

    }

    /**
     * 获取非空白字符串参数 (by {@link Optional}, for Lambda, <b>推荐</b>) <br>
     * 空白值将抛出指定异常
     *
     * @see #getNotBlank(String)
     * @see #getNotBlank(String, AlertCapable)
     * @see #getNotBlank(Object, Function)
     * @param param 参数值
     * @param function 参数提供方法
     * @param alertCapable 错误码
     * @param <T> 对象类型
     * @return 有效的参数值
     */
    public static <T> String getNotBlank(T param, Function<T, String> function, AlertCapable alertCapable) {
        return Optional.ofNullable(function.apply(param)).filter(JudgeUtils::isNotBlankAll)
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    //getNotBlankOptional

    /**
     * 获取非空白字符串参数 (by {@link Optional}, for Lambda, <b>推荐</b>) <br>
     * 空白值将抛出 参数为空 {@link MsgEnum#SERIALNO_IS_NULL} 异常
     * <p>
     * Example:
     * <pre> {@code String mobileNo = ParamUtils.getNotBlankOptional(loanOrderDO,
     *      p -> p.map(LoanOrderDO::getMobileNo)); }</pre>
     * </p>
     *
     * @see #getNotBlankOptional(Object, Function, AlertCapable)
     * @param param 参数值
     * @param function 参数提供方法
     * @param <T> 对象类型
     * @return 有效的参数值
     */
    public static <T> String getNotBlankOptional(T param, Function<Optional<T>, Optional<String>> function) {
        return getNotBlankOptional(param, function, MsgEnum.SERIALNO_IS_NULL);

    }

    /**
     * 获取非空白字符串参数 (by {@link Optional}, for Lambda, <b>推荐</b>) <br>
     * 空白值将抛出指定异常
     *
     * @see #getNotBlankOptional(Object, Function)
     * @param param 参数值
     * @param function 参数提供方法
     * @param alertCapable 错误码
     * @param <T> 对象类型
     * @return 有效的参数值
     */
    public static <T> String getNotBlankOptional(T param, Function<Optional<T>, Optional<String>> function, AlertCapable alertCapable) {
        return function.apply(Optional.ofNullable(param)).filter(JudgeUtils::isNotBlankAll)
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    //isNotBlankAll

    /**
     * 校验参数值 <br>
     * 有任一空白值将抛出 参数为空 {@link MsgEnum#SERIALNO_IS_NULL} 异常
     * <p>
     * Example: <br>
     * <ul>
     * <li>{@code ParamUtils.isNotBlankAll(loanOrderDO.getMobileNo()); }</li>
     * <li>{@code ParamUtils.isNotBlankAll(loanOrderDO.getUserNo(), loanOrderDO.getMobileNo()); }</li>
     * </ul>
     * </p>
     *
     * @see #isNotBlankAll(AlertCapable, String...)
     * @see #isNotBlankAll(Object, Function)
     * @see #isNotBlankAll(Object, Function, AlertCapable)
     * @param params 参数值
     */
    public static void isNotBlankAll(String... params) {
        isNotBlankAll(MsgEnum.SERIALNO_IS_NULL, params);
    }

    /**
     * 校验参数值 <br>
     * 有任一空白值将抛出指定异常
     *
     * @see #isNotBlankAll(String...)
     * @see #isNotBlankAll(Object, Function)
     * @see #isNotBlankAll(Object, Function, AlertCapable)
     * @param alertCapable 错误码
     * @param params 参数值
     */
    public static void isNotBlankAll(AlertCapable alertCapable, String... params) {
        Optional.ofNullable(params).filter(s -> !JudgeUtils.isBlankAny(s))
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    /**
     * 校验参数值 (by Optional, <b>推荐</b>)<br>
     * 有任一空白值将抛出 参数为空 {@link MsgEnum#SERIALNO_IS_NULL} 异常
     * <p>
     * Example: <br>
     * <ul>
     * <li>{@code ParamUtils.isNotBlankAll(loanOrderDO,
     *      p -> ParamUtils.createParams(p.getMobileNo())); }</li>
     * <li>{@code ParamUtils.isNotBlankAll(loanOrderDO,
     *      p -> ParamUtils.createParams(p.getUserNo(), p.getMobileNo())); }</li>
     * </ul>
     * </p>
     *
     * @see #isNotBlankAll(String...)
     * @see #isNotBlankAll(AlertCapable, String...)
     * @see #isNotBlankAll(Object, Function, AlertCapable)
     * @param object 对象
     * @param function 参数提供方法
     * @param <T> 对象类型
     */
    public static <T> void isNotBlankAll(T object, Function<T, String[]> function) {
        isNotBlankAll(object, function, MsgEnum.SERIALNO_IS_NULL);
    }

    /**
     * 校验参数值 (by Optional, <b>推荐</b>)<br>
     * 有任一空白值将抛出指定异常
     * <p>
     * Example: <br>
     * <ul>
     * <li>{@code ParamUtils.isNotBlankAll(loanOrderDO,
     *      p -> ParamUtils.createParams(p.getMobileNo()), MsgEnum.SERIALNO_IS_NULL); }</li>
     * <li>{@code ParamUtils.isNotBlankAll(loanOrderDO,
     *      p -> ParamUtils.createParams(p.getUserNo(),
     *      p.getMobileNo()), MsgEnum.SERIALNO_IS_NULL); }</li>
     * </ul>
     * </p>
     *
     * @see #isNotBlankAll(String...)
     * @see #isNotBlankAll(AlertCapable, String...)
     * @see #isNotBlankAll(Object, Function)
     * @param object 对象
     * @param function 参数提供方法
     * @param alertCapable 错误码
     * @param <T> 对象类型
     */
    public static <T> void isNotBlankAll(T object, Function<T, String[]> function, AlertCapable alertCapable) {
        Optional.ofNullable(object).map(function).filter(s -> !JudgeUtils.isBlankAny(s))
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    //isNotNull

    /**
     * 校验参数值 <br>
     * 有任一空白值将抛出 参数为空 {@link MsgEnum#SERIALNO_IS_NULL} 异常
     * <p>
     * Example: <br>
     * <ul>
     * <li>{@code ParamUtils.isNotNull(loanOrderDO.getMobileNo()); }</li>
     * <li>{@code ParamUtils.isNotNull(loanOrderDO.getUserNo(), loanOrderDO.getMobileNo()); }</li>
     * </ul>
     * </p>
     *
     * @param params 参数
     * @param <V> 参数类型
     * @since 1.0
     */
    @SafeVarargs
    public static <V> void isNotNull(V... params) {
        isNotNull(MsgEnum.SERIALNO_IS_NULL, params);
    }

    /**
     * 校验参数值 <br>
     * 有任一空白值将抛出指定异常
     *
     * @param alertCapable 错误码
     * @param params 参数
     * @param <V> 参数类型
     * @since 1.0
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <V> void isNotNull(AlertCapable alertCapable, V... params) {
        Optional.ofNullable(params).filter(JudgeUtils::isNotNullAny)
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    /**
     * 校验参数值 (by Optional, <b>推荐</b>)<br>
     * 有任一空白值将抛出 参数为空 {@link MsgEnum#SERIALNO_IS_NULL} 异常
     * <p>
     * Example: <br>
     * <ul>
     * <li>{@code ParamUtils.isNotNull(loanOrderDO,
     *      p -> ParamUtils.createParams(p.getMobileNo())); }</li>
     * <li>{@code ParamUtils.isNotNull(loanOrderDO,
     *      p -> ParamUtils.createParams(p.getUserNo(), p.getMobileNo())); }</li>
     * </ul>
     * </p>
     *
     * @param object 对象
     * @param paramsFunction 参数提供方法
     * @param <T> 对象类型
     * @param <V> 参数类型
     * @since 1.0
     */
    public static <T, V> void isNotNull(T object, Function<T, V[]> paramsFunction) {
        isNotNull(object, paramsFunction, MsgEnum.SERIALNO_IS_NULL);
    }

    /**
     * 校验参数值 (by Optional, <b>推荐</b>)<br>
     * 有任一空白值将抛出指定异常
     * <p>
     * Example: <br>
     * <ul>
     * <li>{@code ParamUtils.isNotNull(loanOrderDO,
     *      p -> ParamUtils.createParams(p.getMobileNo()), MsgEnum.SERIALNO_IS_NULL); }</li>
     * <li>{@code ParamUtils.isNotNull(loanOrderDO,
     *      p -> ParamUtils.createParams(p.getUserNo(),
     *      p.getMobileNo()), MsgEnum.SERIALNO_IS_NULL); }</li>
     * </ul>
     * </p>
     *
     * @param object 对象
     * @param paramsFunction 参数提供方法
     * @param alertCapable 错误码
     * @param <T> 对象类型
     * @param <V> 参数类型
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public static <T, V> void isNotNull(T object, Function<T, V[]> paramsFunction, AlertCapable alertCapable) {
        Optional.ofNullable(object).map(paramsFunction).filter(JudgeUtils::isNotNullAny)
                .orElseThrow(() -> new BusinessException(alertCapable));
    }

    /**
     * 判断对象是否有空白字段
     * @param object 对象
     * @param <T> 对象类型
     * @return 结果
     * @since 1.1
     */
    public static <T> boolean isFieldBlankAny(T object) {
        if (object == null) {
            return true;
        }
        try {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fName = getPascalCaseName(field.getName());
                Method getter = object.getClass().getMethod("get" + fName);
                Object oValue = getter.invoke(object);
                if (oValue == null || "".equals(oValue.toString())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 校验参数
     * @param value 参数值
     * @param validator 校验器
     * @param validCallback 有效时回调
     * @param invalidCallback 无效时回调
     * @param <V> 参数类型
     * @return 是否有效
     * @since 1.2
     */
    public static <V> boolean validParam(V value, Function<V, Boolean> validator,
                                          Consumer<V> validCallback, Consumer<V> invalidCallback) {
        return validParam(value, null, validator, validCallback, invalidCallback);
    }

    /**
     * 校验参数
     * @param value 参数值
     * @param converter 转换器
     * @param validator 校验器
     * @param validCallback 有效时回调
     * @param invalidCallback 无效时回调
     * @param <V> 参数转换前类型
     * @param <R> 参数转换后类型
     * @return 是否有效
     * @since 1.2
     */
    @SuppressWarnings("unchecked")
    public static <V, R> boolean validParam(V value, Function<V, R> converter, Function<R, Boolean> validator,
                                      Consumer<R> validCallback, Consumer<V> invalidCallback) {
        boolean isValid;
        R result;
        if (null == converter) {
            result = (R) value;
        } else {
            result = converter.apply(value);
        }
        if (null != validator) {
            isValid = validator.apply(result);
        } else {
            isValid = null != result;
        }
        if (!isValid) {
            if (null != invalidCallback) {
                invalidCallback.accept(value);
            }
            return false;
        }
        if (null != validCallback) {
            validCallback.accept(result);
        }
        return true;
    }

    /**
     * 字符串转PascalCase
     * @param str 待转换的字符串
     * @return PascalCase
     */
    private static String getPascalCaseName(String str) {
        char ch;
        if (str == null || str.length() == 0) {
            return str;
        }
        if ((ch = str.charAt(0)) < CHAR_LOWER_A || ch > CHAR_LOWER_Z) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }

}
