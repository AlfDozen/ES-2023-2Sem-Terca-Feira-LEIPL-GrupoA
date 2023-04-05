package AlfDozen.ES_2023_2Sem_Terca_Feira_LEIPL_GrupoA;

public final class Room {
	
	private String name;
	private Integer capacity;
	
	Room(String name, Integer capacity){
		this.name = name;
		if(capacity != null && capacity <= 0) {
			throw new IllegalArgumentException("Room needs to have a positive number capacity");
		}
		this.capacity = capacity;
	}
	
	Room(String name, String capacity){
		this.name = name;
		if(capacity == null) {
			this.capacity = null;
			return;
		}
		try {
			this.capacity = Integer.parseInt(capacity);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("The provided string doesn't correspond to a number");
		}
		if(this.capacity <= 0) {
			throw new IllegalArgumentException("Room needs to have a positive number capacity");
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
		if(capacity != null && capacity < 0) {
			throw new IllegalArgumentException("Room needs to have a positive number capacity");
		}
		this.capacity = capacity;
	}
	
	void setCapacity(String capacity) {
		if(capacity == null) {
			this.capacity = null;
			return;
		}
		Integer number;
		try {
			number = Integer.parseInt(capacity);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("The provided string doesn't correspond to a number");
		}
		if(number < 0) {
			throw new IllegalArgumentException("Room needs to have a positive number capacity");
		}
		this.capacity = number;
	}
	
	boolean isComplete() {
		return name != null && capacity != null;
	}
	
	@Override
	public String toString() {
		return "Room " + (name == null ? Lecture.FORNULL : name) +
				" (Capacity " + (capacity == null ? Lecture.FORNULL : capacity) + ")";  
	}
	
}
