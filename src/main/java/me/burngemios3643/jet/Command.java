package me.burngemios3643.jet;

import java.util.List;
import java.util.Scanner;

public abstract class Command implements Runnable{
	
	private List<String> lines;
	private String line;
	private int actualLine;
	private Scanner sc;
	
	public Scanner getScanner() {
		return sc;
	}

	public void setScanner(Scanner sc) {
		this.sc = sc;
	}

	public List<String> getLines() {
		return lines;
	}
	
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	
	public void setLine(String line) {
		this.line = line;
	}
	
	public String getLine() {
		return line;
	}
	
	public int getActualLine() {
		return actualLine;
	}
	
	public void setActualLine(int actualLine) {
		this.actualLine = actualLine;
	}
	
}
