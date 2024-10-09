package io.ukhin.automechanics.controller;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.ukhin.automechanics.controller.components.FilterHeader;
import io.ukhin.automechanics.controller.components.NavigationSideBar;
import io.ukhin.automechanics.model.Client;
import io.ukhin.automechanics.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route
public class ClientController extends HorizontalLayout {

    public ClientController(@Autowired ClientService clientService) {
        var clientGrid = new GridCrud<>(Client.class, clientService);
        clientGrid.getGrid().setColumns("name", "surname","patronymic","phone");
        var grid = clientGrid.getGrid();

        var mainView = new VerticalLayout();

        mainView.add(new H1("Clients"));
        mainView.add(clientGrid);

        add(NavigationSideBar.createSideBar(), mainView);
    }
}
