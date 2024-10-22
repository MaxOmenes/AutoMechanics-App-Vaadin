package io.ukhin.automechanics.view;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.ChartVariant;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import io.ukhin.automechanics.controller.components.NavigationSideBar;
import io.ukhin.automechanics.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends HorizontalLayout {
    public MainView(@Autowired OrdersService ordersService) {
        var chart = new Chart(ChartType.PIE);
        var configuration = chart.getConfiguration();

        var dataSeries = new DataSeries();
        var stats = ordersService.getStatistics();

        for (var entry : stats.entrySet()) {
            dataSeries.add(new DataSeriesItem(entry.getKey().name(), entry.getValue()));
        }

        configuration.setSeries(dataSeries);
        chart.addThemeVariants(ChartVariant.LUMO_MONOTONE);
        chart.setSizeFull();
        add(NavigationSideBar.createSideBar(), chart);
    }
}
