package com.rest.restApi.controller;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.hibernate.hql.internal.ast.ErrorReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.restApi.dao.EmployeeRepository;
import com.rest.restApi.exception.ErrorConstants;
import com.rest.restApi.exception.ErrorResponse;
import com.rest.restApi.model.Employee;

@Component
@Path("/employee/api")
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;
	ErrorResponse errorResponse=new ErrorResponse();
	@GET
	@Produces("application/json")
	@Path("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@GET
	@Produces("application/json")
	@Path("/emp/{empid}")
	public Response getEmployeeId(@PathParam("empid") int id) {
	boolean isPrecent=employeeRepository.findById(id).isPresent();
		if(isPrecent) {
			Employee employee=employeeRepository.findById(id).get();
			return Response.status(Response.Status.ACCEPTED).entity(employee).build();
		}
		else {
			errorResponse.setStatus(ErrorConstants.FAILURE);
			errorResponse.setErrorDescription("Employee with employee id: " + id + " not found");
			errorResponse.setErrorCode(ErrorConstants.FAILURE_CODE);
			return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
		}
	}
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/createEmployee")
	public Response createEmployee(Employee employee) {
		Employee emp=employeeRepository.save(employee);
		return Response.status(Response.Status.CREATED).entity(emp).build();
	}
	
	
	public void updateEmployee(Employee updatedEmpDetails ,@PathParam("empid") int id) {
		boolean isPrecent=employeeRepository.findById(id).isPresent();
		if(isPrecent) {
			Employee employee=employeeRepository.findById(id).get();
			employee .setEmpName(updatedEmpDetails.getEmpName());
			employee.setEmpOrganization(updatedEmpDetails.getEmpOrganization());
			employeeRepository.saveAndFlush(employee);
		}
	}
	@DELETE
	@Produces("application/json")
	@Path("/deleteEmp/{empId}")
	public Response deleteEmployee(@PathParam("empId") int empId) {
		boolean isPresent = employeeRepository.findById(empId).isPresent();
		if (isPresent) {
			employeeRepository.deleteById(empId);
			errorResponse.setErrorCode(ErrorConstants.SUCCESS_CODE);
			errorResponse.setErrorDescription("Employee with employee id: " + empId + " deleted successfully");
			errorResponse.setStatus(ErrorConstants.SUCCESS);
			return Response.status(Response.Status.ACCEPTED).entity(errorResponse).build();
		} else {
			errorResponse.setErrorCode(ErrorConstants.FAILURE_CODE);
			errorResponse.setErrorDescription("Employee with employee id: " + empId + " not found");
			errorResponse.setStatus(ErrorConstants.FAILURE);
			return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
		}

	}
}
