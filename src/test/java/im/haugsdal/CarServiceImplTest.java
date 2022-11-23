package im.haugsdal;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import im.haugsdal.json.Car;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CarServiceImplTest {

    static CarService carService;
    static CarService jaxrsCarService;

    @BeforeAll
    static void setup() {
        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJsonProvider());

        var jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        jaxWsServerFactoryBean.setServiceClass(CarServiceImpl.class);
        jaxWsServerFactoryBean.setAddress("http://localhost:8888");
        var fruitServer = jaxWsServerFactoryBean.create();
        fruitServer.start();

        JAXRSServerFactoryBean jaxrsServerFactoryBean = new JAXRSServerFactoryBean();
        jaxrsServerFactoryBean.setServiceClass(CarServiceImpl.class);
        jaxrsServerFactoryBean.setAddress("http://localhost:8889");
        jaxrsServerFactoryBean.setProviders(providers);
        var jaxrsFruitServer = jaxrsServerFactoryBean.create();
        jaxrsFruitServer.start();

        var jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress("http://localhost:8888");
        jaxWsProxyFactoryBean.setServiceClass(CarService.class);
        carService = (CarService) jaxWsProxyFactoryBean.create();

        var jaxrsClientFactoryBean = new JAXRSClientFactoryBean();
        jaxrsClientFactoryBean.setResourceClass(CarService.class);
        jaxrsClientFactoryBean.setAddress("http://localhost:8889");

        jaxrsClientFactoryBean.setProviders(providers);
        jaxrsCarService = (CarService) jaxrsClientFactoryBean.create();

    }

    @org.junit.jupiter.api.Test
    void ping() {
        var pong = carService.ping("ping");
        System.out.println(pong);
        assertEquals("pong", pong);
    }

    @Test
    void xmlBanana() {
        var banana = new im.haugsdal.xml.Car();
        banana.setColor("yellow");
        banana = carService.xmlBanana(banana);
        assertEquals("green", banana.getColor());
    }

    @Test
    void jsonBanana() {
        var bean = new Car();
        bean.setColor("yellow");
        bean = jaxrsCarService.jsonBanana(bean);
        assertEquals("green", bean.getColor());

    }


}