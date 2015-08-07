This springBoot/Jersey RESTFUL service serves Rss from http://mediaweb.musicradio.com/RSSFeed.xml?Channel=9172  as set of resources.<br />
I have used  framework

### Endpoints
serviceHealthCheck = http://localhost:8080/rssfeed/health <br />
getAllResources =  http://localhost:8080/rssfeed <br />
getSingleResourcesById = http://localhost:8080/rssfeed/7589c8d3-9c07-4858-a334-765bd043431b <br />
getResourcesAlternate = http://localhost:8080/rssfeed/7589c8d3-9c07-4858-a334-765bd043431b/alternate <br />
getMostRecentResource = http://localhost:8080/rssfeed/latest <br />
for convenience, you can use google chrome postman plugin with [postman.json](postman.json)<br />
### Running and Deploying the service
this service can be run by either calling run on /src/main/java/Boot main class or you can call "Java -jar" on a prepakaged [jar file](podcast-service-rest-0.0.1-SNAPSHOT.jar)<br />
### Running unit tests
simply call mvn test from the root directory
### Dependencies
No external dependencies is needed to run this service



