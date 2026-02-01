


## Running the project
Since the project is using environment variable for accessing the Foursquare token, you need to 
export it.

export FSQ_API_KEY="paste your key here"

## 
If user is online, just return response from api
if user is offline, entries saved in database are filtered




## What can be improved
Error parsing and handling: Instead of passing hardcoded string for general errors, specific parsing 
and classifications of errors can be done to provide the user with more specific error messages and UI

UseCases

Defining standard measures instead of hardcoding

Caching previous searches and showing them below search field

Adding tests