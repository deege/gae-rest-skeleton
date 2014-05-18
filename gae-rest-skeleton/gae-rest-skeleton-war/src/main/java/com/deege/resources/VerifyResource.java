/**
 * 
 */
package com.deege.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * <p>Simple REST endpoint to verify the server is available and running.</p>
 * <p>Example: http://localhost/hello/{param}</p>
 */
@Path("/hello")
public class VerifyResource {
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg)  {

		String output = "REST API is available.";

		return Response.status(200).entity(output).build();

	}
}