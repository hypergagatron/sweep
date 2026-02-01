## Project requirements

- Develop a mini Android application that uses the Foursquare API to search for and display details about 
venues, with support for offline mode
- Allow the user to search for venues near their current location using a search term. Display the search results in a list
- When a user selects a venue from the search results, open a screen with details for that venue. Display information such as name, description, opening hours, photos, and contact details.

### Restrictions
Project requirements define displaying of pictures, opening hours and description of venues, but at this moment, these pieces of info are behind the paywall. For illustration purposes, 3 links are hardcoded as example image urls. Opening hours and description are not displayed.

## Running the project
Since the project is using environment variable for accessing the Foursquare token, you need to 
export it.

export FSQ_API_KEY="paste your key here"

## 
If user is online, just return response from api
if user is offline, entries saved in database are filtered




## For future improvement
Error parsing and handling: Instead of passing hardcoded string for general errors, specific parsing 
and classifications of errors can be done to provide the user with more specific error messages and UI

UseCases

Defining standard measures instead of hardcoding

Caching previous searches and showing them below search field

Adding tests

Giving user the chance to select desired search radius
