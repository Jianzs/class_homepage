package top.zhengsj.klass.exception.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zhengsj.klass.exception.NoAuthorizationException;
import top.zhengsj.klass.exception.OperateErrorException;
import top.zhengsj.klass.pojo.dto.ResponseDto;

import javax.servlet.http.HttpServletResponse;
import java.security.GeneralSecurityException;

@RestControllerAdvice
public class DefaultExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public ResponseDto defaultErrorHandler(HttpServletResponse response, Exception e) {
        // 获取异常栈
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(e.fillInStackTrace()).append("\n");

        for (StackTraceElement element : stackTrace) {
            sb.append("\tat ").append(element).append("\n");
        }

        ResponseDto responseDto = ResponseDto.failed();
        if (e instanceof NoAuthorizationException) {
            logger.info(sb.toString());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseDto.setMessage("You don't have authority.");

        } else if (e instanceof OperateErrorException ||
                e instanceof HttpRequestMethodNotSupportedException) {
            logger.info(sb.toString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseDto.setMessage(e.getMessage());

        } else if (e instanceof JWTDecodeException) {
            logger.info(sb.toString());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseDto.setMessage("Token is wrong");
        }  else if (e instanceof JSONException ||
                e instanceof MissingServletRequestParameterException ||
                e instanceof HttpMessageNotReadableException) {
            logger.info(sb.toString());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseDto.setMessage("Something is Blank, Or Error in Your Syntax");

        }  else {
            logger.error(sb.toString());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseDto.setMessage("Internet Server Error");
        }

        return responseDto;
    }
}