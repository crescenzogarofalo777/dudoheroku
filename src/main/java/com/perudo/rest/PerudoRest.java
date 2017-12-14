package com.perudo.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.perudo.dto.UserDeviceDto;
import com.perudo.dto.UserDto;
import com.perudo.service.PerudoUserDeviceService;
import com.perudo.service.PerudoUserService;


@Path("/")
public class PerudoRest {

	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String main() {
		StringBuilder res = new StringBuilder();
		res.append("<main>Welcome to perudo main page</main>");
		return res.toString();
	}

	@Path("/scores")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String scores() {
		StringBuilder res = new StringBuilder("");
		PerudoUserService perudoUserService = new PerudoUserService();
		res.append("{\"scores\":[");
		try {
			List<UserDto> scores = perudoUserService.scores();
			if(scores != null && !scores.isEmpty()) {
				for(UserDto dto: scores) {
					res.append("{\"userName\":\"").append(dto.getUserName())
						.append("\",\"score\":\"").append(dto.getScore())
						.append("\"},");
				}
				res.delete(res.length()-1, res.length());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.append("]}");
		return res.toString();
	}

	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(UserDto userDtoWeb) throws JSONException, Exception {
		StringBuilder res = new StringBuilder("");
		PerudoUserService perudoUserService = new PerudoUserService();
		UserDto userDto = perudoUserService.login(userDtoWeb.getUserName(), userDtoWeb.getPassword());
		res.append("{\"userlogin\":\"");
		if(userDto != null) {
			res.append("ok\",");		
			res.append("\"username\":\"").append(userDto.getUserName()).append("\",");
			res.append("\"score\":\"").append(userDto.getScore()).append("\"");
			res.append("}");
		} else {
			res.append("ko\"}");
		}
		return res.toString();
	}

	@Path("/usernameAvailability")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String verifyUsernameAvailability(UserDto userDtoWeb) throws JSONException, Exception {
		StringBuilder res = new StringBuilder("");
		PerudoUserService perudoUserService = new PerudoUserService();
		boolean usernameAvailable = perudoUserService.verifyUsernameAvailability(userDtoWeb.getUserName());
		res.append("{\"usernameAvailable\":\"");
		res.append(usernameAvailable).append("\"}");
		return res.toString();
	}
	
	@Path("/insertUser")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String insertUser(UserDto userDto) {
		StringBuilder res = new StringBuilder();
		res.append("{\"userInserted\":{");
		PerudoUserService perudoUserService = new PerudoUserService();
		UserDto userDtoResult = perudoUserService.insertUser(userDto);
		if(userDtoResult != null) {
			res.append("\"id\":\"").append(userDtoResult.getId()).append("\",");
			res.append("\"username\":\"").append(userDtoResult.getUserName()).append("\",");
			res.append("\"password\":\"").append("*********").append("\",");
			res.append("\"score\":\"").append(userDtoResult.getScore()).append("\"");
		}
		res.append("}}");
		return res.toString();
	}
	
	@Path("/updateScores")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateScores(UserDto userDto) {
		StringBuilder res = new StringBuilder();
		res.append("{\"userScoreUpdated\":{");
		PerudoUserService perudoUserService = new PerudoUserService();
		UserDto userDtoResult = perudoUserService.updateUser(userDto);
		if(userDtoResult != null) {
			res.append("\"username\":\"").append(userDtoResult.getUserName()).append("\",");
			res.append("\"score\":\"").append(userDtoResult.getScore()).append("\"");
		}
		res.append("}}");
		return res.toString();
	}

	@Path("/updateOnline")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateOnline(UserDto userDto) {
		StringBuilder res = new StringBuilder();
		res.append("{\"userOnlineUpdated\":{");
		PerudoUserService perudoUserService = new PerudoUserService();
		UserDto userDtoResult = perudoUserService.updateUser(userDto);
		if(userDtoResult != null) {
			res.append("\"username\":\"").append(userDtoResult.getUserName()).append("\",");
			res.append("\"online\":\"").append(userDtoResult.isOnline()).append("\"");
		}
		res.append("}}");
		return res.toString();
	}

	@Path("/onlineUsers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String scores(@QueryParam("numberOfUsers") int numberOfPlayers) {
		StringBuilder res = new StringBuilder("");
		PerudoUserService perudoUserService = new PerudoUserService();
		res.append("{\"onlineUsers\":[");
		try {
			List<UserDto> scores = perudoUserService.retrieveOnlineUsers(numberOfPlayers);
			if(scores != null && !scores.isEmpty()) {
				for(UserDto dto: scores) {
					res.append("{\"userName\":\"").append(dto.getUserName())
					.append("\",\"id\":\"").append(dto.getId())
						.append("\",\"score\":\"").append(dto.getScore())
						.append("\"},");
				}
				res.delete(res.length()-1, res.length());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.append("]}");
		return res.toString();
	}

	@Path("/insertUserDevice")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String insertUserDevice(@QueryParam("userName") String userName,@QueryParam("deviceId") String deviceId) {
		StringBuilder res = new StringBuilder();
		res.append("{\"deviceBound\":");
		PerudoUserService perudoUserService = new PerudoUserService();
		try {
			UserDto userDto = perudoUserService.retrieveUser(userName);
			if(userDto != null) {
				String deviceIdEncoded = new String(Base64.encodeBase64String(deviceId.getBytes()));
				UserDeviceDto userDeviceDto = new UserDeviceDto();
				userDeviceDto.setUserId(userDto.getId());
				userDeviceDto.getDeviceIds().add(deviceIdEncoded);
				PerudoUserDeviceService perudoUserDeviceService = new PerudoUserDeviceService();
				UserDeviceDto userDeviceDtoResult = perudoUserDeviceService.insertUserDevice(userDeviceDto);
				if(userDeviceDtoResult != null && !userDeviceDtoResult.getDeviceIds().isEmpty()) {
					res.append("\":OK\"");
				} else {
					res.append("\":KO\"");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.append("\":KO\"");
		}
		res.append("}");
		return res.toString();
	}

	@Path("/userDeviceAssociated")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String userDeviceAssociated(@QueryParam("userName") String userName,@QueryParam("deviceId") String deviceId) {
		StringBuilder res = new StringBuilder("");
		PerudoUserDeviceService perudoUserDeviceService = new PerudoUserDeviceService();
		res.append("{\"deviceBound\":");
		try {
			String deviceIdEncoded = new String(Base64.encodeBase64String(deviceId.getBytes()));
			
			UserDeviceDto userDeviceDtoResult = perudoUserDeviceService.verifyDeviceUserAssociated(userName, deviceIdEncoded);
			if(userDeviceDtoResult != null) {
				if(!userDeviceDtoResult.getDeviceIds().isEmpty()) {
					res.append("\":OK\"");
				} else {
					res.append("\":KO\"");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.append("\":KO\"");
		}
		res.append("}");
		return res.toString();
	}

}
