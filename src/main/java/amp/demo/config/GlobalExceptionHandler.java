package amp.demo.config;

import amp.demo.dto.ResultData;
import amp.demo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResultData<Void>> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        log.error("业务异常: URL={}, Code={}, Message={}", request.getRequestURI(), ex.getCode(), ex.getMessage());
        
        ResultData<Void> result = ResultData.error(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultData<Void>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("参数校验异常: URL={}", request.getRequestURI());
        
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        
        log.error("校验失败详情: {}", errorMessage);
        
        ResultData<Void> result = ResultData.error(400, "参数校验失败: " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResultData<Void>> handleBindException(BindException ex, HttpServletRequest request) {
        log.error("绑定异常: URL={}", request.getRequestURI());
        
        String errorMessage = ex.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        
        ResultData<Void> result = ResultData.error(400, "参数绑定失败: " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResultData<Void>> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        log.error("约束违反异常: URL={}", request.getRequestURI());
        
        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        
        ResultData<Void> result = ResultData.error(400, "参数验证失败: " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResultData<Void>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.error("参数类型不匹配异常: URL={}, Parameter={}, RequiredType={}", 
                request.getRequestURI(), ex.getName(), ex.getRequiredType());
        
        ResultData<Void> result = ResultData.error(400, 
                String.format("参数类型错误: 参数 '%s' 应为 '%s' 类型", 
                        ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResultData<Void>> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("非法参数异常: URL={}, Message={}", request.getRequestURI(), ex.getMessage());
        
        ResultData<Void> result = ResultData.error(400, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultData<Void>> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("系统异常: URL={}, Message={}", request.getRequestURI(), ex.getMessage(), ex);
        
        ResultData<Void> result = ResultData.error(500, "系统内部错误，请稍后重试");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}