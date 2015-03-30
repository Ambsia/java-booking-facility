package com.bookingsystem.helpers;

import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileNotFoundException;

/**
 * Created by Alex on 30/03/2015.
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

    public String[] getPropertyValues() throws IOException {

        return new String[] { properties.getProperty("server"), properties.getProperty("port"), properties.getProperty("user"), properties.getProperty("password"), properties.getProperty("database")};
    }
}
