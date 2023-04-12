package alfdozen_es_2023_2sem_terca_feira_leipl_grupoa;

/**
 * @author alfdozen
 * 
 *         The Room class is used to define the name and capacity of the room
 *         where the lecture is scheduled to be. Since the information used to
 *         instantiate this class will frequently come from documents, the
 *         constructor accepts all arguments as Strings. Null arguments are
 *         accepted by the constructor and setters. With the function isComplete
 *         it can be checked if there are still null attributes.
 * 
 */

final class Room {

	static final String NEGATIVE_EXCEPTION = "Room needs to have a positive number capacity";
	static final String NOT_NUMBER_EXCEPTION = "The provided string doesn't correspond to a number";
	private String name;
	private Integer capacity;

	Room(String name, Integer capacity) {
		this.name = name;
		if (capacity != null && capacity <= 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.capacity = capacity;
	}

	Room(String name, String capacity) {
		this.name = name;
		if (capacity == null) {
			this.capacity = null;
			return;
		}
		try {
			this.capacity = Integer.parseInt(capacity);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(NOT_NUMBER_EXCEPTION);
		}
		if (this.capacity <= 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	Integer getCapacity() {
		return capacity;
	}

	void setCapacity(Integer capacity) {
		if (capacity != null && capacity < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.capacity = capacity;
	}

	void setCapacity(String capacity) {
		if (capacity == null) {
			this.capacity = null;
			return;
		}
		Integer number;
		try {
			number = Integer.parseInt(capacity);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(NOT_NUMBER_EXCEPTION);
		}
		if (number < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.capacity = number;
	}

	boolean isComplete() {
		return name != null && capacity != null;
	}

	@Override
	public String toString() {
		String str = "Room ";
		if (name == null) {
			str += Lecture.FOR_NULL;
		} else {
			str += name;
		}
		if (capacity == null) {
			str += " (Capacity " + Lecture.FOR_NULL;
		} else {
			str += " (Capacity " + capacity;
		}
		return str + ")";
	}
}
