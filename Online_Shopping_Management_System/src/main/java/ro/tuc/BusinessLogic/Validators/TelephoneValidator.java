package ro.tuc.BusinessLogic.Validators;

import ro.tuc.Model.Client;

import java.util.regex.Pattern;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */

public class TelephoneValidator implements Validator<Client>{
    private static final String PHONE_PATTERN = "^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
    public void validate(Client t) throws IllegalArgumentException{
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        if (!pattern.matcher(t.getNr_telefon()).matches()) {
            throw new IllegalArgumentException("Numar de telefon invalid!");
        }
    }
}
