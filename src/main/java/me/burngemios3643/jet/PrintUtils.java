package me.burngemios3643.jet;

import java.util.Scanner;

public class PrintUtils {

	public static void cls() {
		for(int i = 0; i < 40; i++)System.out.println();
	}
	
	public static void enterToContinue(Scanner sc) {
		System.out.println("Press Enter to continue...");
		sc.nextLine();
		cls();
	}
	
	public static void printWelcome() {
		cls();
		System.out.println("==============================");
		System.out.println(alignCenter(30, Main.NAME));
		System.out.println(alignCenter(30, "v"+Main.VERSION));
		System.out.println(alignCenter(30, "by BurnGemios3643"));
		System.out.println("==============================");
		System.out.println(Main.NAME+" allow you to test simple java programms \nwithout managing classes or methods.");
		System.out.println("example:\n");
		System.out.println("	import java.util.*;");
		System.out.println("	Scanner sc = new Scanner(System.in);");
		System.out.println("	String line;");
		System.out.println("	System.out.println(\"waiting for lines...\");");
		System.out.println("	while((line = sc.nextLine()) != null) {");
		System.out.println("		System.out.println(\"in: \"+line);");
		System.out.println("		if(line.equalsIgnoreCase(\"exit\"))break;");
		System.out.println("	}");
		System.out.println("\nUse \""+Commands.HELP.getPattern()+"\" to see commands");
		System.out.println("==============================");
	}
	
	public static String alignCenter(int width, String text) {
		String res = "";
		for(int i = 0; i < width/2-Math.round((double)text.replaceAll((char)27+"\\[[0-9]{1,2}m", "").length()/2); i++)res += " ";
		res += text;
		return res;
	}
	
}
