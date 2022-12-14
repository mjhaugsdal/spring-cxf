package im.haugsdal;

import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.core.MediaType;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import im.haugsdal.json.Car;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.rs.security.jose.jaxrs.JwsClientResponseFilter;
import org.apache.cxf.rs.security.jose.jaxrs.JwsContainerRequestFilter;
import org.apache.cxf.rs.security.jose.jaxrs.JwsWriterInterceptor;
import org.apache.cxf.rs.security.jose.jws.JwsCompactConsumer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CarServiceImplTest {

    static CarService carService;
    static CarService jaxrsCarService;

    @BeforeAll
    static void setup() {
        System.setProperty("file.encoding", "UTF-8");

        var jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJsonProvider());



        jaxWsServerFactoryBean.setServiceClass(CarServiceImpl.class);
        jaxWsServerFactoryBean.setAddress("http://localhost:8881");
        var fruitServer = jaxWsServerFactoryBean.create();
        fruitServer.start();

        JAXRSServerFactoryBean jaxrsServerFactoryBean = new JAXRSServerFactoryBean();
        jaxrsServerFactoryBean.setServiceClass(CarServiceImpl.class);
        jaxrsServerFactoryBean.setAddress("http://localhost:8889");

        jaxrsServerFactoryBean.getProperties(true).put(
            "rs.security.signature.in.properties",
            "signature-in.properties");
        // enable the tracing of JWS headers
        jaxrsServerFactoryBean.getProperties(true).put("jose.debug", true);

        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        List<Feature> features = new ArrayList<>();
        features.add(loggingFeature);
        jaxrsServerFactoryBean.setFeatures(features);

        providers.add(new JwsContainerRequestFilter());

        jaxrsServerFactoryBean.setProviders(providers);
        var jaxrsFruitServer = jaxrsServerFactoryBean.create();
        jaxrsFruitServer.start();

        var jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress("http://localhost:8881");
        jaxWsProxyFactoryBean.setServiceClass(CarService.class);
        carService = (CarService) jaxWsProxyFactoryBean.create();

        var jaxrsClientFactoryBean = new JAXRSClientFactoryBean();
        jaxrsClientFactoryBean.setResourceClass(CarService.class);
        jaxrsClientFactoryBean.setAddress("http://localhost:8889");

        jaxrsClientFactoryBean.setProviders(providers);
        RSAClientSigner(jaxrsClientFactoryBean);

        var myJaxrsCarService = jaxrsClientFactoryBean.create();
        //myJaxrsCarService.encoding("UTF-8");
        jaxrsCarService = (CarService) myJaxrsCarService;
        //var config = WebClient.getConfig(jaxrsCarService);
       // var test = WebClient.fromClient(jaxrsCarService);
        //config.getHttpConduit().

    }

    private static void RSAServerSigner() {

    }

    private static void RSAClientSigner(JAXRSClientFactoryBean bean) {
        JwsWriterInterceptor jwsWriter = new JwsWriterInterceptor();
        List<Object> providers = new LinkedList<>();
        providers.add(jwsWriter);
        providers.add(new JacksonJsonProvider());
        bean.setProviders(providers);

        bean.getProperties(true).put(
            "rs.security.signature.out.properties",
            "signature-out.properties");
        // enable the tracing of JWS headers
        bean.getProperties(true).put("jose.debug", true);
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