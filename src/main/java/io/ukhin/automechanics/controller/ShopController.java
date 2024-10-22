package io.ukhin.automechanics.controller;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import io.ukhin.automechanics.controller.components.NavigationSideBar;
import io.ukhin.automechanics.model.Shop;
import io.ukhin.automechanics.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


@Route("shops")
public class ShopController extends HorizontalLayout {
    public ShopController(@Autowired ShopService shopService) {
        var shopGrid = new GridCrud<>(Shop.class, shopService);
        shopGrid.getGrid().setColumns("name", "address", "phone");

        TextField nameFilter = new TextField();
        nameFilter.setPlaceholder("Filter by name");
        nameFilter.setClearButtonVisible(true);

        TextField addressFilter = new TextField();
        addressFilter.setPlaceholder("Filter by address");
        addressFilter.setClearButtonVisible(true);

        TextField phoneFilter = new TextField();
        phoneFilter.setPlaceholder("Filter by phone");
        phoneFilter.setClearButtonVisible(true);

        shopGrid.getCrudLayout().addFilterComponents(nameFilter,
                addressFilter,
                phoneFilter);

        var mainView = new VerticalLayout();

        mainView.add(new H1("Shops"));
        mainView.add(shopGrid);

        add(NavigationSideBar.createSideBar(), mainView);

        shopGrid.setOperations(() ->
                shopService.findByFilters(nameFilter.getValue(),
                                        addressFilter.getValue(),
                                        phoneFilter.getValue()),
                shopService::add,
                shopService::update,
                shopService::delete);

        nameFilter.addValueChangeListener(e -> shopGrid.refreshGrid());
        addressFilter.addValueChangeListener(e -> shopGrid.refreshGrid());
        phoneFilter.addValueChangeListener(e -> shopGrid.refreshGrid());
    }
}
