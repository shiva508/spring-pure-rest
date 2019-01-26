package com.rest.restApi.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.rest.restApi.controller.EmployeeController;
@Component
@ApplicationPath("/jersey")
public class EmployeeConfig extends ResourceConfig {
	
	public EmployeeConfig() {
		register(EmployeeController.class);
	}

}
