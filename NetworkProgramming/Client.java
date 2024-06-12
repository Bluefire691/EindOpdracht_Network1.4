import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Button;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.*;
import java.net.Socket;
import java.util.regex.Pattern;

public class Client extends Application {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private TextArea messageArea;
    private TextField messageInput;
    private String username;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat Client");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Button loginButton = new Button("Login");
        messageArea = new TextArea();
        messageArea.setEditable(false);
        messageInput = new TextField();
        messageInput.setDisable(true);
        Button sendButton = new Button("Send");

        root.getChildren().addAll(usernameLabel, usernameField, loginButton, messageArea, messageInput, sendButton);

        loginButton.setOnAction(event -> {
            username = usernameField.getText();
            if (Pattern.matches("^[a-zA-Z0-9_]{3,15}$", username)) {
                try {
                    connectToServer(); // Connecteer naar de server voordat de receiveMessages-methode wordt gestart
                    out.println(username);
                    messageInput.setDisable(false);
                    messageArea.appendText("Logged in as " + username + "\n");
                    new Thread(() -> receiveMessages()).start(); // Start de receiveMessages-methode in een nieuwe thread
                } catch (IOException e) {
                    e.printStackTrace();
                    messageArea.appendText("Failed to connect to server.\n");
                }
            } else {
                messageArea.appendText("Invalid username. Please try again.\n");
            }
        });

        sendButton.setOnAction(event -> {
            String message = messageInput.getText();
            if (!message.isEmpty()) {
                out.println(message);
                messageInput.clear();
            }
        });

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToServer() throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                final String receivedMessage = message;
                // Ensure the update to the messageArea is done on the JavaFX Application Thread
                Platform.runLater(() -> messageArea.appendText(receivedMessage + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (socket.isClosed()) {
                Platform.runLater(() -> messageArea.appendText("Connection to server lost.\n"));
            }
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}


