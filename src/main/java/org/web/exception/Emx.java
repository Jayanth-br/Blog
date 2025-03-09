package org.web.exception;

public final class Emx{

    public Emx() {
    }

    public static void throwIfEmpty(String content, String errorMessage){
        if(content == null || content.trim().length() <= 0) {
            throw new ValidationException(errorMessage);
        }
    }

    public static void throwIfPasswordNotValid(String content, String errorMessage){
        if(content == null || content.trim().length() < 8){
            throw new ValidationException(errorMessage);
        }
    }

    public static void throwIfAlreadyExists(boolean exist, String errorMessage){
        if(exist) throw new ValidationException(errorMessage);
    }

    public static void throwIfNullOrZero(Long content, String errorMessage){
        if(content == null || content <= 0) {
            throw new ValidationException(errorMessage);
        }
    }

}
