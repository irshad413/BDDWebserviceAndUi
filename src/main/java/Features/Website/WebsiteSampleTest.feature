Feature: Google search demonstration using Selenium

	Scenario: 124 search for some word in google
	Given I navigate to "https://www.google.co.in/" Url 
	When I enter "cucumber framework" in Search
	Then search results should be displayed 
	And I choose images in results
	
	Scenario Outline:125 searching google with examples
	Given I navigate to "<searchUrl>" Url
	When I enter "<word>" in Search
	Then search results should be displayed
	And Exit the browser
	Examples:
	|searchUrl					|word		|
	|https://www.google.co.in/	|cucumber	|
	|https://www.google.co.in/	|Selenium	|