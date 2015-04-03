package com.bookingsystem.helpers;

import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileNotFoundException;

/**
 * Author: [Alex] on [$Date]
 */
public class ReturnSpecifiedPropertyValues {

    private Properties properties;

    //pass properties through if we have more property files..
    public ReturnSpecifiedPropertyValues() throws IOException {
        String propertyFileName = "config.properties";

        properties = new Properties();

        InputStream inputStream = getClass().getResourceAsStream("config.properties");
        System.out.println(inputStream);
        if (inputStream != null) { properties.load(inputStream); } else {
            throw new FileNotFoundException("Property file '" + propertyFileName + "' not found.");
        }
    }

    public String getDatabaseConnectionString() throws IOException {
        return "jdbc:sqlserver://" + properties.getProperty("server") + ":" + properties.getProperty("port") + ";user=" + properties.getProperty("user") + ";password=" + properties.getProperty("password") + ";databaseName=" +  properties.getProperty("database") + "";
    }
}
