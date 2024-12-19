<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>

	<title>Add a Workshop Subscription</title>
    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


</head>
<body>
    
	<h1>Profile STUDENT -${studentSession.name}</h1>
		
	<hr>
	| Home | <a href="/student/workshopsubscription/all?page=0&size=10"> My Subscriptions</a> | My Lectures | About |  Welcome,<%=request.getRemoteUser()%> ! | <a href="/prelogout"> Logout</a>
	 
	<hr> 
	<br>



<h1>Adding a Workshop Subscription</h1>
     
       
        <br>
        <br>
    
  <div>      	
    <form:form class="mt-4" method="POST" action="/student/workshopsubscription/add/process" modelAttribute="workshopsubscriptionForm">
		
		<div>

		<spring:url value="/student/workshopsubscription/selectworkshop" var="selectworkshopUrl" />

		<form:hidden path="workshop.id" value="${workshop.id}"/>
			    	
			<div class="form-row">			 
							
				<div class="form-group col-sm-2 ${status.error ? 'has-error' : ''}">
					<label for="workshop.id">Workshop</label>			
							
					<form:input path="workshop.title" type="text"  id="workshop.title" 
					placeholder="Click here to select a Workshop" onclick="location.href='${selectworkshopUrl}'"  readonly="true" />
					<form:errors path="workshop.id" class="control-label" />		
			  
	  			</div>
			
			</div>				
		
			<br>

		<div>
	      
	        <input type="submit" value="Subscribe" class="btn">
		
		</div>

	</div>			
		</form:form>

  </div>

  
  
</body>
</html>