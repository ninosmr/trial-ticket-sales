# trial-ticket-sales
Thank you for taking the time to review this project.
To run, you will need Xampp, in which you will need to start a MySQL server and an Apache server, simply by clicking start on both. Then Click "Admin" on the MySQL row, and once it opens in your browser (http://localhost/phpmyadmin/), create a new table called "ticketing". You can give it another name if you like, but then you will have to change the name in the application.properties file for it to recognize the table.
Once this is all set up, simply run click Run on the TrialApplication class from your IDE (I used Intellij), and Spring Boot should start it right up. Reload the MySQL admin page and ensure that all the tables are created correcltly. You should have the following 12 tables:

bank_card

device

event_data

event_data_event_seats

hibernate_sequence

seat

user

user_bank_cards

user_devices

user_token

user_user_purchased_seats

user_user_tokens

bank_card, device, event_data, seat, user, and user_token are the tables that hold regular data, while event_data_event_seats, user_bank_cards, user_devices, user_user_purchased_seats, and user_user_tokens simply store relations between the other tables.
Now that the database is up and running, it's time to test it. To do this, download Postman or your favorite API caller and run a simple end to end test of the major functionality.
I've included the Postman request json in the test folder (TicketApiTest.postman_collection), so if you do have Postman just import the file and run the API calls one by one. You can check the database at every step to ensure that the information is getting saved, but I've also included lots of http error codes if something is amiss, and you should be able to view the JSON responses in postman either way.
Feel free to edit the requests in any way you like, but please be aware that if you register a user with different info, you will have to get the user's hash from the database and put it in the header with the user's ID and device ID, as the buy ticket and register new card calls check the user hash to ensure that it's really that person doing the action.   
