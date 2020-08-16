package learn_web.filter_test;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

//敏感词汇过滤器
@WebFilter("/*")
public class SensitiveWordFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest hreq= (HttpServletRequest) req;
        hreq.setCharacterEncoding("utf-8");
//        创建代理对象，增强getParameter方法
        ServletRequest proxyReq= (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//                如果调用的是getRpatameter方法，当然获取数值的方法不止一种，实际使用需要考虑全部过滤，罗列
                if(method.getName().equals("getParameter")){
//                    增强返回值
//                    获取返回值
                   String value= (String) method.invoke(req,args);
                   if(value!=null){
                       for(String s:list){
                           if(value.contains(s)){
                              value= value.replaceAll(s,"**");
                           }
                       }
                   }
                   return  value;
                }
//                这里的意思是如果不是getParameter方法则会原样返回，否则其他的方法就会报错
                return method.invoke(req,args);
            }
        });
//        放行,新的servletrequest方法
        chain.doFilter(proxyReq, resp);
    }
//    敏感词汇列表、
  private List<String> list= new ArrayList<String>();
//对于敏感词汇在初始化中加载，方便资源的合理调用
    public void init(FilterConfig config) throws ServletException {

//        获取文件真实路径
        ServletContext servletContext = config.getServletContext();
        String realPath = servletContext.getRealPath("/WEB-INF/classes/a.txt");
//        读取文件本地流默认gbk格式，文本默认gbk所以不错，但是如果以后文件格式为utf8则会乱码
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(realPath));
            //        将文件内容放到list中
            String line=null;
            while((line=bufferedReader.readLine())!=null){
                list.add(line);
            }
            bufferedReader.close();
            System.out.println(list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
