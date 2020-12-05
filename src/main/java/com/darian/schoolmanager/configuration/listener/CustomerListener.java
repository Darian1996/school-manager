package com.darian.schoolmanager.configuration.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/29  1:08
 */
public class CustomerListener implements SpringApplicationRunListener, Ordered {

    private Logger LOGGER = LoggerFactory.getLogger(CustomerListener.class);

    private SpringApplication application;

    private String[] args;

    public CustomerListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {
        LOGGER.info("CustomerListener#starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        LOGGER.info("CustomerListener#environmentPrepared");

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        LOGGER.info("CustomerListener#contextPrepared");

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        LOGGER.info("CustomerListener#contextLoaded");

    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        LOGGER.info("CustomerListener#started");
        String serverPort = context.getEnvironment().getProperty("server.port");
        LOGGER.info("http://localhost:" + serverPort + "/start/index.html");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        LOGGER.info("CustomerListener#running");

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        LOGGER.error("CustomerListener#failed", exception);

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
