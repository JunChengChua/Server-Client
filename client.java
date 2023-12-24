import java.net.*;
import java.io.*;

public class Client
{
	private Socket socket = null;
	private BufferedReader reader = null;
	private DataOutputStream out = null;

	public Client(String address, int port)
	{
		try
		{
			socket = new Socket(address, port);
			System.out.println("Connected to socket");

			reader = new BufferedReader(new InputStreamReader(System.in));
			out = new DataOutputStream(socket.getOutputStream());
		}
		catch(UnknownHostException u)
		{
			System.out.println(u);
			return;
		}
		catch(IOException e)
		{
			System.out.println(e);
			return;
		}
		String line = "";

		while(!line.equals("end"))
		{
			try
			{
				line = reader.readLine();
				out.writeUTF(line);
			}
			catch(IOException i)
			{
				System.out.println(i);
			}
		}
		try
		{
			reader.close();
			out.close();
			socket.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}
	
	public static void main(String[] args)
	{
		Client client = new Client("127.0.0.1",5000);
	}
}
