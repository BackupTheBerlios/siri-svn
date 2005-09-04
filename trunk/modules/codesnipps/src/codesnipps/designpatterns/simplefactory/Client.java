package codesnipps.designpatterns.simplefactory;

import codesnipps.designpatterns.simplefactory.CarFactory.CarType;


public class Client {
	CarFactory factory = new CarFactory();
	Car myCar = factory.createCar(CarType.AUDI);
	
	
	
	
}
