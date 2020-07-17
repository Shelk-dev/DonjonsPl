package fr.eazyender.donjon.donjon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class RoomsInit {
	
	public static List<IRoom> rooms_1 = new ArrayList<IRoom>();
	public static List<IRoom> rooms_2 = new ArrayList<IRoom>();
	
	public static void initRooms()
	{
		initRoomsLVL1();
		initRoomsLVL2();
	}
	
	public static void initRoomsLVL1() {
		
		List<Location> I_1_Loc = new ArrayList<Location>();
		I_1_Loc.add(new Location(null, -80, 5, -12, 180, 0)); //SPAWN
		I_1_Loc.add(new Location(null, -79, 6, -27, 0, 0)); //SORTIE
		IRoom I_1 = new IRoom(I_1_Loc, 0, 1, null, null, 0, 20*5);
		rooms_1.add(I_1);
		
		List<Location> I_2_Loc = new ArrayList<Location>();
		I_2_Loc.add(new Location(null, -114, 7, -19, 180, 0)); //SPAWN
		I_2_Loc.add(new Location(null, -112, 7, -27, 0, 0)); //SORTIE
		IRoom I_2 = new IRoom(I_2_Loc, 0, 1, null, null, 0, 20*5);
		rooms_1.add(I_2);
		
		List<Location> C_1_Loc = new ArrayList<Location>();
		C_1_Loc.add(new Location(null, -47, 3, -5, 180, 0)); //ENTREE
		C_1_Loc.add(new Location(null, -45, 2, -27, 0, 0)); //SORTIE
		IRoom C_1 = new IRoom(C_1_Loc, 1, 1, null, null, 0, 20*10);
		rooms_1.add(C_1);
		
		List<Location> C_2_Loc = new ArrayList<Location>();
		C_2_Loc.add(new Location(null, -15, 5, -5, 180, 0)); //ENTREE
		C_2_Loc.add(new Location(null, -15, 7, -27, 0, 0)); //SORTIE
		IRoom C_2 = new IRoom(C_2_Loc, 1, 1, null, null, 0, 20*10);
		rooms_1.add(C_2);
		
		List<Location> C_3_Loc = new ArrayList<Location>();
		C_3_Loc.add(new Location(null, 17, 3, -5, 180, 0)); //ENTREE
		C_3_Loc.add(new Location(null, 15, 6, -27, 0, 0)); //SORTIE
		IRoom C_3 = new IRoom(C_3_Loc, 1, 1, null, null, 0, 20*10);
		rooms_1.add(C_3);
		
		List<Location> C_4_Loc = new ArrayList<Location>();
		C_4_Loc.add(new Location(null, 48, 5, -5, 180, 0)); //ENTREE
		C_4_Loc.add(new Location(null, 51, 5, -27, 0, 0)); //SORTIE
		IRoom C_4 = new IRoom(C_4_Loc, 1, 1, null, null, 0, 20*10);
		rooms_1.add(C_4);
		
		List<Location> C_5_Loc = new ArrayList<Location>();
		C_5_Loc.add(new Location(null, 78, 7, -5, 180, 0)); //ENTREE
		C_5_Loc.add(new Location(null, 80, 7, -27, 0, 0)); //SORTIE
		IRoom C_5 = new IRoom(C_5_Loc, 1, 1, null, null, 0, 20*10);
		rooms_1.add(C_5);
		
		List<Location> M_1_Loc = new ArrayList<Location>();
		M_1_Loc.add(new Location(null, -66, 7, -94, 180, 0)); //ENTREE
		M_1_Loc.add(new Location(null, -66, 6, -126, 0, 0)); //SORTIE
		List<Location> M1_entity_loc = new ArrayList<Location>();
		M1_entity_loc.add(new Location(null, -57.29, 7.00, -106.74));
		M1_entity_loc.add(new Location(null, -69.69, 3.00, -115.22));
		M1_entity_loc.add(new Location(null, -64.40, 4.00, -109.50));
		M1_entity_loc.add(new Location(null, -57.51, 6.00, -113.70));
		List<String> M1_entity_type = new ArrayList<String>();
		M1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		M1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		M1_entity_type.add("BUSH_ZOMBIE");
		M1_entity_type.add("BUSH_ZOMBIE");
		IRoom M_1 = new IRoom(M_1_Loc, 3, 1, M1_entity_loc, M1_entity_type, 0, 20*30);
		rooms_1.add(M_1);
		
		List<Location> M_2_Loc = new ArrayList<Location>();
		M_2_Loc.add(new Location(null, -110.33, 3.00, -93.62, 180, 0)); //ENTREE
		M_2_Loc.add(new Location(null, -109.32, 13.00, -125.24, 0, 0)); //SORTIE
		List<Location> M2_entity_loc = new ArrayList<Location>();
		M2_entity_loc.add(new Location(null, -103.51, 13.00, -115.32));
		M2_entity_loc.add(new Location(null, -118.73, 13.00, -112.88));
		M2_entity_loc.add(new Location(null, -116.58, 12.00, -100.64));
		M2_entity_loc.add(new Location(null, -111.55, 4.00, -113.09));
		M2_entity_loc.add(new Location(null, -107.48, 15.00, -109.58));
		List<String> M2_entity_type = new ArrayList<String>();
		M2_entity_type.add("BUSH_SQUELETON");
		M2_entity_type.add("BUSH_SQUELETON");
		M2_entity_type.add("BUSH_ZOMBIE");
		M2_entity_type.add("BUSH_ZOMBIE");
		M2_entity_type.add("BUSH_PHANTOM");
		IRoom M_2 = new IRoom(M_2_Loc, 3, 1, M2_entity_loc, M2_entity_type, 0, 20*30);
		rooms_1.add(M_2);
		
		List<Location> M_3_Loc = new ArrayList<Location>();
		M_3_Loc.add(new Location(null, -65.92, 6.00, -135.44, 180, 0)); //ENTREE
		M_3_Loc.add(new Location(null, -68.64, 6.00, -167.34, 0, 0)); //SORTIE
		List<Location> M3_entity_loc = new ArrayList<Location>();
		M3_entity_loc.add(new Location(null, -54.33, 7.00, -151.43));
		M3_entity_loc.add(new Location(null, -66.47, 6.00, -154.76));
		M3_entity_loc.add(new Location(null, -74.83, 6.00, -149.35));
		List<String> M3_entity_type = new ArrayList<String>();
		M3_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		M3_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		M3_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		IRoom M_3 = new IRoom(M_3_Loc, 3, 1, M3_entity_loc, M3_entity_type, 0, 20*25);
		rooms_1.add(M_3);
		
		List<Location> B_1_Loc = new ArrayList<Location>();
		B_1_Loc.add(new Location(null, -33, 9, -37, 180, 0)); //ENTREE
		B_1_Loc.add(new Location(null, -33, 9, -84, 0, 0)); //SORTIE
		List<Location> B1_entity_loc = new ArrayList<Location>();
		B1_entity_loc.add(new Location(null, -38.93, 16.00, -74.51, -57.5f, 20.3f));
		B1_entity_loc.add(new Location(null, -23.59, 12.00, -46.76, 75.6f, 17.2f));
		B1_entity_loc.add(new Location(null, -47.55, 13.00, -65.42, -36.7f, 21.9f));
		B1_entity_loc.add(new Location(null, -23.54, 10.00, -54.43, 71.2f, 14.8f));
		List<String> B1_entity_type = new ArrayList<String>();
		B1_entity_type.add("BUSH_SQUELETON");
		B1_entity_type.add("BUSH_SQUELETON");
		B1_entity_type.add("BUSH_ZOMBIE");
		B1_entity_type.add("BUSH_ZOMBIE");
		IRoom B_1 = new IRoom(B_1_Loc, 4, 1, B1_entity_loc, B1_entity_type, 0, 20*30);
		rooms_1.add(B_1);
		
		List<Location> B_2_Loc = new ArrayList<Location>();
		B_2_Loc.add(new Location(null, 26, 8, -37, 180, 0)); //ENTREE
		B_2_Loc.add(new Location(null, 15, 7, -84, 0, 0)); //SORTIE
		List<Location> B2_entity_loc = new ArrayList<Location>();
		B2_entity_loc.add(new Location(null, 25.30, 13.00, -68.38));
		B2_entity_loc.add(new Location(null, 14.51, 12.00, -60.11));
		B2_entity_loc.add(new Location(null, 35.52, 5.00, -48.76));
		B2_entity_loc.add(new Location(null, 11.26, 4.00, -63.04));
		List<String> B2_entity_type = new ArrayList<String>();
		B2_entity_type.add("BUSH_SQUELETON");
		B2_entity_type.add("BUSH_SQUELETON");
		B2_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		B2_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		IRoom B_2 = new IRoom(B_2_Loc, 4, 1, B2_entity_loc, B2_entity_type, 0, 20*30);
		rooms_1.add(B_2);
		
		List<Location> B_3_Loc = new ArrayList<Location>();
		B_3_Loc.add(new Location(null, 76, 11, -37, 180, 0)); //ENTREE
		B_3_Loc.add(new Location(null, 76, 13, -84, 0, 0)); //SORTIE
		List<Location> B3_entity_loc = new ArrayList<Location>();
		B3_entity_loc.add(new Location(null, 84.86, 16.00, -62.64));
		B3_entity_loc.add(new Location(null,67.87, 11.00, -69.27));
		B3_entity_loc.add(new Location(null,67.87, 11.00, -69.27));
		B3_entity_loc.add(new Location(null, 80.53, 10.00, -56.74));
		B3_entity_loc.add(new Location(null, 62.79, 11.00, -55.40));
		List<String> B3_entity_type = new ArrayList<String>();
		B3_entity_type.add("BUSH_SQUELETON");
		B3_entity_type.add("BUSH_ZOMBIE");
		B3_entity_type.add("BUSH_ZOMBIE");
		B3_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		B3_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		IRoom B_3 = new IRoom(B_3_Loc, 4, 1, B3_entity_loc, B3_entity_type, 0, 20*30);
		rooms_1.add(B_3);
		
		List<Location> H_1_Loc = new ArrayList<Location>();
		H_1_Loc.add(new Location(null, -5, 13, -94, 180, 0)); //ENTREE
		H_1_Loc.add(new Location(null, 10, 5, -166, 0, 0)); //SORTIE
		List<Location> H1_entity_loc = new ArrayList<Location>();
		H1_entity_loc.add(new Location(null, -3.69, 23.00, -126.66));
		H1_entity_loc.add(new Location(null, -13.75, 17.00, -137.77));
		H1_entity_loc.add(new Location(null, -4.51, 17.00, -137.90));
		H1_entity_loc.add(new Location(null, 5.85, 13.00, -128.39));
		H1_entity_loc.add(new Location(null, 8.77, 19.00, -122.59));
		H1_entity_loc.add(new Location(null, 7.17, 9.00, -121.61));
		H1_entity_loc.add(new Location(null, -4.57, 8.00, -127.33));
		H1_entity_loc.add(new Location(null, -13.31, 7.00, -138.37));
		H1_entity_loc.add(new Location(null, -3.62, 13.00, -128.19));
		List<String> H1_entity_type = new ArrayList<String>();
		H1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		H1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		H1_entity_type.add("GRANITE_ARCHER_SQUELETON");
		H1_entity_type.add("GRANITE_ARCHER_SQUELETON");
		H1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		H1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		H1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		H1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		H1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		IRoom H_1 = new IRoom(H_1_Loc, 5, 1, H1_entity_loc, H1_entity_type, 0, 20*150);
		rooms_1.add(H_1);
		
		List<Location> H_2_Loc = new ArrayList<Location>();
		H_2_Loc.add(new Location(null, 51.50, 25.00, -93.83, 180, 0)); //ENTREE
		H_2_Loc.add(new Location(null, 74.26, 11.00, -165.49, 0, 0)); //SORTIE
		List<Location> H2_entity_loc = new ArrayList<Location>();
		H2_entity_loc.add(new Location(null, 93.54, 11.00, -132.29));
		H2_entity_loc.add(new Location(null, 62.72, 10.00, -137.48));
		H2_entity_loc.add(new Location(null, 71.04, 10.00, -137.56));
		H2_entity_loc.add(new Location(null, 97.38, 13.00, -144.04));
		H2_entity_loc.add(new Location(null, 101.02, 13.00, -121.61));
		H2_entity_loc.add(new Location(null, 95.55, 23.33, -120.00));
		H2_entity_loc.add(new Location(null, 56.62, 19.00, -136.46));
		H2_entity_loc.add(new Location(null, 74.70, 18.00, -158.66));
		List<String> H2_entity_type = new ArrayList<String>();
		H2_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		H2_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		H2_entity_type.add("BUSH_ZOMBIE");
		H2_entity_type.add("BUSH_ZOMBIE");
		H2_entity_type.add("BUSH_ZOMBIE");
		H2_entity_type.add("BUSH_PHANTOM");
		H2_entity_type.add("BUSH_PHANTOM");
		H2_entity_type.add("BUSH_PHANTOM");
		IRoom H_2 = new IRoom(H_2_Loc, 5, 1, H2_entity_loc, H2_entity_type, 0, 20*150);
		rooms_1.add(H_2);
		
		List<Location> BOSS_1_Loc = new ArrayList<Location>();
		BOSS_1_Loc.add(new Location(null, -94, 11, -37, 180, 0)); //ENTREE
		List<Location> BOSS_1_entity_loc = new ArrayList<Location>();
		BOSS_1_entity_loc.add(new Location(null, -94.50, 11.00, -58.50, 0, 0));
		BOSS_1_entity_loc.add(new Location(null, -93.50, 11.00, -58.50, 0, 0));
		BOSS_1_entity_loc.add(new Location(null, -95.50, 11.00, -58.50, 0, 0));
		List<String> BOSS_1_entity_type = new ArrayList<String>();
		BOSS_1_entity_type.add("GRANITE_KING_SQUELETON");
		BOSS_1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		BOSS_1_entity_type.add("GRANITE_KNIGHT_SQUELETON");
		IRoom BOSS_1 = new IRoom(BOSS_1_Loc, 6, 1, BOSS_1_entity_loc, BOSS_1_entity_type, 0, 20*45);
		rooms_1.add(BOSS_1);
		
		List<Location> BOSS_2_Loc = new ArrayList<Location>();
		BOSS_2_Loc.add(new Location(null, -94, 11, -37, 180, 0)); //ENTREE
		List<Location> BOSS_2_entity_loc = new ArrayList<Location>();
		BOSS_2_entity_loc.add(new Location(null, -94.50, 11.00, -58.50, 0, 0));
		List<String> BOSS_2_entity_type = new ArrayList<String>();
		BOSS_2_entity_type.add("BUSH_GOLEM");
		IRoom BOSS_2 = new IRoom(BOSS_2_Loc, 6, 1, BOSS_2_entity_loc, BOSS_2_entity_type, 0, 20*45);
		rooms_1.add(BOSS_2);
		
	}

	public static void initRoomsLVL2() {
		
		List<Location> I_1_Loc = new ArrayList<Location>();
		I_1_Loc.add(new Location(null, 96.5, 12.00, 7.5, 0, 0)); //SPAWN
		I_1_Loc.add(new Location(null, 97.5, 9.00, 24.5, 180, 0)); //SORTIE
		IRoom I_1 = new IRoom(I_1_Loc, 0, 1, null, null, 0, 20*5);
		rooms_2.add(I_1);
		
		List<Location> I_2_Loc = new ArrayList<Location>();
		I_2_Loc.add(new Location(null, 64.5, 8.00, 9.5, 0, 0)); //SPAWN
		I_2_Loc.add(new Location(null, 62.50, 5.00, 24.79, 180, 0)); //SORTIE
		IRoom I_2 = new IRoom(I_2_Loc, 0, 1, null, null, 0, 20*5);
		rooms_2.add(I_2);
		
		List<Location> C_1_Loc = new ArrayList<Location>();
		C_1_Loc.add(new Location(null, 32.34, 12.00, 2.76, 0, 0)); //ENTREE
		C_1_Loc.add(new Location(null, 29.53, 4.00, 24.32, 180, 0)); //SORTIE
		IRoom C_1 = new IRoom(C_1_Loc, 1, 1, null, null, 0, 20*10);
		rooms_2.add(C_1);
		
		List<Location> C_2_Loc = new ArrayList<Location>();
		C_2_Loc.add(new Location(null, 0.36, 7.00, 2.49, 0, 0)); //ENTREE
		C_2_Loc.add(new Location(null, 1.59, 7.00, 24.65, 180, 0)); //SORTIE
		IRoom C_2 = new IRoom(C_2_Loc, 1, 1, null, null, 0, 20*10);
		rooms_2.add(C_2);
		
		List<Location> C_3_Loc = new ArrayList<Location>();
		C_3_Loc.add(new Location(null, -30.09, 13.00, 2.43, 0, 0)); //ENTREE
		C_3_Loc.add(new Location(null, -31.27, 12.00, 24.46, 180, 0)); //SORTIE
		IRoom C_3 = new IRoom(C_3_Loc, 1, 1, null, null, 0, 20*10);
		rooms_2.add(C_3);
		
		List<Location> C_4_Loc = new ArrayList<Location>();
		C_4_Loc.add(new Location(null, -62.87, 9.00, 2.44, 0, 0)); //ENTREE
		C_4_Loc.add(new Location(null, -65.19, 8.00, 24.24, 180, 0)); //SORTIE
		IRoom C_4 = new IRoom(C_4_Loc, 1, 1, null, null, 0, 20*10);
		rooms_2.add(C_4);
		
		List<Location> C_5_Loc = new ArrayList<Location>();
		C_5_Loc.add(new Location(null, 32.71, 36.00, 2.67, 0, 0)); //ENTREE
		C_5_Loc.add(new Location(null, 33.26, 36.00, 24.33, 180, 0)); //SORTIE
		IRoom C_5 = new IRoom(C_5_Loc, 1, 1, null, null, 0, 20*10);
		rooms_2.add(C_5);
	}
}
