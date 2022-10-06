package nus.iss.team1.project1.annotation.token;


import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import nus.iss.team1.project1.models.User;
import nus.iss.team1.project1.utils.JwtUtil;
import nus.iss.team1.project1.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    public static final String USER_KEY = "USER_ID";
    public static final String USER_INFO = "USER_INFO";

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Token annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Token.class);
        }else{
            return true;
        }
        //没有声明需要权限,或者声明不验证权限
        if(annotation == null || !annotation.validate()){
            return true;
        }
        //从header中获取token
        String token = request.getHeader("token");
        if(token == null){
            log.info("缺少token，拒绝访问");
            handleFalseResponse(response, "lack of token");
            return false;
        }
        //查询token信息
        User user = jwtUtil.validateToken(token);
        if(user == null) {
            handleFalseResponse(response, "invalid token");
            return false;
        };

        //token校验通过，将用户信息放在request中，供需要用user信息的接口里从token取数据
        request.setAttribute(USER_KEY, user.getId());
        request.setAttribute(USER_INFO, user);
        return true;
    }

    private void handleFalseResponse(HttpServletResponse response, String msg) throws Exception {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(ResponseResult.error(401, msg, null).toJsonString());
        response.getWriter().flush();
    }
}
