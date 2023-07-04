import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;

public class Client1Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group client;

    @FXML
    private TextField ClientField;

    @FXML
    private ImageView sentButton;

    @FXML
    private Label clientLabel;

    @FXML
    private Group join;

    @FXML
    private TextField joinText;

    @FXML
    private Button joinBtn;

    @FXML
    private VBox ClientArea;

    @FXML
    private FlowPane emojiPane;

    @FXML
    private ScrollPane scolePane;


    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String getMassege = "";
    String getName = "";

    private static final String[] emojis = {"\uD83D\uDE00", "\uD83D\uDE01", "\uD83D\uDE02", "\uD83D\uDE03", "\uD83D\uDE04", "\uD83D\uDC4D", "\uD83D\uDE04", "\uD83D\uDE03", "\uD83D\uDE00", "\uD83D\uDE01", "\u263A", "\uD83D\uDE00", "\uD83D\uDE01", "\uD83D\uDE02", "\uD83D\uDE03",
            "\uD83D\uDE04", "\uD83D\uDE05", "\uD83D\uDE06", "\uD83D\uDE07", "\uD83D\uDE08",
            "\uD83D\uDE09", "\uD83D\uDE0A", "\uD83D\uDE0B", "\uD83D\uDE0C", "\uD83D\uDE0D",
            "\uD83D\uDE0E", "\uD83D\uDE0F", "\uD83D\uDE10", "\uD83D\uDE11", "\uD83D\uDE12",
            "\uD83D\uDE13", "\uD83D\uDE14", "\uD83D\uDE15", "\uD83D\uDE16", "\uD83D\uDE17",
            "\uD83D\uDE18", "\uD83D\uDE19", "\uD83D\uDE1A", "\uD83D\uDE1B", "\uD83D\uDE1C",
            "\uD83D\uDE1D", "\uD83D\uDE1E", "\uD83D\uDE1F", "\uD83D\uDE20", "\uD83D\uDE21",
            "\uD83D\uDE22", "\uD83D\uDE23", "\uD83D\uDE24", "\uD83D\uDE25", "\uD83D\uDE26",
            "\uD83D\uDE27", "\uD83D\uDE28", "\uD83D\uDE29", "\uD83D\uDE2A", "\uD83D\uDE2B",
            "\uD83D\uDE2C", "\uD83D\uDE2D", "\uD83D\uDE2E", "\uD83D\uDE2F", "\uD83D\uDE30",
            "\uD83D\uDE31", "\uD83D\uDE32", "\uD83D\uDE33", "\uD83D\uDE34", "\uD83D\uDE35",
            "\uD83D\uDE36", "\uD83D\uDE37", "\uD83D\uDE38", "\uD83D\uDE39", "\uD83D\uDE3A",
            "\uD83D\uDE3B", "\uD83D\uDE3C", "\uD83D\uDE3D", "\uD83D\uDE3E", "\uD83D\uDE3F",
            "\uD83D\uDE40", "\uD83D\uDE41", "\uD83D\uDE42", "\uD83D\uDE43", "\uD83D\uDE44",
            "\uD83D\uDE45", "\uD83D\uDE46", "\uD83D\uDE47", "\uD83D\uDE48", "\uD83D\uDE49",
            "\uD83D\uDE4A", "\uD83D\uDE4B", "\uD83D\uDE4C", "\uD83D\uDE4D", "\uD83D\uDE4E", "\uD83D\uDE4F"};


    public void sentButtonOnclick(MouseEvent mouseEvent) throws IOException {

        String sentMsg = ClientField.getText();
        String sentName = clientLabel.getText();
        dataOutputStream.writeUTF(sentMsg);
        dataOutputStream.writeUTF(sentName);
        dataOutputStream.flush();


        Platform.runLater(() -> {
            Label setName = new Label(sentMsg + "\n");
            setName.setStyle("-fx-background-color: #00B3D4FF;-fx-background-radius: 5;-fx-text-fill: white");
            setName.setPadding(new Insets(5, 15, 5, 15));
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_RIGHT);
            //     hBox.setPadding(new Insets(5,15,5,15));
            hBox.getChildren().add(setName);
            ClientArea.getChildren().add(hBox);

        });

        ClientField.setText(null);

    }

    @FXML
    void initialize() {
        ClientArea.setPadding(new Insets(10, 10, 10, 10));
        ClientArea.setSpacing(10);
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3001);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (true) {
                    getMassege = dataInputStream.readUTF();
                    getName = dataInputStream.readUTF();
                    System.out.println(getMassege);
                    System.out.println(getName);

                    if (getMassege.startsWith("file:")) {

                        ImageView imageView = new ImageView(new Image(getMassege));
                        HBox hBox = new HBox();
                        hBox.getChildren().add(imageView);
                        hBox.setPadding(new Insets(10));
                        imageView.setFitWidth(192);
                        imageView.setPreserveRatio(true);

                        Platform.runLater(() -> {
                            Label set = new Label(getName + ":" + "\n");
                            ClientArea.getChildren().addAll(set, imageView);
                        });
                    } else {
                        Platform.runLater(() -> {
                            //
                            Label getMsgLabel = new Label(getName + ": " + getMassege + "\n");
                            getMsgLabel.setStyle("-fx-background-color: white;-fx-background-radius: 5;-fx-text-fill: black");
                            getMsgLabel.setPadding(new Insets(5, 15, 5, 15));
                            ClientArea.getChildren().addAll(getMsgLabel);

                        });
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();


        // Add emoji labels to the FlowPane
        for (String emoji : emojis) {
            Label label = new Label(emoji);
            label.setStyle("-fx-font-size: 20;"); // Adjust font size if needed
            label.setPadding(new Insets(8));
            emojiPane.getChildren().add(label);

            label.setOnMouseClicked(event -> {
                String unicode = emoji;
                ClientField.appendText(emoji);
                ClientField.requestFocus();
                ClientField.positionCaret(ClientField.getText().length());
            });
        }


        assert client != null : "fx:id=\"client\" was not injected: check your FXML file 'client1.fxml'.";
        assert ClientArea != null : "fx:id=\"ClientArea\" was not injected: check your FXML file 'client1.fxml'.";
        assert ClientField != null : "fx:id=\"ClientField\" was not injected: check your FXML file 'client1.fxml'.";
        assert sentButton != null : "fx:id=\"sentButton\" was not injected: check your FXML file 'client1.fxml'.";
        assert clientLabel != null : "fx:id=\"clientLabel\" was not injected: check your FXML file 'client1.fxml'.";
        assert join != null : "fx:id=\"join\" was not injected: check your FXML file 'client1.fxml'.";
        assert joinText != null : "fx:id=\"joinText\" was not injected: check your FXML file 'client1.fxml'.";
        assert joinBtn != null : "fx:id=\"joinBtn\" was not injected: check your FXML file 'client1.fxml'.";


    }

    public void joinOnAction(ActionEvent event) {
        join.setVisible(false);
        client.setVisible(true);

        clientLabel.setText(joinText.getText());
    }

    File filePath;

    public void imageOnclick(MouseEvent mouseEvent) throws IOException {


        FileChooser fileChooser;
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = (fileChooser.showOpenDialog(stage));

        System.out.println(filePath+"@");
        if (filePath!= null) {
            System.out.println(filePath);

            dataOutputStream.writeUTF("file:" + filePath);
            dataOutputStream.writeUTF(clientLabel.getText());
            dataOutputStream.flush();

            ImageView imageView = new ImageView(new Image("file:" + filePath));
            imageView.setFitWidth(192);
            imageView.setPreserveRatio(true);

            Platform.runLater(() -> {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.BASELINE_RIGHT);
                hBox.getChildren().add(imageView);
                hBox.setPadding(new Insets(5));
                ClientArea.getChildren().add(hBox);
            });
        }
    }

    public void emojiOnClick(MouseEvent mouseEvent) {

        scolePane.setVisible(true);
        emojiPane.setVisible(true);
        emojiPane.setPadding(new Insets(10));

    }

    public void textClick(MouseEvent mouseEvent) {

        scolePane.setVisible(false);
        emojiPane.setVisible(false);
    }

}