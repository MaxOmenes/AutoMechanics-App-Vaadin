package io.ukhin.automechanics.controller;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.ukhin.automechanics.controller.components.NavigationSideBar;
import io.ukhin.automechanics.model.Order;
import io.ukhin.automechanics.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.sql.Date;
import java.time.ZoneId;

@Route("orders")
public class OrderController extends HorizontalLayout {
    public OrderController(@Autowired OrdersService orderService) {
        var orderGrid = new GridCrud<>(Order.class, orderService);
        var grid = orderGrid.getGrid();

        TextField clientFilter = new TextField();
        clientFilter.setPlaceholder("Filter by client");
        clientFilter.setClearButtonVisible(true);

        TextField carFilter = new TextField();
        carFilter.setPlaceholder("Filter by car");
        carFilter.setClearButtonVisible(true);

        ComboBox<Order.Status> statusFilter = new ComboBox<>();
        statusFilter.setItems(Order.Status.values());
        statusFilter.setPlaceholder("Filter by status");
        statusFilter.setRenderer(createStatusRenderer());
        statusFilter.setClearButtonVisible(true);

        DatePicker dateFilter = new DatePicker();
        dateFilter.setPlaceholder("Filter by date");
        dateFilter.setClearButtonVisible(true);


        Button viewOrderButton = getButton(grid);
       grid.asSingleSelect().addValueChangeListener(e -> {
            viewOrderButton.setEnabled(orderGrid.getGrid().asSingleSelect().getValue() != null);
        });

        grid.removeAllColumns();
        grid.addColumn(Order::getDate).setHeader("Date").setSortable(true);
        grid.addColumn(order -> order.getCar().getClient().getName() + " "
                + order.getCar().getClient().getSurname()).setHeader("Client").setSortable(true);
        grid.addColumn(order -> order.getCar().getBrand() + " " + order.getCar().getModel()).setSortable(true).setHeader("Car");
        grid.addComponentColumn(order -> getStatusIcon(order.getStatus()))
                .setTooltipGenerator(order -> order.getStatus().name()).setHeader("Status");
        grid.addColumn(Order::getPrice).setHeader("Price").setSortable(true);

        orderGrid.getCrudLayout().addFilterComponents(dateFilter, clientFilter, carFilter, statusFilter);
        orderGrid.getCrudLayout().addToolbarComponent(viewOrderButton);

        var mainView = new VerticalLayout();

        mainView.add(new H1("Orders"));
        mainView.add(orderGrid);

        add(NavigationSideBar.createSideBar(), mainView);

        orderGrid.setOperations(() -> orderService.findByFilters(clientFilter.getValue(),
                        carFilter.getValue(),
                        statusFilter.getValue(),
                        dateFilter.getValue() == null ? null :
                        Date.from(dateFilter.getValue()
                                .atStartOfDay(ZoneId.systemDefault()).toInstant())),
                orderService::add,
                orderService::update,
                orderService::delete);


        clientFilter.addValueChangeListener(e -> orderGrid.refreshGrid());
        dateFilter.addValueChangeListener(e -> orderGrid.refreshGrid());
        carFilter.addValueChangeListener(e -> orderGrid.refreshGrid());
        statusFilter.addValueChangeListener(e -> orderGrid.refreshGrid());
    }

    private Button getButton(Grid<Order> grid) {
        Button viewOrderButton = new Button(new Icon(VaadinIcon.EYE));
        viewOrderButton.setEnabled(false);
        viewOrderButton.addClickListener(e -> {
            var selectedOrder = grid.asSingleSelect().getValue();
            if (selectedOrder != null) {
                getUI().ifPresent(ui -> ui.navigate("orders/" + selectedOrder.getId()));
            }
        });
        return viewOrderButton;
    }

    public Icon getStatusIcon(Order.Status status) {
        switch (status) {
            case ACCEPTED:
                var iconAccepted = VaadinIcon.EXCLAMATION_CIRCLE_O.create();
                iconAccepted.setColor("DarkGrey");
                return iconAccepted;
            case IN_PROGRESS:
                var iconInProgress = VaadinIcon.TIMER.create();
                iconInProgress.setColor("DarkOrange");
                return iconInProgress;
            case DONE:
                var iconDone = VaadinIcon.CHECK_CIRCLE_O.create();
                iconDone.setColor("DarkGreen");
                return iconDone;
            default:
                return new Icon("vaadin", "question");
        }
    }

    private Renderer<Order.Status> createStatusRenderer() {
        var style = """
                <div style="display: flex; align-items: center;">
                    <div style="flex-grow: 1; margin-right: var(--lumo-space-s);">${item.name}</div>
                    <vaadin-icon style="color: ${item.color};" icon="${item.icon}"></vaadin-icon>
                </div>
                """;
        return LitRenderer.<Order.Status>of(style)
                .withProperty("name", Enum::name)
                .withProperty("color", status -> {
                    switch (status) {
                        case ACCEPTED:
                            return "DarkGrey";
                        case IN_PROGRESS:
                            return "DarkOrange";
                        case DONE:
                            return "DarkGreen";
                        default:
                            return "black";
                    }
                })
                .withProperty("icon", status -> {
                    switch (status) {
                        case ACCEPTED:
                            return "vaadin:exclamation-circle-o";
                        case IN_PROGRESS:
                            return "vaadin:timer";
                        case DONE:
                            return "vaadin:check-circle-o";
                        default:
                            return "vaadin:question";
                    }
                });
    }
}