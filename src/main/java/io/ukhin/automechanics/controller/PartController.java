package io.ukhin.automechanics.controller;


import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.ukhin.automechanics.controller.components.NavigationSideBar;
import io.ukhin.automechanics.model.Part;
import io.ukhin.automechanics.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("parts")
public class PartController extends HorizontalLayout {

    public PartController(@Autowired PartService partService) {
        var partGrid = new GridCrud<>(Part.class, partService);
        partGrid.getGrid().setColumns("name", "price");

        TextField nameFilter = new TextField();
        nameFilter.setPlaceholder("Filter by name");
        nameFilter.setClearButtonVisible(true);

        var priceLessMore = new Select<String>();
        priceLessMore.setItems("", ">", "<");
        priceLessMore.setPlaceholder("Filter by price");
        priceLessMore.setPrefixComponent(VaadinIcon.FUNCTION.create());

        NumberField priceFilter = new NumberField();
        priceFilter.setPlaceholder("Filter by price");
        priceFilter.setClearButtonVisible(true);

        partGrid.getCrudLayout().addFilterComponents(nameFilter,
                priceLessMore,
                priceFilter);

        var mainView = new VerticalLayout();

        mainView.add(new H1("Parts"));
        mainView.add(partGrid);

        add(NavigationSideBar.createSideBar(), mainView);

        partGrid.setOperations(() ->
                partService.findByFilters(nameFilter.getValue(),
                                        priceLessMore.getValue(),
                                        priceFilter.getValue()),
                partService::add,
                partService::update,
                partService::delete);

        nameFilter.addValueChangeListener(e -> partGrid.refreshGrid());
        priceLessMore.addValueChangeListener(e -> {
            if (priceFilter.getValue() != null){
                partGrid.refreshGrid();
            }
        });
        priceFilter.addValueChangeListener(e -> partGrid.refreshGrid());
    }
}
