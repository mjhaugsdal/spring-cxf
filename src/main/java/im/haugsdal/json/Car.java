package im.haugsdal.json;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Car {

    @JsonProperty
    private String color;

    @JsonGetter
    public String getColor() {
        return color;
    }

    @JsonSetter
    public void setColor(String color) {
        this.color = color;
    }
}