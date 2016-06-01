package com.resin.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.resin.db.ServiceController;


@Path("/")
public class CustomerInsert {
	@POST
	@Path("/CustomerInsert")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response customerInsert(InputStream inputStream) throws ClassNotFoundException,IOException,SQLException{
		
		ServiceController controller = new ServiceController(inputStream);
		return controller.getResponse();		
	}//function
	
	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream input) {
		String result = "CustomerInsert Successfully started..";
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}// function
}// class