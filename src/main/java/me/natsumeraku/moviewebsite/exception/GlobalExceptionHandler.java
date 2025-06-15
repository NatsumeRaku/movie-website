package me.natsumeraku.moviewebsite.exception;

import me.natsumeraku.moviewebsite.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e, HttpServletRequest request) {
        logger.error("请求地址'{}',发生未知异常.", request.getRequestURI(), e);
        return Result.error("系统内部错误，请联系管理员");
    }
    
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        logger.error("请求地址'{}',发生运行时异常.", request.getRequestURI(), e);
        return Result.error("系统运行异常：" + e.getMessage());
    }
    
    @ExceptionHandler(SQLException.class)
    public Result<String> handleSQLException(SQLException e, HttpServletRequest request) {
        logger.error("请求地址'{}',发生数据库异常.", request.getRequestURI(), e);
        return Result.error("数据库操作异常，请稍后重试");
    }
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        logger.error("请求地址'{}',上传文件大小超限.", request.getRequestURI(), e);
        return Result.badRequest("上传文件大小超过限制");
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        logger.error("请求地址'{}',参数异常.", request.getRequestURI(), e);
        return Result.badRequest("参数错误：" + e.getMessage());
    }
    
    @ExceptionHandler(NullPointerException.class)
    public Result<String> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        logger.error("请求地址'{}',发生空指针异常.", request.getRequestURI(), e);
        return Result.error("系统内部错误，请联系管理员");
    }
}