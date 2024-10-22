package io.ukhin.automechanics;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.AbstractTheme;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Theme(themeClass = Lumo.class, variant = Lumo.DARK)
public class AutomechanicsAppApplication implements AppShellConfigurator{
    public static void main(String[] args) {
        SpringApplication.run(AutomechanicsAppApplication.class, args);
    }
}
