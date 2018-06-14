import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class scanner
{
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
		loadFile(".\\testScanner.txt");
		
	}
	
	
	public static void loadFile(String path)
	{
		boolean txt = path.endsWith(".txt");
		if(!txt) {
			JOptionPane.showMessageDialog(null, "Please choose a .txt file.");
		}
		else
		{
			try
			{
				Scanner fileIn;
				fileIn = new Scanner(new File(path));
				// first there needs to be given the number of vertices
				if(fileIn.hasNextInt())
				{
					int NumberOfV = fileIn.nextInt();
					System.out.println("number of vertices: "+NumberOfV);
				}
				else
				{
					System.out.println("error");
				}
				// next information is the number of edges
				if(fileIn.hasNextInt())
				{
					int NumberOfE = fileIn.nextInt();
					System.out.println("number of edges: "+NumberOfE);
				}
				else
				{
					System.out.println("error2");
				}
				
				fileIn.nextLine();
				// read in the concrete edges
				while(fileIn.hasNext())
				{
					
					String line = fileIn.nextLine();
					System.out.println(line);
					// for instance 2 3
					String patternType1 = "(.*?)(\\d+)\\s+(\\d+)";
					// for instance 2 3 5 with 5 as weight
					String patternType2 = "(.*?)(\\d+)\\s+(\\d+)+\\s+(\\d+)";
					// for instance (2,3) or (2, 3) or (2,  3) or (2 , 3), or ( 2 ,3) etc.
					String patternType3 = "(.*?)(\\s+)?(\\d+)(\\s+)?,(\\s+)?(\\d+)(\\s+)?\\)";
					// like patterType3 but with 3 numbers
					String patternType4 = "(.*?)(\\s+)?(\\d+)(\\s+)?,(\\s+)?(\\d+)(\\s+)?,(\\s+)?(\\d+)(\\s+)?\\)";
					
					
					Pattern pattern1 = Pattern.compile(patternType1);
					Pattern pattern2 = Pattern.compile(patternType2);
					Pattern pattern3 = Pattern.compile(patternType3);
					Pattern pattern4 = Pattern.compile(patternType4);

			        Matcher matcher1 = pattern1.matcher(line);
			        Matcher matcher2 = pattern2.matcher(line);
			        Matcher matcher3 = pattern3.matcher(line);
			        Matcher matcher4 = pattern4.matcher(line);
			        
			        boolean matchesType1 = matcher1.matches();
			        boolean matchesType2 = matcher2.matches();
			        boolean matchesType3 = matcher3.matches();
			        boolean matchesType4 = matcher4.matches();
			        
			        System.out.print("matchesType1: "+matchesType1+ " , ");
			        System.out.print("matchesType2: "+matchesType2+ " , ");
			        System.out.print("matchesType3: "+matchesType3+ " , ");
			        System.out.println("matchesType4: "+matchesType4);
			        
			        if(matchesType1 & !matchesType2)
			        {
			        	String test = matcher1.group(2);
			        	System.out.println("first vertex: "+Integer.parseInt(test));
			        	String test2 = matcher1.group(3);
			        	System.out.println("second vertex: "+Integer.parseInt(test2));
			        	
			        }
			        
			        if(matchesType2)
			        {
			        	String test = matcher2.group(2);
			        	System.out.println("first vertex: "+Integer.parseInt(test));
			        	String test2 = matcher2.group(3);
			        	System.out.println("second vertex: "+Integer.parseInt(test2));
			        	String test3 = matcher2.group(4);
			        	System.out.println("weight: "+Integer.parseInt(test3));
			        	
			        }
			        
			        if(matchesType3 & !matchesType4)
			        {
			        	String test = matcher3.group(3);
			        	System.out.println("first vertex: "+Integer.parseInt(test));
			        	String test2 = matcher3.group(6);
			        	System.out.println("second vertex: "+Integer.parseInt(test2));
			        	
			        }
			        
			        if(matchesType4)
			        {
			        	String test = matcher4.group(3);
			        	System.out.println("first vertex: "+Integer.parseInt(test));
			        	String test2 = matcher4.group(6);
			        	System.out.println("second vertex: "+Integer.parseInt(test2));
			        	String test3 = matcher4.group(9);
			        	System.out.println("weight: "+Integer.parseInt(test3));
			 
			        	
			        }
			        
			        if(matchesType3 || matchesType1)
			        {
			        	System.out.println("Found a type that matched");
			        	System.out.println("");
			        }
				}
				fileIn.close();
			}
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
}

