package ru.trick.springmangabot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableScheduling
@Data
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement
public class BotConfig {
    private final Environment environment;
    private final Environment env;

    @Autowired
    public BotConfig(Environment environment, Environment env) {
        this.environment = environment;
        this.env = env;
    }

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();


        dataSource.setDriverClassName(env.getRequiredProperty("hibernate.driver_class"));
        dataSource.setUrl(env.getRequiredProperty("hibernate.connection.url"));
        dataSource.setUsername(env.getRequiredProperty("hibernate.connection.username"));
        dataSource.setPassword(env.getRequiredProperty("hibernate.connection.password"));

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));

        return properties;
    }

}
