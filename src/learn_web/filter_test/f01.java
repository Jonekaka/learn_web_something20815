package learn_web.filter_test;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//只有浏览器直接访问index.jsp才会执行，内部转发不会执行，内部是个数组，如果需要多项条件拦截，使用{？，？}
//@WebFilter(value="/index.jsp",dispatcherTypes=DispatcherType.REQUEST)
//登录过滤器判断案例
@WebFilter(value="/a.jsp")
public class f01 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        获得请求资源的路径，geturl，geturi,一长一短。servlet是接口，使用内部的http类可以获得
        HttpServletRequest hRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse hresponse = (HttpServletResponse) servletResponse;
//        获得请求路径，判断路径是否有登录的相关申请资源，判断登录.注意资源要周全，否则会造成登录相关资源无法加载
        String requestURI = hRequest.getRequestURI();
        if(requestURI.contains("/login.jsp") || requestURI.contains("loginServlet")){
//            用户想登录,放行
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else{
//            用户想要访问其他资源,判断是否是合法用户
            Object user = hRequest.getSession().getAttribute("User");
            if(user!=null){
//                合法用户，登录了，放行
                filterChain.doFilter(servletRequest,servletResponse);
            }
            else{
//                不合法用户，让他去登录
                hRequest.setAttribute("login_msg","尚未登录，请登录");
                hRequest.getRequestDispatcher("/login.jsp").forward(hRequest,hresponse);
            }
        }
//        对request的请求消息进行增强
        System.out.println("filter执行");
//        放行
        filterChain.doFilter(servletRequest,servletResponse);
//        对response的响应消息进行增强
        System.out.println("filter回来了");
    }

    @Override
    public void destroy() {

    }
}
