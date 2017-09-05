// Brian Huang

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
public class pingIP 
{
	public static void runPing(String command) 	//Ping user specified IP address
	{
		try 
		{
			Process p = Runtime.getRuntime().exec(command);	//accepts a command and returns according to command received.
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String str = "";
			while ((str = input.readLine()) != null) 
			{
				System.out.println(str);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) 
	{
		String yes;
		Scanner scan = new Scanner(System.in);
		do
		{	
			String ip = null, contInput = "";
			int numPing = 4;
			boolean pingTillStop = false;
			do
			{
				System.out.println("Please enter an IP address.");
				ip = scan.nextLine();
			} while (ip.trim().isEmpty());
			
			System.out.println("Do you want to ping host continuously? Enter yes or no.");
			contInput = scan.nextLine();
			if (contInput.equalsIgnoreCase("yes"))
				pingTillStop = true;
			
			if (!pingTillStop)
			{
				System.out.println("How many times do you want to ping?");
				try 
				{
					numPing = scan.nextInt();
					scan.nextLine();
				}
				catch (Exception e)
				{
					System.out.println(e + "\nIP will be pinged the default number: 4 times.");
				}
				runPing("ping " + ip + " -n " + numPing);
			}
			if (pingTillStop)
			{
				System.out.println("Pinging host continuously. Terminate to stop.");
				runPing("ping " + ip + " -t");
			}
			
			System.out.println("Ping another address?");
			yes = scan.nextLine();
		} while (yes.equalsIgnoreCase("yes"));
		System.out.println("Have a great day!");
		scan.close();
		
	}
}

