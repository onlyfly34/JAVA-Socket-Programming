import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Simple_TCP_Socket_Server_4 {
	private ServerSocket ss;
	private Socket cs;
	private static final int port = 57968;
	private static final int backlog = 10;
	private static final String addr = "127.0.0.1";

	public Simple_TCP_Socket_Server_4(){
		build_ServerSocket_and_list_ServerInfo();
		listenClient();
		close_socket();
	}
	
	private void build_ServerSocket_and_list_ServerInfo(){
		try{
			ss = new ServerSocket(port, backlog, InetAddress.getByName(addr)); //build socket
			// Get local information
			// ====================================================================================
			System.out.println("Local Information:");
			System.out.println("  Local Host: " + InetAddress.getLocalHost());
			System.out.println("  Host  Name: " + InetAddress.getLocalHost().getHostName());
			System.out.println("  IP address: " + InetAddress.getLocalHost().getHostAddress());
			System.out.println();
			// ====================================================================================
			
			// Get server socket information
			// ====================================================================================
			System.out.println("Server Socket Information:");
			System.out.println("  IP Address: " + ss.getInetAddress().getHostAddress());
			System.out.println("  Port #    : " + ss.getLocalPort());
			System.out.println();
			// ====================================================================================
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	private void listenClient(){
		System.out.println("Listen for clients...");
		while(true){
			try{
				cs = ss.accept();
				Thread thread = new Thread(new listenThread(cs));
				thread.start();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	private void close_socket(){
		try{
			cs.close();
			ss.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("I'm Server!!!!");
		new Simple_TCP_Socket_Server_4();
	}
}

class listenThread implements Runnable {
	private Socket cs;
	private Scanner scanner;
	
	public listenThread(Socket clientSocket) {
		cs = clientSocket;
	}
	
	public void run() {
		String inData;
		String outData;
		DataInputStream in;
		DataOutputStream out;

		try {
			if(cs.isConnected()) {
				System.out.println("Connection from client IP: " + 
						cs.getInetAddress().getHostAddress() + "\n");
				in = new DataInputStream(cs.getInputStream());
				out = new DataOutputStream(cs.getOutputStream());

				while(true){
					inData = in.readUTF();
					System.out.println("Message from client "+cs.getInetAddress().getHostAddress()+" :"+inData);
					System.out.print("Please type messages to client：(Enter exit to kick the client)");
					scanner = new Scanner(System.in);
					outData = scanner.nextLine();
					if(outData.equals("exit")){
						break;
					}
					out.writeUTF(outData);
				}
				
				in.close();
				out.close();
				cs.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}