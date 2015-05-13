package com.main;

import com.gui.GraphicUserInterface;
import com.operations.FileWorker;

public class Main {
	
	public static void main(String args[])
	{
		if(args.length == 0) 
			noArgs();
		else {
			withArgs(args);
		}
	}
	
	public static void noArgs()
	{
		GraphicUserInterface gui = new GraphicUserInterface();
	}

	//Args0: mode Args1: Filepath, Args2: Password, Rest: Nachricht
	public static void withArgs(String args[])
	{
		long startTime = System.nanoTime();
		String mode = args[0];
		String filepath = args[1];
		String password = args[2];
		
		FileWorker fw = new FileWorker(filepath);
		
		if(mode.equals("read"))
		{
			System.out.println(fw.getMessage(password));
			System.out.printf("Time needed: %1.5fs", ((float)(System.nanoTime()-startTime)/1000000000));
		}
		
		else if(mode.equals("write"))
		{
			StringBuilder message = new StringBuilder();
			
			for(String a: args)
			{
				if(!a.equals(password) && !a.equals(filepath) && !a.equals("write"))
				{
					message.append(a+" ");
				}
			}	
			
			fw.toFile(message.toString(), password);
			System.out.println("Time needed: " + ((float)(System.nanoTime()-startTime)/1000000000)+"s");
		}
		
	}
}
