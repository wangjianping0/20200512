package com.example.firstdemo.util;

public class NullArgumentException extends IllegalArgumentException {

    public NullArgumentException(String argName){
        super((argName==null?"Argument":argName)+" must not be null.");
    }
}
