# parking-ticket-system
Its a simple simulation of a parking ticket system. There are 3 projects (REST layer, Business layer and DAO layer). Each projects are maven
projects. 
Steps:
1. Clone the repository
2. Run mvn clean install on all the 3 projects in following order:
       i. parking-ticket-dao
       ii. parking-ticket-biz
       iii. parking-ticket-rest
3. The WAR generated from the rest project could be deployed to Tomcat and we can run test the application.
