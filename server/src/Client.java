import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread {

    DataInputStream dataIn;
    DataOutputStream dataOut;
    Socket socket;
    ArrayList<Client>getSocket;


    public Client(Socket socket, ArrayList<Client> getSocket) throws IOException {
        this.socket = socket;
        this.getSocket = getSocket;
        this.dataIn=new DataInputStream(this.socket.getInputStream());
        this.dataOut=new DataOutputStream(this.socket.getOutputStream());
    }

    @Override
    public void run(){

        while (true){
            try {
                String getInData=dataIn.readUTF();
                String  getName=dataIn.readUTF();

                System.out.println(getInData);
                System.out.println(getName);

                for (Client client : getSocket) {
                    if(client.socket != this.socket) {
                        client.dataOut.writeUTF(getInData);
                        client.dataOut.writeUTF(getName);
                        System.out.println(getSocket);
                        dataOut.flush();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
