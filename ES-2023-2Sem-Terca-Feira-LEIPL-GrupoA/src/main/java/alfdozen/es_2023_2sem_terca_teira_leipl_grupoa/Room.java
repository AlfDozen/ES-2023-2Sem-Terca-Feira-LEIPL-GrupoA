package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

/**
 * The Room class is used to define the name and capacity of the room where the
 * lecture is scheduled to be. Since the information used to instantiate this
 * class will frequently come from documents, the constructor accepts all
 * arguments as Strings. Null arguments are accepted by the constructor and
 * setters. With the function isComplete it can be checked if there are still
 * null attributes.
 * 
 * @author alfdozen
 * @version 1.0.0
 */
final class Room {

	static final String NEGATIVE_EXCEPTION = "Room needs to have a positive number capacity";
	static final String NOT_NUMBER_EXCEPTION = "The provided string doesn't correspond to a number";
	private String name;
	private Integer capacity;

	/**
	 * Constructor creates a room with name and capacity value.
	 * 
	 * @param name     the name for the room.
	 * @param capacity the value of the capacity for the room.
	 * @throws IllegalArgumentException if the capacity value is equal to zero or
	 *                                  negative.
	 */
	Room(String name, Integer capacity) {
		this.name = name;
		if (capacity != null && capacity <= 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.capacity = capacity;
	}

	/**
	 * Constructor creates a room with name and capacity value. Receives the
	 * capacity as a String and converts to Integer.
	 * 
	 * @param name     the name for the room.
	 * @param capacity the value of the capacity for the room as a String.
	 * @throws NumberFormatException    if the capacity String cannot be parsed to
	 *                                  an Integer.
	 * @throws IllegalArgumentException if the capacity value is equal to zero or
	 *                                  negative.
	 */
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

	/**
	 * Returns the name of the room.
	 * 
	 * @return the name of the room.
	 */
	String getName() {
		return name;
	}

	/**
	 * Sets the name for the room.
	 * 
	 * @param name the name for the room.
	 */
	void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the value of the capacity of the room.
	 * 
	 * @return the value of the capacity of the room.
	 */
	Integer getCapacity() {
		return capacity;
	}

	/**
	 * Sets the value of the capacity for the room.
	 * 
	 * @param capacity the value of the capacity for the room.
	 * @throws IllegalArgumentException if the capacity value is equal to zero or
	 *                                  negative.
	 */
	void setCapacity(Integer capacity) {
		if (capacity != null && capacity < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.capacity = capacity;
	}

	/**
	 * Sets the value of the capacity for the room. Receives the capacity as a
	 * String and converts to Integer.
	 * 
	 * @param capacity the value of the capacity for the room as a String.
	 * @throws NumberFormatException    if the capacity String cannot be parsed to
	 *                                  an Integer.
	 * @throws IllegalArgumentException if the capacity value is equal to zero or
	 *                                  negative.
	 */
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

	/**
	 * Checks if the name and the capacity are not null. If a null is found, the
	 * method returns false. Otherwise, it returns true.
	 * 
	 * @return true if both the name and capacity are not null; false otherwise.
	 */
	boolean isComplete() {
		return name != null && capacity != null;
	}

	/**
	 * Return a string representation of the room, including its name and capacity
	 * value. If any of this information is not defined, that information is instead
	 * given as "Unknown".
	 * 
	 * @return a string representation of the room.
	 */
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
