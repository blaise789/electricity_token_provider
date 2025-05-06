### electricity token provider application  ###
features
## auth ##
 -registration:names, email, phone and national id, password and roles(
ROLE_ADMIN, ROLE_CUSTOMER)
-login

## meter registration(admin)
 -register meter number to a specific user which is unique and made up of 6 numbers
 -person can own multiple numbers
## purchase electricity
->generate token: id,amount,meter_number,token,token_status(USED,NEW,EXPIRED)
token_value_days
## searching a token
## notification of expired token
- using stored database routines to check for token that are going to expire in 5 hours and generate and send  notification to the user through email telling him/her to purchase new token
-notification(id, meter number, message, issuedDate)
# electricity_token_provider
