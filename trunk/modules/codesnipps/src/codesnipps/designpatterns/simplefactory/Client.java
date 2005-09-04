package codesnipps.designpatterns.simplefactory;

import codesnipps.designpatterns.simplefactory.CarFactory.CarType;

/**
 * Client useing a fsimple actory to retrieve a car.
 * @author gepo
 *
 */
public class Client {
	private static CarFactory factory = new CarFactory();
	
	public static void main(String[] args) {
		Car myCar = factory.createCar(CarType.AUDI);
		String mycolor = myCar.getColor();		
		System.out.println("All audi cars are of color : " + mycolor);
		myCar = factory.createCar(CarType.VOLVO);
		mycolor = myCar.getColor();		
		System.out.println("All volvo cars are of color : " + mycolor);

	}
	
}
