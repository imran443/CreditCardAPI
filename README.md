# CreditCardAPI

The following is a simple RESTful API for Credit Card validation. 

## Notes on usage:
- The endpoint to send the request to is /validatecard
- The request should be structured in this style for the API endpoint to work:
```javascript
[
	{
		"creditCardNum": "4512 2386 2252 6415",
		"expiration": "01/19"
		
	},
	{
		"creditCardNum": "4788 3845 3855 2446",
		"expiration": "01/22"
	}
]
```
