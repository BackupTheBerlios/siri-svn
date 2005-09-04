package codesnipps.designpatterns.simplefactory;

/**
 * Factory for creating cars.
 * 
 * @author gepo
 */
public class CarFactory {
	Car acar;
	public enum CarType {
		AUDI, VOLVO
	};

	public Car createCar(CarType cartype) {
		switch (cartype) {
		case AUDI:
		{
			acar = new Audi();
			return acar;
		}
		case VOLVO:
		{
			acar = new Volvo();
			return acar;
		}
		default:
			return null;
		}
	}

}
