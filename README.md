# CreditCardAPI

The following is a simple RESTful API for Credit Card validation. 

## Notes on usage:
- The endpoint to send the request to is /validatecard
- The request should be structured in this style for the API endpoint to work:
```javascript
[
	{
		"creditCardNum": "4657 5122 2152 6415",
		"expiration": "01/19"
		
	},
	{
		"creditCardNum": "4788 3845 3855 2446",
		"expiration": "01/22"
	}
]
```
- The response will be in the following format:
```javascript
[
    {
        "creditCardNum": "4657512221526415",
        "expirationDate": "01/19",
        "validCreditCard": false,
        "blackListed": true
    },
    {
        "creditCardNum": "4788384538552446",
        "expirationDate": "01/22",
        "validCreditCard": false,
        "blackListed": true
    }
]
```
