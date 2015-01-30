<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>

<!-- script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" /-->
<script src="https://apis.google.com/js/client:platform.js" async defer />

</head>

<body>

<div id="fb-root"></div>

<script>
window.fbAsyncInit = function() {
    FB.init({
      appId      : '1541250209463102',
      xfbml      : true,
      version    : 'v2.1'
    });
    
    FB.getLoginStatus(function(response) {
        //statusChangeCallback(response);
        console.log(response);
    });
};
  
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js"; //js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&appId=1541250209463102&version=v2.0";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

</script>

  
 <c:url value="/login" var="loginProcessingUrl"/>
 <sf:form action="${loginProcessingUrl}" method="post" modelAttribute="login">
    <fieldset>
        <legend>Please Login</legend>
        <c:if test="${param.error != null}">
            <div>
                Failed to login.
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                  Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                </c:if>
            </div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div>
                You have been logged out.
            </div>
        </c:if>
        <p>
            <label for="username">Username</label>
            <sf:input path="username" id="username" />
        </p>
        <p>
            <label for="password">Password</label>
            <sf:password path="password" id="password" />
        </p>
        <p>
            <label for="rm">Remember Me:</label>
            <input type="checkbox" name="rm" id="rm" />
        </p>
        <div>
            <button type="submit" class="btn">Log in</button>
        </div>
        
    </fieldset>
 </sf:form>

<div class="fb-login-button" data-max-rows="1" data-size="medium" data-show-faces="false" data-auto-logout-link="false">
</div>

<span id="signinButton">
  <span
    class="g-signin"
    data-callback="signinCallback"
    data-clientid="592203802548-f8p6d9d968rs9ip0cq279t6i73gbt2ip.apps.googleusercontent.com"
    data-cookiepolicy="single_host_origin"
    data-scope="profile email"
    data-redirecturi="postmessage"
    data-accesstype="offline"
    >
  </span>
</span>

 <c:url value="/googlelogin" var="googleLoginProcessingUrl"/>
 <sf:form id="googleform" action="${googleLoginProcessingUrl}" method="post">
    <input name="state" type="hidden" value="${_csrf.token}"/>
 </sf:form>

<script>
function signinCallback(authResult) {
    console.log(authResult);
// 	  if (authResult['status']['signed_in']) {
// 	    // Update the app to reflect a signed in user
// 	    // Hide the sign-in button now that the user is authorized, for example:
//         console.log(gapi.auth.getToken());
// 	    document.getElementById('signinButton').setAttribute('style', 'display: none');
// 	    var restRequest = gapi.client.request({
// 	    	  'path': '/plus/v1/people/me',
// 	    	});
// 	    	restRequest.then(function(resp) {
// 	    	  //console.log(resp.result);
// 	          console.log('displayName = ' + resp.result.displayName);
// 	          console.log('email = ' + resp.result.emails[0].value);
// 	    	}, function(reason) {
// 	    	  console.log('Error: ' + reason.result.error.message);
// 	    	});
// 	  } else {
// 	    // Update the app to reflect a signed out user
// 	    // Possible error values:
// 	    //   "user_signed_out" - User is signed-out
// 	    //   "access_denied" - User denied access to your app
// 	    //   "immediate_failed" - Could not automatically log in the user
// 	        console.log('Error: ' + authResult['error']);
// 	  }

    if (authResult['code']) {

        // Hide the sign-in button now that the user is authorized, for example:
        document.getElementById('signinButton').setAttribute('style', 'display: none');

        // Send the code to the server
//         $.ajax({
//           type: 'POST',
//           url: 'plus.php?storeToken',
//           contentType: 'application/octet-stream; charset=utf-8',
//           success: function(result) {
//             // Handle or verify the server response if necessary.

//             // Prints the list of people that the user has allowed the app to know
//             // to the console.
//             console.log(result);
//             if (result['profile'] && result['people']){
//               $('#results').html('Hello ' + result['profile']['displayName'] + '. You successfully made a server side call to people.get and people.list');
//             } else {
//               $('#results').html('Failed to make a server-side call. Check your configuration and console.');
//             }
//           },
//           processData: false,
//           data: authResult['code']
//         });
        var googleForm = document.forms['googleform'];
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'code';
        input.value = authResult['code'];
        googleForm.appendChild(input);
        googleForm.submit();
        
    } else if (authResult['error']) {
        // There was an error.
        // Possible error codes:
        //   "access_denied" - User denied access to your app
        //   "immediate_failed" - Could not automatially log in the user
        console.log('There was an error: ' + authResult['error']);
      }
}
</script>

</body>

</html>

