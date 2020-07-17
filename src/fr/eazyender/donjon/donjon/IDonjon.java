package fr.eazyender.donjon.donjon;

import java.util.ArrayList;
import java.util.List;

public class IDonjon {
	
	private List<IRoom> donjon = new ArrayList<IRoom>();
	private int size;
	private int biome;
	private short difficulty;
	
	public IDonjon(List<IRoom> donjon, int size, int biome, short difficulty) {
		this.donjon = donjon;
		this.size = size;
		this.biome = biome;
		this.difficulty = difficulty;
	}

	public List<IRoom> getDonjon() {
		return donjon;
	}

	public void setDonjon(List<IRoom> donjon) {
		this.donjon = donjon;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getBiome() {
		return biome;
	}

	public void setBiome(int biome) {
		this.biome = biome;
	}

	public short getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(short difficulty) {
		this.difficulty = difficulty;
	}
	
	

}
