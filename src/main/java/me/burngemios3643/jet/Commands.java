package me.burngemios3643.jet;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public enum Commands {

	HELP(Main.PREFIX+"help", "show the list of commands and their usages", new Command() {
		@Override
		public void run() {
			System.out.println("================");
			System.out.println("  INFORMATIONS  ");
			System.out.println("================");
			for(int i = 0; i < Commands.values().length; i++) {
				Commands cmd = Commands.values()[i];
				System.out.println(" - \""+cmd.getPattern()+"\" "+cmd.getDescription());
			}
			System.out.println("================");
			PrintUtils.enterToContinue(getScanner());
		}
	}),
	CHANGE_LINE(Main.PREFIX+"[0-9]+", "change the selected line", new Command() {
		@Override
		public void run() {
			int wanted = Integer.parseInt(getLine().substring(Main.PREFIX.length()));
			setActualLine(wanted<0?0:wanted>getLines().size()?getLines().size():wanted);
		}
	}),
	INSERT(Main.PREFIX+"insert", "insert new line at selected", new Command() {
		@Override
		public void run() {
			if(getActualLine()<getLines().size())getLines().add(getActualLine(), "");
		}
	}),
	DELETE(Main.PREFIX+"delete", "delete selected line", new Command() {
		@Override
		public void run() {
			if(getActualLine()<getLines().size())getLines().remove(getActualLine());
		}
	}),
	CLEAR(Main.PREFIX+"clear", "clear the selected line", new Command() {
		@Override
		public void run() {
			if(getActualLine()<getLines().size())getLines().set(getActualLine(), "");
		}
	}),
	CLEAR_ALL(Main.PREFIX+"clearall", "clear the whole code", new Command() {
		@Override
		public void run() {
			setLines(new ArrayList<String>());
			setActualLine(0);
		}
	}),
	COPY(Main.PREFIX+"copy", "copy the code to the clipboard", new Command() {
		@Override
		public void run() {
			StringJoiner sj = new StringJoiner(System.lineSeparator());
			for(String s:getLines())sj.add(s);
			StringSelection stringSelection = new StringSelection(sj.toString());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}
	}),
	EXEC(Main.PREFIX+"exec([\\s ].*)*", "execute the code with optional arguments", new Command() {
		@Override
		public void run() {
			try {
				CodeCompiler cc = new CodeCompiler(getLines());
				cc.compile();
				cc.run(getLine().substring(Main.PREFIX.length()+4));
			} catch (IOException e) {
				e.printStackTrace();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			PrintUtils.enterToContinue(getScanner());
		}
	}),
	QUIT(Main.PREFIX+"quit", "exit the app", new Command() {
		@Override
		public void run() {
			System.exit(0);
		}
	});
	
	
	private String pattern;
	private String description;
	private Command run;
	
	private Commands(String pattern, String description, Command run) {
		this.pattern = pattern;
		this.description = description;
		this.run = run;
	}

	public void run(List<String> lines, String line, int actualLine, Scanner sc) {
		run.setLines(lines);
		run.setLine(line);
		run.setActualLine(actualLine);
		run.setScanner(sc);
		run.run();
	}
	
	public String getPattern() {
		return pattern;
	}

	public String getDescription() {
		return description;
	}
	
	public Command getExecuted() {
		return run;
	}
	
}
