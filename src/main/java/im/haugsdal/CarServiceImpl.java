package im.haugsdal;

import im.haugsdal.xml.Car;

public class CarServiceImpl implements CarService {



    @Override
    public String ping(String ping) {
        return "pong";
    }

    @Override
    public Car xmlBanana(Car car) {
        var color = car.getColor();
        System.out.println(color);
        car.setColor("green");
        return car;
    }

    @Override
    public im.haugsdal.json.Car jsonBanana(im.haugsdal.json.Car car) {
        var color = car.getColor();
        car.setColor("green");
        return car;
    }
}
