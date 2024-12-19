<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="pt-br">
  <head>    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">    
    
  </head>  
<body>

<hr>
	<h1>Profile STUDENT ${studentSession.name}</h1>
		
	<hr>
	| Home | <a href="/student/workshopsubscription/all?page=0&size=10"> My Subscriptions</a> | My Lectures | About |  Welcome,<%=request.getRemoteUser()%> ! | <a href="/prelogout"> Logout</a>
	 
	<hr> 
	<br>


	<div class="container">

		<div  class="spacesup"></div>		
		
			<h4>Search the Workshop:</h4>
		
		
			<spring:url value="/student/workshopsubscription/selectworkshop2" var="selectUrl" />
				
			<form:form  class="form-row"  modelAttribute="workshopForm" action="${selectUrl}">	
		
			<div  class="form-group col-sm-1"></div>
			Title:
			<spring:bind path="title">
				  <div class="form-group col-sm-8  ${status.error ? 'has-error' : ''}">		
						<form:input path="title" type="text" class="form-control" id="title" required="required"/>                                
						<form:errors path="title" class="control-label" />								
				  </div>
			</spring:bind>	
						
			<div class="form-group col-sm-3 d-flex justify-content-center justify-content-md-start">
				<button type="submit" class="btn btn-outline-secondary"><i class="fas fa-search"></i> <span class="esconder"> Search</span></button>
			</div>	
		</form:form>	
	
	<c:if test="${not empty workshops}">

		<div>
			<h5>Results of the Search:</h5>		
		</div>

		<div class="table-responsive-md mt-3" >		
		<table class="table table-striped table-hover display" >
			<thead>
				<tr>
					<th class="text-center">Title</th>
					<th class="text-center">Date</th>
					<th class="text-center">Select</th>
				</tr>
			</thead>

			<c:forEach var="workshop" items="${workshops}">
			    <tr>
					<td>${workshop.title}</td>
					
					<td>${workshop.date}</td>
								
					<td  class="text-center">
					  <spring:url value="/student/workshopsubscription/selectworkshop/${workshop.id}" var="workshopUrl" />
					  <button class="btn btn-sm" data-toggle="tooltip" data-placement="botton" title="Selecione este workshop" onclick="location.href='${workshopUrl}'"><i class="fas fa-check-circle"></i></button>				  
	                </td>
			    </tr>
			</c:forEach>
		</table>
		</div>
		</c:if>	
	
	
</div>	
	
</body>
</html>