<%-- 
    Document   : identify
    Created on : Apr 12, 2016, 3:02:31 PM
    Author     : vootla
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Identify</title>
    </head>
    <body>
        <h1>Identify</h1>
      
        <h4>Repository Name <i style="color: red;">${repo.repoName}</i></h4>
         <h4>Repository Base Url<i style="color: red;"> ${repo.repoBaseUrl}</i></h4>
          <h4>Repository Protocol Version <i style="color: red;">${repo.repoProtocolVersion}</i></h4>
           <h4>Repository Granularity Date<i style="color: red;"> ${repo.repoGranularityDate}</i></h4>
            <h4>Repository Earliest Timestamp <i style="color: red;">${repo.repoEarliestTimestamp}</i></h4>
             <h4>Repository Deletion Mode <i style="color: red;">${repo.repoDeletionMode}</i></h4>
        
        
        
    </body>
</html>
