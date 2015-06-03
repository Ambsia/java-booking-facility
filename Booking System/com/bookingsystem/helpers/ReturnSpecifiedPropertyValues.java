package com.bookingsystem.helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Author: [Alex] on [$Date]
 */
public class ReturnSpecifiedPropertyValues {

    private final Properties properties;

    //pass properties through if we have more property files..
    public ReturnSpecifiedPropertyValues(String filePath) {
        String propertyFileName = filePath;

        properties = new Properties();
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + propertyFileName + "' not found.");
            }
        } catch (IOException e) {
            MessageBox.errorMessageBox("There was an issue whilst we were trying to open the property file!\n" + "Does this make any sense to you.." + e.toString() + "?");
        }
    }

    public String getDatabaseConnectionString() {
        return "jdbc:sqlserver://" + properties.getProperty("server") + ":" + properties.getProperty("port") + ";user=" + properties.getProperty("user") + ";password=" + properties.getProperty("password") + ";databaseName=" +  properties.getProperty("database") + "";
    }
    
    
}
