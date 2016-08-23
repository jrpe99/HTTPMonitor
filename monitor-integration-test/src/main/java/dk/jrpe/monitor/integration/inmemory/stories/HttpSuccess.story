Meta:

Narrative:
As a user application database client
I want to perform an action that updates the database with new HTTP success values
So that I can achieve a business goal get the current statistics for HTTP success

Scenario: scenario description
Given the in-memory database is initialized
When a client update with new HTTP success data
Then the database store the value in the database
And new statistics can be fetched
