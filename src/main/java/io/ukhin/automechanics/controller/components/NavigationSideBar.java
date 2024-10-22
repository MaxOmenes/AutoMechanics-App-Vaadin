package io.ukhin.automechanics.controller.components;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import io.ukhin.automechanics.controller.*;
import io.ukhin.automechanics.model.DeliveryNote;
import io.ukhin.automechanics.view.MainView;

public class NavigationSideBar {
    public static SideNav createSideBar() {
        SideNav sideNav = new SideNav();

        sideNav.addItem(
                new SideNavItem("Home", MainView.class, VaadinIcon.HOME.create()),
                new SideNavItem("Clients", ClientController.class, VaadinIcon.USERS.create()),
                new SideNavItem("Cars", CarController.class, VaadinIcon.CAR.create()),
                new SideNavItem("Parts", PartController.class, VaadinIcon.COG.create()),
                new SideNavItem("Shops", ShopController.class, VaadinIcon.SHOP.create()),
                new SideNavItem("Orders", OrderController.class, VaadinIcon.CART.create())
        );

        return sideNav;
    }
}
