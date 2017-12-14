function prepareUserDataAndSubmit(operationType){
	var jsonToSubmit='';
	if(operationType == 0 || operationType == 1) {
		var username = document.getElementById("usernameId").value;
		var password = document.getElementById("passwordId").value;
		jsonToSubmit='{"userName":"'+username+'","password":"'+password+'"}';
	} else if(operationType == 2) {
		var username = document.getElementById("usernameId").value;
		var score = document.getElementById("scoreId").value;
		jsonToSubmit='{"userName":"'+username+'","score":"'+score+'"}';
	} else if(operationType == 4) {
		var username = document.getElementById("usernameId").value;
		jsonToSubmit='{"userName":"'+username+'"}';
	} else if(operationType == 5) {
		var username = document.getElementById("usernameId").value;
		var online = document.getElementById("onlineId").checked;
		var onlineString = online ? "true" : "false";
		jsonToSubmit='{"userName":"'+username+'","online":"'+onlineString+'"}';
	} else if(operationType == 6) {
		var numberOfUsers = document.getElementById("numberOfUsersId").value;
	} else if(operationType == 7 || operationType == 8) {
		var username = document.getElementById("usernameId").value;
		var deviceId = document.getElementById("deviceId").value;
	}
	
	 
	var xmlhttp = new XMLHttpRequest();
	var url = "";
	switch (operationType) {
		case 0: url = CONTEXT+"/jersey/login";
		break;
		case 1: url = CONTEXT+"/jersey/insertUser";
		break;
		case 2: url = CONTEXT+"/jersey/updateScores";
		break;
		case 3: url = CONTEXT+"/jersey/scores";
		break;
		case 4: url = CONTEXT+"/jersey/usernameAvailability";
		break;
		case 5: url = CONTEXT+"/jersey/updateOnline";
		break;
		case 6: url = CONTEXT+"/jersey/onlineUsers?numberOfUsers="+numberOfUsers;
		break;
		case 7: url = CONTEXT+"/jersey/insertUserDevice?userName="+username+"&deviceId="+deviceId;
		break;
		case 8: url = CONTEXT+"/jersey/userDeviceAssociated?userName="+username+"&deviceId="+deviceId;
		break;
	}
	if(operationType == 0) {
		xmlhttp.open("POST", url, false);
		var usernamePwdBase64 = Base64.encode(username+":"+password);
		xmlhttp.setRequestHeader("authorization",usernamePwdBase64);		
	} else if(operationType == 1 || operationType == 7) {
		xmlhttp.open("PUT", url, false);
	} else if(operationType == 2 || operationType == 4 || operationType == 5) {
		xmlhttp.open("POST", url, false);
	} else if(operationType == 3 || operationType == 6 || operationType == 8) {
		xmlhttp.open("GET", url, false);
	} 
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.setRequestHeader("Access-Control-Allow-Origin","*");
	if(operationType == 0) {
		xmlhttp.onreadystatechange = function(){callableResponseLogin(xmlhttp);}
	} else if(operationType == 1) {		
		xmlhttp.onreadystatechange = function(){callableResponseInsertUser(xmlhttp);};
	} else if(operationType == 2) {		
		xmlhttp.onreadystatechange = function(){callableResponseUpdateUser(xmlhttp);};
	} else if(operationType == 3) {		
		xmlhttp.onreadystatechange = function(){callableResponseScores(xmlhttp);};
	} else if(operationType == 4) {		
		xmlhttp.onreadystatechange = function(){callableResponseUsernameAvailability(xmlhttp);};
	} else if(operationType == 5 || operationType == 6 || operationType == 7 || operationType == 8) {		
		xmlhttp.onreadystatechange = function(){callableResponseUpdateUserOnline(xmlhttp);};
	}
	
	var data = JSON.stringify(jsonToSubmit);
	xmlhttp.send(jsonToSubmit);
}

callableResponseLogin = function (xmlhttp) {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var json = JSON.parse(xmlhttp.responseText);
        console.log(json.username + ", " + json.password);
        var resultDiv = document.getElementById('loginResult');
        resultDiv.innerHTML = xmlhttp.responseText;
        resultDiv.style.display = 'block';
        var registrationTable = document.getElementById('loginTable');
        registrationTable.style.display = 'none';
    }	
}

callableResponseInsertUser = function (xmlhttp) {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var json = JSON.parse(xmlhttp.responseText);
        console.log(json.username + ", " + json.password);
        var resultDiv = document.getElementById('registrationResult');
        resultDiv.innerHTML = xmlhttp.responseText;
        resultDiv.style.display = 'block';
        var registrationTable = document.getElementById('registrationTable');
        registrationTable.style.display = 'none';
    }	
}

callableResponseUpdateUser = function (xmlhttp) {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var json = JSON.parse(xmlhttp.responseText);
        console.log(json.username + ", " + json.score);
        var resultDiv = document.getElementById('updateResult');
        resultDiv.innerHTML = xmlhttp.responseText;
        resultDiv.style.display = 'block';
        var registrationTable = document.getElementById('updateTable');
        registrationTable.style.display = 'none';
    }	
}

callableResponseScores = function (xmlhttp) {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var json = JSON.parse(xmlhttp.responseText);
        var resultDiv = document.getElementById('scoresResult');
        resultDiv.innerHTML = xmlhttp.responseText;
        resultDiv.style.display = 'block';
    }	
}

callableResponseUsernameAvailability = function (xmlhttp) {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var json = JSON.parse(xmlhttp.responseText);
        console.log(json.username + ", " + json.password);
        var resultDiv = document.getElementById('loginResult');
        resultDiv.innerHTML = xmlhttp.responseText;
        resultDiv.style.display = 'block';
        var registrationTable = document.getElementById('loginTable');
        registrationTable.style.display = 'none';
    }	
}

callableResponseUpdateUserOnline = function (xmlhttp) {
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var json = JSON.parse(xmlhttp.responseText);
        var resultDiv = document.getElementById('updateResult');
        resultDiv.innerHTML = xmlhttp.responseText;
        resultDiv.style.display = 'block';
        var registrationTable = document.getElementById('updateTable');
        registrationTable.style.display = 'none';
    }	
}

