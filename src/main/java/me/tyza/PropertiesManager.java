package me.tyza;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesManager {
    HashMap<String, String> propertiesMap;

    PropertiesManager() {
        propertiesMap = new HashMap<String, String>();
    }

    public void createProperties(File propertiesFile) {
        Properties properties = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(propertiesFile);

            properties.setProperty("api","key");
            properties.setProperty("string_botyta_on","**Botyta ON**");
            properties.setProperty("string_botyta_off","**Botyta OFF**");


            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void load(File propertiesFile) {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(propertiesFile);
        } catch (FileNotFoundException filex) {
            createProperties(propertiesFile);
            System.out.println("Properties file is missing, a new properties file has been created.");
            filex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    properties.load(inputStream);
                    inputStream.close();

                    for(String key : properties.stringPropertyNames()) {
                        String value = properties.getProperty(key);
                        this.propertiesMap.put(key,value);
                    }

                } catch (IOException ioex) {
                    ioex.printStackTrace();
                }
            }
        }
    }

    public String getProperty(String name) {
        return this.propertiesMap.get(name);
    }

}
