package im.haugsdal;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import im.haugsdal.json.MyBean;
import im.haugsdal.xml.Banana;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {

    static FruitService fruitService;
    static FruitService jaxrsFruitService;

    @BeforeAll
    static void setup() {
        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJsonProvider());

        var jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        jaxWsServerFactoryBean.setServiceClass(FruitServiceImpl.class);
        jaxWsServerFactoryBean.setAddress("http://localhost:8888");
        var fruitServer = jaxWsServerFactoryBean.create();
        fruitServer.start();

        JAXRSServerFactoryBean jaxrsServerFactoryBean = new JAXRSServerFactoryBean();
        jaxrsServerFactoryBean.setServiceClass(FruitServiceImpl.class);
        jaxrsServerFactoryBean.setAddress("http://localhost:8889");
        jaxrsServerFactoryBean.setProviders(providers);
        var jaxrsFruitServer = jaxrsServerFactoryBean.create();
        jaxrsFruitServer.start();

        var jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress("http://localhost:8888");
        jaxWsProxyFactoryBean.setServiceClass(FruitService.class);
        fruitService = (FruitService) jaxWsProxyFactoryBean.create();

        var jaxrsClientFactoryBean = new JAXRSClientFactoryBean();
        jaxrsClientFactoryBean.setResourceClass(FruitService.class);
        jaxrsClientFactoryBean.setAddress("http://localhost:8889");

        jaxrsClientFactoryBean.setProviders(providers);
        jaxrsFruitService = (FruitService) jaxrsClientFactoryBean.create();

    }

    @org.junit.jupiter.api.Test
    void ping() {
        var pong = fruitService.ping("ping");
        System.out.println(pong);
        assertEquals("pong", pong);
    }

    @Test
    void banana() {
        var banana = new Banana();
        banana.setColor("yellow");
        banana = fruitService.banana(banana);
        assertEquals("green", banana.getColor());
    }

    @Test
    void extendableBean() {
        var bean = new MyBean();
        bean.setName("something");
        bean = jaxrsFruitService.bean(bean);
        assertEquals("something else", bean.getName());

    }


}