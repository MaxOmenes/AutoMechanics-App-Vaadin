package io.ukhin.automechanics.controller;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.ukhin.automechanics.controller.components.NavigationSideBar;
import io.ukhin.automechanics.model.Car;
import io.ukhin.automechanics.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("cars")
public class CarController extends HorizontalLayout {
    public CarController(@Autowired CarService carService) {
        var carGrid = new GridCrud<>(Car.class, carService);
        carGrid.getGrid().setColumns("brand", "model", "vin", "number");

        TextField brandFilter = new TextField();
        brandFilter.setPlaceholder("Filter by brand");
        brandFilter.setClearButtonVisible(true);

        TextField modelFilter = new TextField();
        modelFilter.setPlaceholder("Filter by model");
        modelFilter.setClearButtonVisible(true);

        TextField vinFilter = new TextField();
        vinFilter.setPlaceholder("Filter by vin");
        vinFilter.setClearButtonVisible(true);

        TextField numberFilter = new TextField();
        numberFilter.setPlaceholder("Filter by number");
        numberFilter.setClearButtonVisible(true);

        carGrid.getCrudLayout().addFilterComponents(brandFilter,
                modelFilter,
                vinFilter,
                numberFilter);

        var mainView = new VerticalLayout();

        mainView.add(new H1("Cars"));
        mainView.add(carGrid);

        add(NavigationSideBar.createSideBar(), mainView);

        carGrid.setOperations(() ->
                carService.findByFilters(brandFilter.getValue(),
                                        modelFilter.getValue(),
                                        vinFilter.getValue(),
                                        numberFilter.getValue()),
                carService::add,
                carService::update,
                carService::delete);

        brandFilter.addValueChangeListener(e -> carGrid.refreshGrid());
        modelFilter.addValueChangeListener(e -> carGrid.refreshGrid());
        vinFilter.addValueChangeListener(e -> carGrid.refreshGrid());
        numberFilter.addValueChangeListener(e -> carGrid.refreshGrid());
    }
}
