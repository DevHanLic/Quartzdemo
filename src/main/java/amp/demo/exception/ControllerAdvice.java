package amp.demo.exception;

import amp.demo.enums.MsgCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author han_lic
 * @date 2021/11/7 19:59
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object  HandlerMaxUploadSize(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        log.error("url: {}, msg: {}", request.getRequestURL(), ex.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("msgCd", MsgCodeEnum.IMAGE_SIZE_PASS_2M.getMsgCd());
        map.put("msgInfo",MsgCodeEnum.IMAGE_SIZE_PASS_2M.getMsgInfo());
        map.put("url", request.getRequestURL());
        return map;
    }
}
