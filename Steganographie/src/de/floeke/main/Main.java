package de.floeke.main;

import de.floeke.gui.GraphicUserInterface;
import de.floeke.operations.FileWorker;

public class Main {
	
	public static void main(String args[])
	{
		if(args.length == 0) 
		{
			GraphicUserInterface gui = new GraphicUserInterface();
			gui.setVisible(true);
		}	
		else {
			
			//Args0: mode Args1: Filepath, Args2: Password, Rest: Nachricht
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
				System.out.printf("Time needed: %1.5fs", ((float)(System.nanoTime()-startTime)/1000000000));
			}
		}
	}
}
