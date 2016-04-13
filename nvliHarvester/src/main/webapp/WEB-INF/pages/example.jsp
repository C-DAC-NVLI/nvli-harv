<%-- 
    Document   : example
    Created on : Apr 6, 2016, 2:32:53 PM
    Author     : vootla
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function update()
            {
        var baseUrlInput=document.getElementById("baseUrlInput").value;
        document.getElementById("identify").href="${pageContext.servletContext.contextPath}/identify?baseURL="+baseUrlInput;    
       document.getElementById("listSets").href="${pageContext.servletContext.contextPath}/listSets?baseURL="+baseUrlInput; 
       document.getElementById("listMetadataFormats").href="${pageContext.servletContext.contextPath}/listMetadataFormats?baseURL="+baseUrlInput;  
          alert("updated..");
            }
        </script>
        
    </head>
    <body>
        <h1> Harvester Demo!</h1>
        
       Defualt Base URL : <input type="text" id="baseUrlInput" value="http://dspace.library.iitb.ac.in/oai/request" style="width: 500px;"/>
       <input type="button" id="btn" value="update" onclick="update();">
        <br>
        <br>
        <a href="${pageContext.servletContext.contextPath}/identify?baseURL=http://dspace.library.iitb.ac.in/oai/request" id="identify">identify</a><br>
        <a href="${pageContext.servletContext.contextPath}/listSets?baseURL=http://dspace.library.iitb.ac.in/oai/request" id="listSets">listSets</a><br>
        <a href="${pageContext.servletContext.contextPath}/listMetadataFormats?baseURL=http://dspace.library.iitb.ac.in/oai/request" id="listMetadataFormats">listMetadataFormats</a><br>
        
    </body>
</html>
