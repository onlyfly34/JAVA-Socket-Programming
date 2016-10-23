import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Simple_Socket_Client_1{
	private Socket cs;
	private static final int port = 57968;
	private static final String addr = "127.0.0.1";
	
	public Simple_Socket_Client_1(){
		build_client_socket();
		close_socket();
	}

	private void build_client_socket(){
		try{
			cs = new Socket(InetAddress.getByName(addr), port);

			System.out.println("Connection to server IP: " + 
					cs.getInetAddress().getHostAddress());
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	private void close_socket(){
		try{
			cs.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		System.out.println("\nI'm Client!!!!");
		new Simple_Socket_Client_1();
	}
}
