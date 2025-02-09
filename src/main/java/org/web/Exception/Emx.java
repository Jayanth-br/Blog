package org.web.Exception;

public final class Emx{

    public Emx() {
    }

    public static void throwIfEmpty(String content, String errorMessage){
        if(content == null || content.trim().length() <= 0) {
            throw new ValidationException(errorMessage);
        }
    }

}
