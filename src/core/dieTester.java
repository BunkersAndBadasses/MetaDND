package core;

import java.util.ArrayList;

public class dieTester {

	public static void main(String [] args){
		int size = 6;
		int count = 3;
		int rolled = 0;

		ArrayList<Roll> roll = new ArrayList<Roll>(10);
		roll.add(new Roll(size, count));
		roll.add(new Roll(20, 3, 2, 1));
		DnDie.saveFavDie("test", roll);

		ArrayList<Roll> loaded = new ArrayList<Roll>(10);
		loaded = DnDie.loadFavDie("test");

		for(int i = 0; i < loaded.size(); i++){
			System.out.println();
			System.out.println("die_Size: " + loaded.get(i).getDieSize());
			System.out.println("die_Count: " + loaded.get(i).getDieCount());
			System.out.println("modifier: " + loaded.get(i).getModifier());
			System.out.println("player_Level_Adjust: " + loaded.get(i).getPlayerLevelAdjust());
		}

		//tests the favorite load and roll together, made for the buttons?
		rolled = DnDie.rollFavDie("test");
		
		System.out.println();
		System.out.println("Test: " + rolled);
		
		DnDie.deleteFavDie("test");
		
		loaded = DnDie.loadFavDie("test");
		
	}
}
