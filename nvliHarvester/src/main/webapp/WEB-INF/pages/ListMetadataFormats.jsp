<%-- 
    Document   : listsets
    Created on : Apr 13, 2016, 11:33:06 AM
    Author     : vootla
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>MetaDataFormats</h1>
   
        <table>
            <tr><th>metadataPrefix</th><th>schema</th><th>metadataNamespace</th></tr>
        <c:forEach items="${metaDataFormats}" var="metadata">
            <tr><td>${metadata.metadataPrefix}</td><td>${metadata.schema}</td><td>${metadata.metadataNamespace}</td>
            </tr>
            
            
        </c:forEach>
        </table>
    </body>
</html>
