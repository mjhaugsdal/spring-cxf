package im.haugsdal;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import im.haugsdal.json.MyBean;
import im.haugsdal.xml.Banana;

@WebService(serviceName = "FruitService")
@Path("/fruit")
public interface FruitService {


    /*@Path("/ping")
    @GET
    String ping();*/

    @WebMethod
    @WebResult(name = "ping", targetNamespace = "http://im.haugsdal.net/")
    String ping(@WebParam String ping);

    @WebMethod
    @WebResult(name = "banana")
    Banana banana(@WebParam Banana banana);

    @Path("/extBean")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    MyBean bean(MyBean extendableBean);

}
