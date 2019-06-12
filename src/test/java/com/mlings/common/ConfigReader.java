/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlings.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Mahesh Lingsugur
 */
public class ConfigReader {

    public static final Logger LOGGER = Logger.getLogger(ConfigReader.class.getName());

    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/WebTest.properties";
    private static ConfigReader instance;
    private static Map<String, String> confMap;

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                instance = new ConfigReader();
                confMap = loadConfMap();
            }
        }
        return instance;
    }

    public String getValue(String key) {
        String value = confMap.get(key);
        if (value == null) {
            LOGGER.log(Level.FATAL, "Get value returned null for the key: "+ key);
            value = "";
        }
        LOGGER.log(Level.INFO, String.format("Conf get value: [%s] for key: [%s]",key, value));
        return value;
    }

    public String putValue(String key, String value) {
        LOGGER.log(Level.INFO, String.format("Conf put value: [%s] for key: [%s]", key, value));
        return confMap.put(key, value);
    }

    private static Map<String, String> loadConfMap() {
        FileInputStream fileInputStream;
        Properties conf = new Properties();
        confMap = new HashMap<>();

        try {
            fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            conf.load(fileInputStream);
        } catch (IOException ex) {
            LOGGER.log(Level.FATAL, "Abort due to configuration file not found: " + CONFIG_FILE_PATH, ex);
            System.exit(1);
        }

        conf.stringPropertyNames().forEach((String key) -> {
            String val = System.getProperty(key);
            if (val == null || val.trim().isEmpty()) {
                val = conf.getProperty(key);
            }
            confMap.put(key, val);
        });

        return confMap;
    }
}
