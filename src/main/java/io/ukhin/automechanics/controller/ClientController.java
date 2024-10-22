package io.ukhin.automechanics.controller;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.ukhin.automechanics.controller.components.NavigationSideBar;
import io.ukhin.automechanics.model.Client;
import io.ukhin.automechanics.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;


@Route("clients")
public class ClientController extends HorizontalLayout {

    public ClientController(@Autowired ClientService clientService) {
        var clientGrid = new GridCrud<>(Client.class, clientService);
        clientGrid.getGrid().setColumns("name", "surname","patronymic","phone");

        TextField nameFilter = new TextField();
        nameFilter.setPlaceholder("Filter by name");
        nameFilter.setClearButtonVisible(true);

        TextField surnameFilter = new TextField();
        surnameFilter.setPlaceholder("Filter by surname");
        surnameFilter.setClearButtonVisible(true);

        TextField patronymicFilter = new TextField();
        patronymicFilter.setPlaceholder("Filter by patronymic");
        patronymicFilter.setClearButtonVisible(true);

        TextField phoneFilter = new TextField();
        phoneFilter.setPlaceholder("Filter by phone");
        phoneFilter.setClearButtonVisible(true);

        clientGrid.getCrudLayout().addFilterComponents(nameFilter,
                surnameFilter,
                patronymicFilter,
                phoneFilter);


        var mainView = new VerticalLayout();

        mainView.add(new H1("Clients"));
        mainView.add(clientGrid);


        add(NavigationSideBar.createSideBar(), mainView);

        clientGrid.setOperations(() ->
                clientService.findByFilters(nameFilter.getValue(),
                                                            surnameFilter.getValue(),
                                                            patronymicFilter.getValue(),
                                                            phoneFilter.getValue()),
                clientService::add,
                clientService::add,
                clientService::delete);


        nameFilter.addValueChangeListener(e -> clientGrid.refreshGrid());
        surnameFilter.addValueChangeListener(e -> clientGrid.refreshGrid());
        patronymicFilter.addValueChangeListener(e -> clientGrid.refreshGrid());
        phoneFilter.addValueChangeListener(e -> clientGrid.refreshGrid());
    }
}
