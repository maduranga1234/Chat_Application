import com.sun.deploy.util.SessionState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class Server {

    DataInputStream dataIn;
    DataOutputStream dataOut;
    static Socket socket;
    static ArrayList<Client>getSocket=new ArrayList<>();


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3001);
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println(socket);
                Client client = new Client(socket, getSocket);
                getSocket.add(client);
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}

