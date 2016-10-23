import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Simple_TCP_Socket_Server_2 {
	private ServerSocket ss;
	private Socket cs;
	private static final int port = 57968;
	private static final int backlog = 10;
	private static final String addr = "127.0.0.1";

	public Simple_TCP_Socket_Server_2(){
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
				System.out.println("Client from IP："+cs.getInetAddress());
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
		new Simple_TCP_Socket_Server_2();
	}
}
