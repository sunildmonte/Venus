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

<script src="https://apis.google.com/js/client:platform.js" async defer>
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
    data-scope="profile email">
  </span>
</span>

<script>
function signinCallback(authResult) {
    console.log(authResult);
	  if (authResult['status']['signed_in']) {
	    // Update the app to reflect a signed in user
	    // Hide the sign-in button now that the user is authorized, for example:
        console.log(gapi.auth.getToken());
	    document.getElementById('signinButton').setAttribute('style', 'display: none');
	    var restRequest = gapi.client.request({
	    	  'path': '/plus/v1/people/me',
	    	});
	    	restRequest.then(function(resp) {
	    	  //console.log(resp.result);
	          console.log('displayName = ' + resp.result.displayName);
	          console.log('email = ' + resp.result.emails[0].value);
	    	}, function(reason) {
	    	  console.log('Error: ' + reason.result.error.message);
	    	});
	  } else {
	    // Update the app to reflect a signed out user
	    // Possible error values:
	    //   "user_signed_out" - User is signed-out
	    //   "access_denied" - User denied access to your app
	    //   "immediate_failed" - Could not automatically log in the user
	        console.log('Error: ' + authResult['error']);
	  }
	}
</script>

</body>

</html>

