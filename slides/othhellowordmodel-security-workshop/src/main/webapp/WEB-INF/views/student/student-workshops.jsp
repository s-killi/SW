<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<title>Home Student</title>

</head>
<body>

	<hr>
	<h1>Profile STUDENT -${studentSession.name}</h1>
		
	<hr>
	| Home | <a href="/student/workshopsubscription/all?page=0&size=10"> My Subscriptions</a> | My Lectures | About |  Welcome,<%=request.getRemoteUser()%> ! | <a href="/prelogout"> Logout</a>
	 
	<hr> 
	<br>
	
	<table border="2" width="70%" cellpadding="2">

		<tr>
		            <th>My Workshop Subscriptions</th>
		            
		            
		</tr>
		
		
		<tr>
		            <th>Id Workshop</th>
		            <th>Title of the Workshop</th>
		            <th>Date</th>
		            <th>Professor</th>
		            <th>Academic Event</th>
		            <th>Action</th>
     
          	            
		            
		</tr>
	        	
		 <c:forEach var="workshopsubscription" items="${workshopSubscriptions}">
		            <tr>
		                <td>${workshopsubscription.workshop.id}</td>
		                <td>${workshopsubscription.workshop.title}</td>
		                <td>${workshopsubscription.workshop.date}</td>
		                <td>${workshopsubscription.workshop.professor.name}</td>
		                <td>${workshopsubscription.workshop.academicevent.title}</td>
		                <td><a href="/student/workshopsubscription/delete/${workshopsubscription.id}">Cancel</a></td>
		            </tr>
		</c:forEach>	
		
		

	</table>
	
	
	
	
	<a href="/student/workshopsubscription/all?page=${previouspage}&size=${size}"> Previous</a> <a href="/student/workshopsubscription/all?page=${page}&size=${size}"> Next</a>	
        
	<br>	
	<a href="/student/workshopsubscription/add">Add Workshop Subscription</a> 





</body>


</html>