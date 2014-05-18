package com.deege.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.deege.domain.general.ServerVersion;
import com.deege.utilities.DateHelper;

/**
 * <p>Resource for returning application version information.</p>
 *
 */
@Path("/version")
public class VersionResource {
	private static final Logger logger = Logger.getLogger(VersionResource.class.getName());
	
	@Context UriInfo uri;
	
	/**
	 * <p>Returns {@link ServerVersion} for this application.</p>
	 * <p>Example: GET http://localhost/version</p>
	 * <p>Mime-type: application/json</p>
	 * 
	 * @return
	 */
	@GET
	@Produces({ServerVersion.SINGLE_MIMETYPE, "application/json"})
	public Response getServerVersionAsJson() {
		Response response = null;

		if (logger.isLoggable(Level.FINEST)) {
			logger.finest("Getting Protocols Server version information.");
		}
		ServerVersion versionInfo = getServerVersion();

		// Marshal the object to xml and return it 
		response = Response.status(HttpURLConnection.HTTP_OK).entity(versionInfo).location(uri.getRequestUri()).build();
		return response;

	}

	/**
	 * <p>Returns the current version of this application.  This information is set
	 * by Maven at compile time.</p>
	 * 
	 * @return the ServerVersion for this application 
	 */
	private ServerVersion getServerVersion() {
		ServerVersion versionInfo = new ServerVersion();

		// Load in the properties file created by Maven to set build information
		InputStream inputStream = this.getClass().getClassLoader()  
			.getResourceAsStream("protocols.properties");  
		Properties properties = new Properties();   
		boolean readProperties = false;
		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}  
			readProperties = true;
		} 
		
		//get the value of the property  
		versionInfo.setBuildVersion(properties.getProperty("application.version"));  
		
		DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmm");
		Date date;
		Date serverDate = new Date();
		try {
			if (readProperties) {
				date = df.parse(properties.getProperty("application.builddate"));
				versionInfo.setBuildDate(DateHelper.asXMLGregorianCalendar(date));
			}			
			versionInfo.setServerTime(DateHelper.asXMLGregorianCalendar(serverDate));
		} catch (ParseException e) {
			logger.severe(e.getMessage());
		} 
		 
		versionInfo.setBuiltBy(properties.getProperty("application.builtby")); 
		versionInfo.setLocation(uri.getRequestUri().toString());
		versionInfo.setDescription("Protocols application");
		return versionInfo;
	}
}
