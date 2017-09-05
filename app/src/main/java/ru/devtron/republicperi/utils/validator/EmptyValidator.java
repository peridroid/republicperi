package ru.devtron.republicperi.utils.validator;

import android.text.TextUtils;

public class EmptyValidator implements Validator<String> {

    @Override
    public boolean isValid(String value) {
        return !TextUtils.isEmpty(value);
    }

    @Override
    public String getDescription() {
        return "Поле не должно быть пустым";
    }
}