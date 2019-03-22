Feature: Calculator 
This feature checks the calculator does calculations correctly

Scenario: 1 plus 2 equals 3
Given A reset calculator 
When The user presses '1'
And The user presses '+'	
And The user presses '2'
And The user presses '='	
Then The calculator displays '3'
