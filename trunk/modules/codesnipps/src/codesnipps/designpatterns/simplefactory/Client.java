package codesnipps.designpatterns.simplefactory;

import codesnipps.designpatterns.simplefactory.CarFactory.CarType;

/**
 * Cleint useing a factory to retrieve a car.
 * @author gepo
 *
 */
public class Client {
	
	public static void main(String[] args) {
		CarFactory factory = new CarFactory();
		Car myCar = factory.createCar(CarType.AUDI);
		
		String mycolor = myCar.getColor();
		
		System.out.println(mycolor);

	}
	
}
