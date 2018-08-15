Feature: Sample test for Webservices with rest-assured, integrating Cucumber

#very famous example on Google which uses rest-assured
  Scenario: User calls web service to get a book by its ISBN
	Given a book exists with an isbn of 9781451648546
	When a user retrieves the book by isbn
	Then the status code is 200
	And response includes the following
	| totalItems 	 		| 1 					|
	| kind					| books#volumes			|
   And response includes the following in any order
	| items.volumeInfo.title 					| Steve Jobs			|
	| items.volumeInfo.publisher 				| Simon and Schuster	|   
	| items.volumeInfo.pageCount 				| 630					|
	
#another test, POST method for Rest API using DemoQa free API
  Scenario: User tries to register on DemoQA dummy API
	Given user calls webservice for registration
	|FirstName	| Abram123	|
	|LastName	| Pentapati	|
	|UserName	| James007	|
	|Password	| 123456	|
	|Email		| AbramPentapati@gmail.com	|
	When user posts a request with information
	Then error message should be recieved

#another test, using SOAP API. One thing user should know is SOAP API will mostly have POST method alone
  Scenario: User wants to list all currencies available
	Given user has a webservice for currency converter
	When user tries to retrieve all currencies
	Then response should have all currencies