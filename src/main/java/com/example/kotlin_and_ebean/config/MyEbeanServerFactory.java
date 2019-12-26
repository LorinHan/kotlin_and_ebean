package com.example.kotlin_and_ebean.config;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Simple Spring bean factory for creating the EbeanServer.
 */
@Component
public class MyEbeanServerFactory implements FactoryBean<EbeanServer> {

    public EbeanServer getObject() throws Exception {
        return createEbeanServer();
    }

    public Class<?> getObjectType() {
        return EbeanServer.class;
    }

    public boolean isSingleton() {
        return true;
    }

    /**
     * Create a EbeanServer instance.
     */
    private EbeanServer createEbeanServer() {

        ServerConfig config = new ServerConfig();
        config.setName("db");

        // load configuration from ebean.properties
        config.loadFromProperties();
        config.setDefaultServer(true);
        // other programmatic configuration

        return EbeanServerFactory.create(config);
    }
}
