package sml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.lang.reflect.Constructor;

/*
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
public class Translator {

	// word + line is the part of the current line that's not yet processed
	// word has no whitespace
	// If word and line are not empty, line begins with whitespace
	private String line = "";
	private Labels labels; // The labels of the program being translated
	private ArrayList<Instruction> program; // The program to be created
	private String fileName; // source file of SML code

	private static final String SRC = "src";

	public Translator(String fileName) {
		this.fileName = SRC + "/sml/" + fileName;
	}

	// translate the small program in the file into lab (the labels) and
	// prog (the program)
	// return "no errors were detected"
	public boolean readAndTranslate(Labels lab, ArrayList<Instruction> prog) {

		try (Scanner sc = new Scanner(new File(fileName))) {
			// Scanner attached to the file chosen by the user
			labels = lab;
			labels.reset();
			program = prog;
			program.clear();

			try {
				line = sc.nextLine();
			} catch (NoSuchElementException ioE) {
				return false;
			}

			// Each iteration processes line and reads the next line into line
			while (line != null) {
				// Store the label in label
				String label = scan();

				if (label.length() > 0) {
					Instruction ins = getInstruction(label);
					if (ins != null) {
						labels.addLabel(label);
						program.add(ins);
					}
				}

				try {
					line = sc.nextLine();
				} catch (NoSuchElementException ioE) {
					return false;
				}
			}
		} catch (IOException ioE) {
			System.out.println("File: IO error " + ioE.getMessage());
			return false;
		}
		return true;
	}

	// line should consist of an MML instruction, with its label already
	// removed. Translate line into an instruction with label label
	// and return the instruction
	public Instruction getInstruction(String label) {
		int s1; // Possible operands of the instruction
		int s2;
		String s3;
		int r;
		//int x;

		if (line.equals(""))
			return null;

		String ins = scan();
		switch (ins) {
		case "add":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
		    //return new AddInstruction(label, r, s1, s2);
		//using reflection	
		try{
		Class<?> c = Class.forName("sml.AddInstruction");
		
		Constructor<?> cons = c.getConstructor(String.class, int.class, int.class, int.class);
	     
		AddInstruction a = (AddInstruction) cons.newInstance(new Object[] {label, r, s1, s2});
		return a;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		case "lin":
			r = scanInt();
			s1 = scanInt();
			//return new LinInstruction(label, r, s1);
		//using reflection
		try{	
		Class<?> c2 = Class.forName("sml.LinInstruction");
				
		Constructor<?> cons2 = c2.getConstructor(String.class, int.class, int.class);
				
		LinInstruction b = (LinInstruction) cons2.newInstance(new Object[] {label, r, s1});
		return b;
				
		}catch(Exception e){
			e.printStackTrace();
		}	
			
		case "sub":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			//return new SubInstruction(label, r, s1, s2);	
		//using reflection
		try{
		Class<?> c3 = Class.forName("sml.SubInstruction");
				
		Constructor<?> cons3 = c3.getConstructor(String.class, int.class, int.class, int.class);
			     
		SubInstruction c = (SubInstruction) cons3.newInstance(new Object[] {label, r, s1, s2});
		return c;
				
		}catch(Exception e){
			e.printStackTrace();
		}	
		case "mul":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			//return new MulInstruction(label, r, s1, s2);		
		//using reflection
		try{	
		Class<?> c4 = Class.forName("sml.MulInstruction");
						
		Constructor<?> cons4 = c4.getConstructor(String.class, int.class, int.class, int.class);
					     
		MulInstruction d = (MulInstruction) cons4.newInstance(new Object[] {label, r, s1, s2});
		return d;
						
		}catch(Exception e){
			e.printStackTrace();
		}	
		case "div":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			//return new DivInstruction(label, r, s1, s2);
		//using reflection	
	    try{
		Class<?> c5 = Class.forName("sml.DivInstruction");
								
		Constructor<?> cons5 = c5.getConstructor(String.class, int.class, int.class, int.class);
							     
		DivInstruction e = (DivInstruction) cons5.newInstance(new Object[] {label, r, s1, s2});
		return e;
								
		}catch(Exception e){
			e.printStackTrace();
		}
		case "out":
			s1 = scanInt();
			//return new OutInstruction(label, s1);
	    //using reflection	
		try{	
		Class<?> c6 = Class.forName("sml.OutInstruction");
									
		Constructor<?> cons6 = c6.getConstructor(String.class, int.class);
								     
		OutInstruction f = (OutInstruction) cons6.newInstance(new Object[] {label, s1});
		return f;
									
		}catch(Exception e){
			e.printStackTrace();
		}	
			
		case "bnz":
			s1 = scanInt();
			s3 = scanString();
			//return new BnzInstruction(label, s1, s3);			
		//using reflection	
		try{
		Class<?> c7 = Class.forName("sml.BnzInstruction");
										
		Constructor<?> cons7 = c7.getConstructor(String.class, int.class, String.class);
									     
	    BnzInstruction g = (BnzInstruction) cons7.newInstance(new Object[] {label, s1, s3});
		return g;
										
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		// You will have to write code here for the other instructions.
		}
		return null;
	}

	/*
	 * Return the first word of line and remove it from line. If there is no
	 * word, return ""
	 */
	private String scan() {
		line = line.trim();
		if (line.length() == 0)
			return "";

		int i = 0;
		while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
			i = i + 1;
		}
		String word = line.substring(0, i);
		line = line.substring(i);
		return word;
	}

	// Return the first word of line as an integer. If there is
	// any error, return the maximum int
	private int scanInt() {
		String word = scan();
		if (word.length() == 0) {
			return Integer.MAX_VALUE;
		}

		try {
			return Integer.parseInt(word);
		} catch (NumberFormatException e) {
			return Integer.MAX_VALUE;
		}
	}
	
	private String scanString(){
	String word2 = scan();
		return word2;
	}
	
}