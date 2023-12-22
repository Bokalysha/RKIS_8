package ru.bokalysha.rkis.Prac8.exceptions.update;

public class SmartphoneNotUpdatedException extends ModelNotUpdatedException {

    public static final String entityType = "Smartphone";

    public SmartphoneNotUpdatedException(String msg) {
        super(entityType, msg);
    }
}