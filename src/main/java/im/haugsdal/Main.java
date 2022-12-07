package im.haugsdal;

import java.util.Arrays;
import java.util.List;

import org.apache.cxf.Bus;
import javax.xml.ws.Endpoint;

import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    @Autowired
    private Bus bus;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        //Crap code
        for(int i = 0; i<= 10; i++) {
            if(true) {
                System.out.println("");
            }
        }

        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }

    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setAddress("http://localhost:8888/rest");
        endpoint.setServiceBean(new CarServiceImpl());
        endpoint.setFeatures(List.of(new Swagger2Feature()));
        return endpoint.create();
    }


    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new CarServiceImpl());
        endpoint.setAddress("http://localhost:8080");
        endpoint.publish("/Hello");
        return endpoint;
    }
}