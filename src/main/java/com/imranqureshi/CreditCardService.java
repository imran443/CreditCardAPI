package com.imranqureshi;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CreditCardService {

    public CreditCardResponse validate(CreditCard card, BlackList blackList) {

        // The regex used to check what credit card it is.
        String cardRegex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14})|)";
        String cardNum = card.getCreditCardNum();
        String expDate = card.getExpiration();

        Pattern pattern = Pattern.compile(cardRegex);

        //Strip all spaces
        cardNum = cardNum.replaceAll(" ", "");
        Matcher matcher = pattern.matcher(cardNum);

        //This value will be flipped to true if the expiration date is valid.
        boolean validExpDate = false;

        // Check if the expiration date is valid.
        try {
            validExpDate = validateExpiryDate(expDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Check if the credit card number is on the black list;
        boolean isOnBlackList = checkBlackList(card.getCreditCardNum(), blackList);

        // Check if the credit card number matches the regex and the other conditions stated above.
        if (matcher.matches() && validExpDate && luhnCheck(cardNum)) {
            if (isOnBlackList) {
                return new CreditCardResponse(cardNum, expDate, false, isOnBlackList);
            }
            return new CreditCardResponse(cardNum, expDate, true, isOnBlackList);
        }

        return new CreditCardResponse(cardNum, expDate,false, isOnBlackList);
    }

    private boolean checkBlackList(String cardNum, BlackList blackList) {
        List<String> blackListCardNums = blackList.getBlacklist();
        // Iterate through the each black listed credit card and compare it.
        for (String blackListedCard: blackListCardNums) {
            if (blackListedCard.equals(cardNum)) {
                return true;
            }
        }

        return false;
    }

    public boolean validateExpiryDate(String expDate) throws ParseException {
        boolean validFormat = expDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");

        if (validFormat) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
            simpleDateFormat.setLenient(false);
            Date expiry = simpleDateFormat.parse(expDate);
            boolean notExpired = new Date().before(expiry);
            return notExpired;
        }
        return false;
    }

    public boolean luhnCheck(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
