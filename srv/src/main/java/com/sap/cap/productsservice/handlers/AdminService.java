package com.sap.cap.productsservice.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

//import cds.gen.adminservice.Products;

import com.sap.cds.services.EventContext;
import com.sap.cds.services.cds.CdsCreateEventContext;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CdsService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;

@Component
@ServiceName("AdminService")
public class AdminService implements EventHandler {

    private Map<Object, Map<String, Object>> products = new HashMap<>();

   

    @On(event = CdsService.EVENT_CREATE, entity = "AdminService.Products")
    public void onCreate(CdsCreateEventContext context) {
        System.out.println("I am inside onCreate");
        context.getCqn().entries().forEach(e -> products.put(e.get("ID"), e));

        context.setResult(context.getCqn().entries());
    }

    @On(event = CdsService.EVENT_READ, entity = "AdminService.Products")
    public void onRead(CdsReadEventContext context) {
        System.out.println("I am inside onRead");
        // context.setResult(products.values());
    }

    // READ event can be omitted from the annotation
    // it is inferred from the CdsReadEventContext argument

    @On(entity = "AdminService.Products")
    public void onReadWithoutEvent(CdsReadEventContext context) {
        System.out.println("I am inside onReadWithoutEvent");
        // context.setResult(products.values());
    }

    // WRONG! This will throw an error during startup
    // the CdsReadEventContext does not match to the CREATE event
    // in this case only the generic EventContext argument can be used
    // you can use the EventContext.as(Class) method as alternative

    // @On(event = {CdsService.EVENT_READ,CdsService.EVENT_CREATE} ,entity =
    // "AdminService.Products")
    // public void onReadAndCreate(CdsReadEventContext context) {
    // System.out.println("I am inside onReadAndCreate");
    // context.setResult(products.values());
    // }

    // shows how an event handler can be registered on multiple events
    @Before(event = { "CREATE", "READ" }, entity = "AdminService.Products")
    public void beforeCreateAndReadBooks(EventContext context) {
        System.out.println("An event handler for multiple events");
    }

    // shows how the default service on class-level can be overriden
    // shows how to register on any entity
    @On(service = "UserService", event = "READ")
    public void readAnyEntityInCatalogService(EventContext context) {
            System.out.println("Using CatalogService for any entity");
    }

}