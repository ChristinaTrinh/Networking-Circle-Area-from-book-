package Server;
// Liang Java - BigBlueBook
// pg 1143-1144 Listing31.1 - CircleArea

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;   
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


/* **** NOTE:  
 *           RUN:  Right Click and select Run JavaFX Apllication and 
 *                    NOT main(String args[])
 */ 

public class Server extends Application {

    public static void main(String[] args) {
        Application.launch(args);
        System.exit(0);
    }

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Text area for displaying contents
        TextArea ta = new TextArea();

        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        new Thread(() -> {
                try {
                    try (// Create a server socket
                        ServerSocket serversocket = new ServerSocket(8000)) {
                        Platform.runLater(() -> ta.appendText("Server started at " + new Date() + '\n'));

                        // Listen for a connection request
                        Socket socket = serversocket.accept();                   

                        // Create data input and output streams            
                        //  NOTE:  1st Create Output stream 
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                        //         2nd Create Input stream
                        DataInputStream input = new DataInputStream(socket.getInputStream());
               
                        while (true) {
                            // Receive radius from the client
                            double radius = input.readDouble();

                            // Compute area
                            double area = radius*Math.PI*radius;
                            
                            output.writeDouble(area);
                            // Send area back to the client
         // >>>>>>>>>>>>>>>>>>> YOUR CODE HERE <<<<<<<<<<<<<<<<<<<  

                            Platform.runLater(() -> {
                                    ta.appendText("Radius received from client: " + radius + '\n');
                                    ta.appendText("Area is: " + area + '\n');
                                });
                        }
                    }
                }
                catch(IOException ex) {
                    ex.printStackTrace();
                }
            }).start();
    }
}
