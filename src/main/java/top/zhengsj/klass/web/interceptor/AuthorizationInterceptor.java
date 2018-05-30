package top.zhengsj.klass.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.zhengsj.klass.enums.UserRoleEnum;
import top.zhengsj.klass.exception.NoAuthorizationException;
import top.zhengsj.klass.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws NoAuthorizationException {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }

        Integer role = (Integer) request.getAttribute(JWTUtil.USER_ROLE);

        if (role == null || !role.equals(UserRoleEnum.ADMINISTRATION.getValue())) {
            throw new NoAuthorizationException("No Authorization.");
        }

        return true;
    }
}
