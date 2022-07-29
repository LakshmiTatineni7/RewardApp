      RewardApp
      ---------------------------
      
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.
 
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
 
Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.
 
•	Make up a data set to best demonstrate your solution
•	Check solution into GitHub and share the URL(Make it Public)
·	Solution should be built using ReactJS or Spring(Spring Boot)
·	Instructions for running the solution should be uploaded in ReadMe file on GitHub

------------------
## To run This project locally
git clone https://github.com/LakshmiTatineni7/RewardApp.git

## In windows 
- open command prompt and change directory to application
- runn bellow commands to install

mvn clean install

mvn test 

mvnw spring-boot:run

Open this url to see the customers data aand their purchase transaction details available in memeory db.
      
      http://localhost:8080/customers

Individual customer transaction history 

      http://localhost:8080/customers/100

