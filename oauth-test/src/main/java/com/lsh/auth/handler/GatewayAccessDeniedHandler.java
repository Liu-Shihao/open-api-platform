package com.lsh.auth.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：LiuShihao
 * @date ：Created in 2021/8/19 1:40 下午
 * @desc ：实现无权限处理类 Sorry, you do not have permission to access！
 */
@Component
public class GatewayAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html");
        String body = "<html><body><div style='color: gold; width:800px; margin:auto; text-align:center; font-size:24px' >对不起，您没有权限访问！</div></body></html>";
        httpServletResponse.getWriter().println(body);
        httpServletResponse.getWriter().flush();

    }
}
