package com.ticbus.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author AnhLH
 */
@Component
@PropertySource("classpath:custom.properties")
public class PropertiesConfig {

  @Autowired
  private Environment environment;

  public String getConfigValue(String configKey) {
    return environment.getProperty(configKey);
  }
}
