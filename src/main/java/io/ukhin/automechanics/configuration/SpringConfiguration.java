package io.ukhin.automechanics.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Configuration
public class SpringConfiguration {
    @Bean
    public SimpleDateFormat getDataFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:postgresql://localhost:6000/automechanics",
                "postgres", "postgres");
    }
}
