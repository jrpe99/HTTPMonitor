Meta:

Narrative:
As a user application database client
I want to perform an action that updates the database with new HTTP success values
So that I can achieve a business goal that gets the current statistics for HTTP success

Scenario: Update the database and fetch latest statistics in JSON
Given the in-memory database is initialized
When a client update with new HTTP success data <IP>
Then the data is stored in the database <IP>
And new statistics can be fetched for ip <IP> and JSON generated

Examples:
|IP|
|100.23.34.100|
|110.27.90.120|
|110.27.90.122|
|110.27.90.123|
|110.27.90.124|
|110.27.90.125|
|110.27.90.126|
|110.27.90.127|
|110.27.90.128|

