<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head></head>
   <body>
   	<h2>Custom Logout Page</h2>
   	
	<br>
	  Are you sure you want to leave?<br>   	
      <a href="<c:url value="/logout" />">Logout</a>
   </body>
</html>