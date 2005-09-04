package codesnipps.designpatterns.factory;

import codesnipps.designpatterns.simplefactory.Volvo;

public class CarFactory {
	public enum CarType {AUDI, VOLVO};
	
	public Car createCar(CarType cartype)
	{
		switch (cartype) {
		case AUDI:
			return new Audi();
		case VOLVO:
			return new Volvo();
		default:
			return null;
		}
	}
		
}
