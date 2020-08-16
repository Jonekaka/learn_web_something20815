package learn_web.filter_test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
*@Description: 使用代理完成操作
*@Param:
*@Return:
*/
public class proxy_test {
    public static void main(String[] args) {
//        创建真实对象
        Lenovo lenovo = new Lenovo();
//        动态代理增强lenovo对象，并不具备lenovo的功能
        /**
        *@Description:
        *@Param: [args]
         * 1.类加载器，真实对象.getClass().getClassLoader()
         * 2.接口数组：真实对象.getClass().getInterface()
         * 3.处理器：new InvocationHandler()
        *@Return: void
        */
        SaleComputer proxy_lenovo = (SaleComputer) Proxy.newProxyInstance(lenovo.getClass().getClassLoader(), lenovo.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            /**
             *@Description: 代理逻辑编写的方法，代理对象调用的所有方法都会出发该方法执行
             *@Param: [proxy代理对象, method代理对象调用的方法，被封装为对象, args代理对象调用的方法时，实际传递的参数]
             *@Return: java.lang.Object
             */
//            怎么去增强方法：方法返回值，方法的参数，方法的名称
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                System.out.println(method.getName());
//                System.out.println(args[0]);
//                判断是否是sale方法
                if (method.getName().equals("sale")) {
//                    增强参数
                    double arg = (double) args[0];
                    arg = arg * 0.85;
 //                  增强方法体
                    System.out.println("专车接客户");
//                    使用真实对象调用方法，方法与传参
                    String invoke = (String) method.invoke(lenovo, arg);
                    System.out.println("免费送货");
//                    增强返回值
                    return invoke + "—送鼠标垫";
                } else {
//                    不被增强，原本调用
                    Object invoke = method.invoke(lenovo, args);
                    return invoke;
                }
            }
        });
//        调用方法
        String sale = proxy_lenovo.sale(8000);
        System.out.println(sale);
        proxy_lenovo.show();
    }
}
