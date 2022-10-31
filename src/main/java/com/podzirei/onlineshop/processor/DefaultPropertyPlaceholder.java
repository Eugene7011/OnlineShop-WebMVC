package com.podzirei.onlineshop.processor;

import com.study.ioc.entity.BeanDefinition;
import com.study.ioc.processor.BeanFactoryPostProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DefaultPropertyPlaceholder implements BeanFactoryPostProcessor {

    private static final String PATH = "application.properties";
    private static final String DB_URL = "db.db_url";
    private static final String JDBC_DRIVER = "db.jdbc_driver";
    private static final String USER = "db.user";
    private static final String PASSWORD = "db.pass";
    private final Map<String, String> propertiesMap = new HashMap<>();

    private final Properties properties = new Properties();

    @Override
    public void postProcessorBeanFactory(Map<String, BeanDefinition> beanDefinitions) {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitions.entrySet()) {
            BeanDefinition beanDefinition = entry.getValue();
            if (!beanDefinition.getClassName().equals("org.postgresql.ds.PGSimpleDataSource")) {
                continue;
            }

            Map<String, String> valueDependencies = beanDefinition.getValueDependencies();
            Map<String, String> property = new HashMap<>();

            loadFileWithDatabaseProperties();
            for (Map.Entry<String, String> entryValue : valueDependencies.entrySet()) {
                String valueProperty = entryValue.getValue();
                String nameProperty = entryValue.getKey();
                if (valueProperty.startsWith(("${"))) {
                    String propertyValue = findProperty(nameProperty);
                    property.put(nameProperty, propertyValue);
                }
            }
            beanDefinition.setValueDependencies(property);
        }
    }

    private String findProperty(String nameProperty) {
        String value = switch (nameProperty) {

            case "driverClassName" -> propertiesMap.get(JDBC_DRIVER);
            case "url" -> propertiesMap.get(DB_URL);
            case "user" -> propertiesMap.get(USER);
            case "password" -> propertiesMap.get(PASSWORD);
            case "databaseName" -> propertiesMap.get("databaseName");
            case "portNumber" -> propertiesMap.get("portNumber");

            default -> null;
        };
        return value;
    }

    private void loadFileWithDatabaseProperties() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream resourceStream = loader.getResourceAsStream(PATH)) {
            properties.load(resourceStream);
        } catch (IOException exception) {
            throw new RuntimeException("Can not load properties");
        }
        fillPropertiesMap(properties);
    }

    private void fillPropertiesMap(Properties properties) {
        String[] parts = properties.getProperty(DB_URL).split("/");
        propertiesMap.put("databaseName", parts[3]);
        String[] portNumbers = parts[2].split(":");

        propertiesMap.put("portNumber", portNumbers[1]);
        propertiesMap.put(JDBC_DRIVER, properties.getProperty(JDBC_DRIVER));
        propertiesMap.put(DB_URL, properties.getProperty(DB_URL));
        propertiesMap.put(USER, properties.getProperty(USER));
        propertiesMap.put(PASSWORD, properties.getProperty(PASSWORD));
    }
}
