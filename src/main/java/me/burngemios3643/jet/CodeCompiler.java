package me.burngemios3643.jet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeCompiler {

	private List<String> lines;
	
	private File tmpjava;
	private File tmpclass;
	
	public CodeCompiler(List<String> lines) throws IOException {
		this.lines = lines;
		tmpjava = File.createTempFile("jpp", ".java");
		tmpjava.deleteOnExit();
		sortCode();
		writeToFile();
	}
	
	private List<String> imprts;
	private List<String> code;
	
	public void sortCode() {
		imprts = new ArrayList<>();
		code = new ArrayList<>();
		for(String l:lines) {
			if(l.matches("[\\s \t]*import [A-Za-z0-9\\._\\*;\\s \t]+;"))imprts.add(l);
			else code.add(l);
		}
	}
	
	public void writeToFile() throws IOException {
		System.out.println("temporary saving...");
		FileWriter fw = new FileWriter(tmpjava);
		System.out.println("temporary saving in file ("+tmpjava.getAbsolutePath()+")...");
		
		for(String l:imprts) {
			fw.write(l+System.lineSeparator());
			fw.flush();
		}
		fw.write("public class "+tmpjava.getName().substring(0, tmpjava.getName().lastIndexOf("."))+" {"+System.lineSeparator());
		fw.flush();
		fw.write("public static void main(String... args){"+System.lineSeparator());
		fw.flush();
		for(String l:code) {
			fw.write(l+System.lineSeparator());
			fw.flush();
		}
		fw.write("}"+System.lineSeparator());
		fw.flush();
		fw.write("}"+System.lineSeparator());
		fw.flush();

		fw.close();
		System.out.println("temporary save completed!");
	}
	
	public void compile() throws IOException {
		System.out.println("Compiling...");
		
		if(tmpclass != null && tmpclass.exists())tmpclass.delete();
		
		Runtime rt = Runtime.getRuntime();
	    String[] commands = {"javac", "-d", tmpjava.getParentFile().getAbsolutePath(), tmpjava.getAbsolutePath()};
	    Process proc = rt.exec(commands);

	    BufferedReader stdInput = new BufferedReader(new 
	         InputStreamReader(proc.getInputStream()));

	    BufferedReader stdError = new BufferedReader(new 
	         InputStreamReader(proc.getErrorStream()));

	    String s;
	    while ((s = stdInput.readLine()) != null) {
	        System.out.println(s);
	    }

	    while ((s = stdError.readLine()) != null) {
	        System.err.println(s);
	    }
	    
	    System.out.println("Compiling - validating...");
	    
	    tmpclass = new File(tmpjava.getParentFile().getAbsolutePath(), tmpjava.getName().substring(0, tmpjava.getName().lastIndexOf("."))+".class");
	    
	    if(tmpclass == null || !tmpclass.exists() || !tmpclass.isFile() || !tmpclass.canRead())throw new IOException("tmpclass ("+tmpjava.getName().substring(0, tmpjava.getName().lastIndexOf("."))+") not found/invalid...");
	    
	    tmpclass.deleteOnExit();
	    
	    System.out.println("Compiling - completed!");
	    
	}
	
	@SuppressWarnings("deprecation")
	public void run(String args) throws IOException {
		Runtime rt = Runtime.getRuntime();
		final Process process = rt.exec("java -cp .;"+tmpclass.getParentFile().getAbsolutePath()+" "+tmpclass.getName().substring(0, tmpclass.getName().lastIndexOf("."))+" "+args);
	    
	    System.out.println("======================");
	    System.out.println("  STARTING EXECUTION  ");
	    System.out.println("======================");
	    
	    BufferedReader stdInput = new BufferedReader(new 
		         InputStreamReader(process.getInputStream()));

	    Thread t = new Thread(()->{
	    	OutputStream in = process.getOutputStream();
	    	Scanner sc = new Scanner(System.in);
	    	String line;
	    	while((line = sc.nextLine()) != null) {
	    		try {
	    			in.write((line+System.lineSeparator()).getBytes());
	    			in.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    	sc.close();
	    });
	    t.start();
	    
	    String s;
	    while ((s = stdInput.readLine()) != null) {
	        System.out.println(s);
	    }
	    
	    t.stop();
	    
	    System.out.println("______________________");
	    System.out.println("potential errors:");
	    

	    BufferedReader stdError = new BufferedReader(new 
	         InputStreamReader(process.getErrorStream()));
	    
	    while ((s = stdError.readLine()) != null) {
	        System.err.println(s);
	    }
	    
	    System.out.println("======================");
	    System.out.println("   END OF EXECUTION   ");
	    System.out.println("======================");
	}
	
}
