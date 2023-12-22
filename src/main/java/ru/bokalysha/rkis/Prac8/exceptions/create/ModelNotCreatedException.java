package ru.bokalysha.rkis.Prac8.exceptions.create;


public class ModelNotCreatedException extends RuntimeException {
    public ModelNotCreatedException(String entityType , String msg) {
        super(entityType + " not created because: " + msg);
    }

}