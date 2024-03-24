package ro.tuc.BusinessLogic.Validators;

import javax.swing.*;
/**
 * @Author Boncea Natalia
 * @Since May 18, 2023
 */
public interface Validator<T> {
    public void validate(T t);
}
