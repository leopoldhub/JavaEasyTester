package me.burngemios3643.jet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static final String NAME = "JET (Java Easy Tester)";
	public static final String PREFIX = "/";
	public static final String VERSION = "0.1";
	
	public static void main(String... args) {
		Scanner sc = new Scanner(System.in);
		List<String> lines = new ArrayList<>();
		String line;
		int actualline = 0;
		PrintUtils.printWelcome();
		System.out.print("> ");
		while((line = sc.nextLine()) != null) {
			PrintUtils.cls();
			boolean executed = false;
			for(int i = 0; i < Commands.values().length; i++) {
				Commands cmd = Commands.values()[i];
				if(line.matches(cmd.getPattern())) {
					executed = true;
					cmd.run(lines, line, actualline, sc);
					lines = cmd.getExecuted().getLines();
					actualline = cmd.getExecuted().getActualLine();
					break;
				}
			}
			if(!executed) {
				if(actualline == lines.size()) {
					lines.add(line);
					actualline++;
				}else {
					lines.set(actualline, line);
					actualline++;
				}
			}
			for(int i = 0; i < lines.size(); i++) {
				System.out.println(i+" "+(i == actualline?"=>":": ")+lines.get(i));
			}
			System.out.print("-----------------------------"+System.lineSeparator()+actualline+"> ");
		}
		sc.close();
	}
	
}
