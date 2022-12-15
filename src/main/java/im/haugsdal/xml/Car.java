package im.haugsdal.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Banana")
public class Car {

    @XmlElement(required = true, name = "color")
    private String color;

    @XmlElement(required = true, name = "brand")
    private String brand;


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
