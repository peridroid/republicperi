package ru.devtron.republicperi.utils.validator;

public interface Validator<T> {
    boolean isValid(T value);
    String getDescription();
}