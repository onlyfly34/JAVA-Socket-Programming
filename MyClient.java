import java.net.Socket;
import java.net.InetAddress;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

class MyClient{
	Socket cs;
	Scanner scanner;
	private static final int port = 57968;
	private static final String addr = "127.0.0.1";

	public MyClient(){
		build_client_socket();
		close_socket();
	}

	private void build_client_socket(){
		try{
			cs = new Socket(InetAddress.getByName(addr), port);
			System.out.println("Connection to IP："+
								cs.getInetAddress().getHostAddress());

			String inData = "";
			DataOutputStream out = new DataOutputStream(cs.getOutputStream());
			DataInputStream in = new DataInputStream(cs.getInputStream());

			System.out.print("Please type messages to server：");
			scanner = new Scanner(System.in);
			String outData = scanner.nextLine();
			out.writeUTF(outData);
			inData = in.readUTF();
			System.out.println("Message from server "+cs.getInetAddress().getHostAddress()+" :"+inData);

			in.close();
			out.close();
		}catch (IOException ioe){
			ioe.printStackTrace();
		}
	}

	private void close_socket(){
		try{
			cs.close();
		}catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
	public static void main(String[] args){
		System.out.println("\nI'm client!!!!");
		new MyClient();
	}
}