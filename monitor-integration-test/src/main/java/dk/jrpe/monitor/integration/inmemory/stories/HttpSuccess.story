Meta:

Narrative:
As a user application database client
I want to perform an action that updates the database with new HTTP success values
So that I can achieve a business goal that gets the current statistics for HTTP success

Scenario: Update the database and fetch latest statistics in JSON
Given the in-memory database is initialized
When a client update with new HTTP success data 100.23.34.100
Then the data is stored in the database 100.23.34.100
And new statistics can be fetched and JSON generated

