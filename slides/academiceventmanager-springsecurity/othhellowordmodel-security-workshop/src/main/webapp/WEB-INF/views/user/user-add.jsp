<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Sign Up - CodeJava</title>
 	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body>
    <div class="container text-center">
        <div>
            <h1>User Registration - Sign Up</h1>
        </div>
           
        	<form:form action="/register/process" method="POST" modelAttribute="userForm" style="max-width: 600px; margin: 0 auto;">
        <div class="m-3">
            
            <div class="form-group row">
                <label class="col-4 col-form-label">Login: </label>
                <div class="col-8">
                    <form:input path="login" type="text"  id="login" class="form-control" required ></form:input>
                    <form:errors path="login"></form:errors>
                </div>
            </div>
            
            <div class="form-group row">
                <label class="col-4 col-form-label">E-mail: </label>
                <div class="col-8">
                                        
                    <form:input path="email" type="text"  id="email" class="form-control" required ></form:input>
                    <form:errors path="email"></form:errors>
                </div>
            </div>
             
            <div class="form-group row">
                <label class="col-4 col-form-label">Password: </label>
                <div class="col-8">
                    <form:input path="password" type="text"  id="password" class="form-control" required ></form:input>
                    <form:errors path="password"></form:errors>
                </div>
            </div>
             
            <div class="form-group row">
                <label class="col-4 col-form-label">Name: </label>
                <div class="col-8">
                    <form:input path="name" type="text"  id="name" class="form-control" required ></form:input>
                    <form:errors path="name"></form:errors>
                </div>
            </div>
             
            
            <div class="form-group row">
                <label class="col-4 col-form-label">Name: </label>
                <div class="col-8">
	             <form:select path="profile" class="form-control">  
	        		<form:option value="1" label="Student" />  
	        		<form:option value="2" label="Professor"/>
	        	</form:select> 
	             
	             <form:errors path="profile"></form:errors>
                </div>
            </div>
             
            <div>
                <button type="submit" class="btn btn-primary">Sign Up</button>
            </div>
        </div>
        </form:form>
    </div>
</body>
</html>