package io.ukhin.automechanics.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import io.ukhin.automechanics.controller.OrderController;
import io.ukhin.automechanics.model.Order;
import io.ukhin.automechanics.model.Part;
import io.ukhin.automechanics.service.OrdersService;
import io.ukhin.automechanics.service.PartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.UUID;

@Route("orders/:orderId")
@Slf4j
public class OrderView extends HorizontalLayout implements BeforeEnterObserver {
    @Autowired OrdersService ordersService;
    @Autowired PartService partService;

    Order order;

    public OrderView(){
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
       var orderId = event.getRouteParameters().get("orderId").orElse(null);

       if (orderId == null) {
           log.error("Error: order not found");
           getUI().ifPresent(ui -> ui.navigate(OrderController.class));
           return;
       }
       try {
           order = ordersService.findById(UUID.fromString(orderId));
       }catch (Exception e) {
           log.error("Error while getting order", e);
           getUI().ifPresent(ui -> ui.navigate(OrderController.class));
           return;
       }

       var infoLayout = new VerticalLayout();
       var partsLayout = new VerticalLayout();

       var image = new Image("images/default_car.png", "Car image");
       image.setHeight("400px");
       image.setWidth("400px");
       infoLayout.add(image);

       Text SPACE = new Text("   ");

       infoLayout.add(new Span(VaadinIcon.DATE_INPUT.create(), SPACE, new Text("Date: " + order.getDate())));
       infoLayout.add(new Span(VaadinIcon.CHECK.create(), SPACE, new Text("Status: " + order.getStatus())));
       infoLayout.add(new Span(VaadinIcon.NOTEBOOK.create(), SPACE, new Text("Description: " + order.getDescription())));
       infoLayout.add(new Span(VaadinIcon.DOLLAR.create(), SPACE, new Text("Price: " + order.getPrice())));
       infoLayout.add(new Span(VaadinIcon.CAR.create(), SPACE, new Text("Car: " + order.getCar().getBrand() + " " + order.getCar().getModel())));
       infoLayout.add(new Span(VaadinIcon.USER.create(), SPACE, new Text("Client: " + order.getCar().getClient().getName() + " " + order.getCar().getClient().getSurname())));

       var crud = new GridCrud<>(Part.class, partService);
       crud.getGrid().setColumns("name", "price");
       crud.getGrid().addColumn(part -> part.getDeliveryNote().getShop().getName())
               .setSortable(true).setHeader("Shop");

       crud.setOperations(() -> partService.findByOrder(order.getId()),
               part -> {
                   part.setOrder(order);
                   return part;
               },
                partService::update,
               partService::delete);
       partsLayout.add(crud);

         add(infoLayout, partsLayout);
    }
}
