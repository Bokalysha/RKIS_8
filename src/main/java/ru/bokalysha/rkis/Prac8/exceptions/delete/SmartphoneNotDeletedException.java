package ru.bokalysha.rkis.Prac8.exceptions.delete;

public class SmartphoneNotDeletedException extends ModelNotDeletedException {

    public static final String entityType = "Smartphone";

    public SmartphoneNotDeletedException(String msg) {
        super(entityType, msg);
    }
}