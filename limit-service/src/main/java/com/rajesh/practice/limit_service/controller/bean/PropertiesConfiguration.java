package com.rajesh.practice.limit_service.controller.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("limit-service")
//@ConfigurationProperties in Spring Boot is an annotation used to bind external configuration properties
// (from sources like application.properties or application.yml) to a Java class. This helps manage application configuration
// in a clean and structured way by mapping related properties into a single object.
//Avoids scattering @Value annotations throughout the codebase.
public class PropertiesConfiguration {


    private int maximum;
    private int minimum;

    public PropertiesConfiguration() {
    }

    public PropertiesConfiguration(int maximum, int minimum) {
        this.maximum = maximum;
        this.minimum = minimum;
    }


    public int getMaximum() {
        return maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }
}
