<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDateTime" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Simple JSP Application</title>
    </head>
    <body>
        <h1>Hello world!</h1>
        <h2>Current time is <%= LocalDateTime.now() %></h2>
    </body>
</html>