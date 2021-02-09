package org.neustupov.springboot2bestpractics;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Springboot2bestpracticsApplication {

  private static Logger log = LoggerFactory.getLogger(Springboot2bestpracticsApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(Springboot2bestpracticsApplication.class, args);
  }

  @Value("${myapp.server-ip}")
  String serverIp;

  @Autowired
  MyAppProperties props;

  @Bean
  CommandLineRunner values(){
    return args -> {
      log.info(" > The server IP is: " + serverIp);
      log.info(" > App Name: " + props.getName());
      log.info(" > App Info: " + props.getDescription());
    };
  }

  @Data
  @Component
  @ConfigurationProperties(prefix = "myapp")
  public static class MyAppProperties {
    private String name;
    private String description;
    private String serverIp;
  }
}
