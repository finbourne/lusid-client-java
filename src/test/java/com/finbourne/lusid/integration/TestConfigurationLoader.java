package com.finbourne.lusid.integration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TestConfigurationLoader {

    /**
     * Loads the API configuration from a resource in the classpath
     *
     * @param apiConfig name of the resource
     * @return API configuration file
     * @throws IOException
     */
    public File loadConfiguration(String apiConfig) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(apiConfig);

        if (url == null) {
            throw new IOException("cannot find " + apiConfig + " in classpath");
        }

        File configFile = new File(url.getFile());
        return new File(configFile.toURI());
    }

}
