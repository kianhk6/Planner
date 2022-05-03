# Planner
Created a Spring Boot server which helps students plan their courses. 
Specifically, it shows to students when a course has been offered in the past which may help predict when it will be offered in the future.
This is the server which will use a REST interface to support a web-based UI (web UI provided by the instructor).
The server will read a locally stored CSV input file containing data about course offerings at SFU. It
will then process the data to organize it into a well structured model. The server’s controllers will
provide access to this data via a REST API. 
