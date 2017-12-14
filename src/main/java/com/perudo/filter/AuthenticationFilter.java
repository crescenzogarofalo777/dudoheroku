package com.perudo.filter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class AuthenticationFilter implements ContainerRequestFilter {

	@Override
	public ContainerRequest filter(ContainerRequest containerRequest) {

		String method = containerRequest.getMethod();
		
		String path = containerRequest.getPath();
		
		if("login".equals(path)) {			
			//Get the authentification passed in HTTP headers parameters
			String auth = containerRequest.getHeaderValue("authorization");
			
			//If the user does not have the right (does not provide any HTTP Basic Auth)
			if(auth == null){
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			
			String[] authParams = decodeBase64(auth);
			
			if(authParams == null || authParams.length != 2) {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			
		}
		return containerRequest;
	}


	private String[] decodeBase64(String auth) {
		byte[] decodedBytes = DatatypeConverter.parseBase64Binary(auth);
		 
        //If the decode fails in any case
        if(decodedBytes == null || decodedBytes.length == 0){
            return null;
        }
 
        //Now we can convert the byte[] into a splitted array :
        //  - the first one is login,
        //  - the second one password
        return new String(decodedBytes).split(":", 2);
	}

}
