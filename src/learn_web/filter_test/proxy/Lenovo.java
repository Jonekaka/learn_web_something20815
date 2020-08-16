package learn_web.filter_test.proxy;
/**
*@Description: 真实类
*@Param:
*@Return:
*/
public class Lenovo implements  SaleComputer{
    @Override
    public String sale(double money) {
        System.out.println("电脑"+money+"元");
        return "买了一台电脑";
    }

    @Override
    public void show() {
        System.out.println("电脑展示");
    }
}
