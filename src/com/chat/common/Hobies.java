package com.chat.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
@XmlType
@XmlEnum(String.class)
public enum Hobies implements Serializable{


    Swimming("Hobies"),
    Songs("Listening to songs"),
    Siging("Singing"),
    Programing("Programing"),
    Golf("Playing golf"),
    Football("Playing football"),
    backetball("Playing basket ball"),
    Tennis("Playing Tennis");


    String value;

    private Hobies(String value) {
        this.value = value;
    }
    
    public String getValue()
    {
        return value;
    }


}
