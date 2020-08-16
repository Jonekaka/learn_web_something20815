package learn_web.filter_test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//一个事件，servletcontext被创建
//发生的地方tomcat
//监听代码
public class ServletListenerTest implements ServletContextListener {
    @Override
    /**
    *@Description: 在服务器启动后servletContext自动创建，监听serviletContext对象创建
     * 通常作用是为服务器配置资源文件
    *@Param: [servletContextEvent]
    *@Return: void
    */
    public void contextInitialized(ServletContextEvent servletContextEvent) {

//        获得对象servletContext
        ServletContext servletContext = servletContextEvent.getServletContext();
        //        加载资源文件
        String contextconfiglocation = servletContext.getInitParameter("contextconfiglocation");
//        获得资源文件真实路径
        String realPath = servletContext.getRealPath(contextconfiglocation);
//        加载文件到内存
        try (FileInputStream fileInputStream = new FileInputStream(realPath)) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("servletContext方法被创建");
    }

    @Override
    /**
    *@Description: servletcontext对象被销毁，服务器关闭后该方法被调用
    *@Param: [servletContextEvent]
    *@Return: void
    */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("servletcontext方法被销毁");
    }
}
