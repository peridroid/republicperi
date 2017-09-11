package ru.devtron.republicperi.utils.validator;

import android.util.Patterns;

/**
 * Created by flux on 10.09.2017.
 */

public class PhoneValidator implements Validator<String> {
    @Override
    public boolean isValid(String value) {
        return Patterns.PHONE.matcher(value).matches();
    }

    @Override
    public String getDescription() {
        return "Введите пожалуйста номер телефона";
    }
}
