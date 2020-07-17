package fr.eazyender.donjon.donjon;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public class IRoom {
	
	private List<Location> doors = new ArrayList<Location>(); 
	private int type;
	private int biome;
	private List<Location> entity_loc = new ArrayList<Location>();
	private List<String> entity_type = new ArrayList<String>();
	private int numberOfMobs;
	private int maxTime;
	
	public IRoom(List<Location> doors, int type, int biome, List<Location> entity_loc, List<String> entity_type, int numberOfMobs, int maxTime) {
		this.doors = doors;
		this.type = type;
		this.biome = biome;
		this.entity_loc = entity_loc;
		this.entity_type = entity_type;
		this.numberOfMobs = numberOfMobs;
		this.maxTime = maxTime;
	}
	
	
	


	public int getMaxTime() {
		return maxTime;
	}





	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}





	public List<Location> getEntity_loc() {
		return entity_loc;
	}





	public void setEntity_loc(List<Location> entity_loc) {
		this.entity_loc = entity_loc;
	}





	public List<String> getEntity_type() {
		return entity_type;
	}





	public void setEntity_type(List<String> entity_type) {
		this.entity_type = entity_type;
	}





	public int getNumberOfMobs() {
		return numberOfMobs;
	}



	public void setNumberOfMobs(int numberOfMobs) {
		this.numberOfMobs = numberOfMobs;
	}



	public List<Location> getDoors() {
		return doors;
	}

	public void setDoors(List<Location> doors) {
		this.doors = doors;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBiome() {
		return biome;
	}

	public void setBiome(int biome) {
		this.biome = biome;
	}
	
	

}
