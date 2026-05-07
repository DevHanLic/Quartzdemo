package amp.demo.dto;

import amp.demo.entity.UserTest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;
    private Long timestamp;

    private  String policyNo;
    private  String policyStatus;
    private  String voidReason;
    private  String insuranceStartDate;
    private  String insuranceEndDate;
    private  String surrenderDate;
    private  String channelOrderNo;
    List<UserTest> userTestList;

    public ResultData() {
        this.timestamp = System.currentTimeMillis();
    }

    public ResultData(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public ResultData(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultData<T> success() {
        return new ResultData<>(200, "操作成功");
    }

    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(200, "操作成功", data);
    }

    public static <T> ResultData<T> success(String message, T data) {
        return new ResultData<>(200, message, data);
    }

    public static <T> ResultData<T> error(Integer code, String message) {
        return new ResultData<>(code, message);
    }

    public static <T> ResultData<T> error(String code, String message) {
        try {
            return new ResultData<>(Integer.parseInt(code), message);
        } catch (NumberFormatException e) {
            return new ResultData<>(500, message);
        }
    }

    public static <T> ResultData<T> error(String message) {
        return new ResultData<>(500, message);
    }

    public static <T> ResultData<T> error() {
        return new ResultData<>(500, "系统内部错误");
    }
}
