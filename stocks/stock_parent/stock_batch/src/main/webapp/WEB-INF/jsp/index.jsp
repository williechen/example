<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDateTime" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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