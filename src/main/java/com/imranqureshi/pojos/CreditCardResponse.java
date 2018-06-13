package com.imranqureshi.pojos;

// This class will create the response for each credit card received.
public class CreditCardResponse {
    private String creditCardNum;
    private String expirationDate;
    private boolean validCreditCard;
    private boolean blackListed;

    public CreditCardResponse(String creditCardNum, String expirationDate , boolean validCreditCard, boolean blackListed) {
        this.creditCardNum = creditCardNum;
        this.expirationDate = expirationDate;
        this.blackListed = blackListed;
        this.validCreditCard = validCreditCard;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public boolean isBlackListed() {
        return blackListed;
    }

    public boolean isValidCreditCard() {
        return validCreditCard;
    }
}
