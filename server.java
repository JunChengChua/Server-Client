import java.net.*;
import java.io.*;

public class Server
{
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	
	public Server(int port)
	{
		try
		{
			server = new ServerSocket(port);
			while (true)
			{
				System.out.println("Server started");
				server.setReuseAddress(true);
				socket = server.accept();
				System.out.println("Client reached for port: " + port);
				
				Thread clientThread = new Thread(new ClientHandler(socket));
				clientThread.start();
			}

		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}
	public static void main(String[] args)
	{
		Server server = new Server(5000);
	}
}

class ClientHandler implements Runnable {

	private Socket clientSocket;
	private DataInputStream in = null;
	private int clientPort;

	public ClientHandler(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		this.clientPort = clientSocket.getPort();
	}

	public void run()
	{
		try{
			in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			String line = "";
			
			System.out.println("User: " + clientPort + " has joined the chat");

			while (!line.equals("end"))
			{
				line = in.readUTF();
				System.out.println(clientPort + ": " + line);			
			}
			System.out.println("User: " + clientPort + " has left the chat");
	
			clientSocket.close();
			in.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
			return;
		}
	}
}
