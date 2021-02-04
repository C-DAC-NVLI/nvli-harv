# NVLI-HARV
## Setup
1. Create a folder named **harvester-data** where files and other data will be downloaded during harvesting of repositories and give its path in `WEB-INF/conf/settings.properties`
2. Update the webservice url according to NVLI application url in `WEB-INF/conf/settings.properties`
3. Create another folder **app-logs** where application logs will be saved and give its path in `WEB-INF/conf/logfile.properties`
4. Import the enclosed database sql file.There are two master tables `har_repo_type` and `har_repo_status`.
5. Now update the `WEB-INF/conf/db.properties` and `WEB-INF/conf/DBMigration.properties` with the updated database server `username` and `password`
6. Create a `war` of he application and deploy it on web application server.
