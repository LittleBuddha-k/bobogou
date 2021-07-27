package com.littlebuddha.bobogou.common.exception;

import com.alibaba.fastjson.JSON;
import com.littlebuddha.bobogou.common.exception.errorcode.CustomizeErrorCode;
import com.littlebuddha.bobogou.common.exception.serviceexception.CustomizeException;
import com.littlebuddha.bobogou.common.response.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handler(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response){
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResponseResult responseResult = null;
            //返回json
            if(ex instanceof CustomizeException){
                responseResult = ResponseResult.errorOf((CustomizeException) ex);
            }else {
                responseResult = ResponseResult.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(responseResult));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            //跳转错误页面
            if(ex instanceof CustomizeException){
                HttpStatus status = getStatus(request);
                model.addAttribute("message",ex.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
