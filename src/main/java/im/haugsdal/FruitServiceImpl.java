package im.haugsdal;

import javax.ws.rs.Path;

import im.haugsdal.json.MyBean;
import im.haugsdal.xml.Banana;

public class FruitServiceImpl implements FruitService{



    @Override
    public String ping(String ping) {
        return "pong";
    }

    @Override
    public Banana banana(Banana banana) {
        var color = banana.getColor();
        System.out.println(color);
        banana.setColor("green");
        return banana;
    }

    @Override
    public MyBean bean(MyBean bean) {
        var name = bean.getName();
        bean.setName("something else");
        return bean;
    }
}
