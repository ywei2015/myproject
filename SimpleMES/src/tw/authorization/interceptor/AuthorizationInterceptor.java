package tw.authorization.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tw.authorization.annotation.Authorization;
import tw.sysbase.pub.Constants;
import tw.sysbase.servie.sec.RoleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，判断此次请求是否有权限
 * @see com.scienjus.authorization.annotation.Authorization
 * @author YWW
 * @date 2018/1/22
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private RoleService roleService;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String operator = (String) request.getSession().getAttribute(Constants.SESSION_KEY_OPERATOR);
       // RoleVo rv = roleMgrController.getRoleByOperator(operator);
        //验证是否拥有管理员权限
        if (operator.equals("1001")) {
            return true;
        }
        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
