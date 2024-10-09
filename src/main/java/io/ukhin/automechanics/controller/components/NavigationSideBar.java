package io.ukhin.automechanics.controller.components;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import io.ukhin.automechanics.controller.ClientController;

public class NavigationSideBar {
    public static SideNav createSideBar() {
        SideNav sideNav = new SideNav();

        sideNav.addItem(
                new SideNavItem("Clients", ClientController.class, VaadinIcon.USERS.create()),
                new SideNavItem("Cars", "cars", VaadinIcon.CAR.create()),
                new SideNavItem("Parts", "part", VaadinIcon.COG.create()),
                new SideNavItem("Shops", "shops", VaadinIcon.SHOP.create()),
                new SideNavItem("Delivery Notes", "delivery-notes", VaadinIcon.TRUCK.create())
        );

        return sideNav;
    }
}
