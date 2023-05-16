package Client;
// Liang Java - BigBlueBook
// pg 1145-1146 Listing31.2

import java.io.*;
import java.net.*;
// import java.util.Date;
import javafx.application.Application;
// import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/* **** NOTE:  
 *           RUN:  Right Click and select Run JavaFX Apllication and
 *                    NOT main(String args[])
 */




// ================= CLIENT =============

// Liang Java - BigBlueBook
// pg 1145-1146 Listing31.2

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/* **** NOTE:  
 *           RUN:  Right Click and select "Run JavaFX Apllication" and
 *                    NOT main(String args[])
 */

public class Client extends Application {
    // IO streams
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    public static void main(String[] args) {
            Application.launch(args);
            System.exit(0);
    }
       
       
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
 // PART ONE:        
        // Panel to hold the label and text field
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5, 5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Enter a radius: "));

        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);

        BorderPane mainPane = new BorderPane();
        // Text area to display contents
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);

        // Create a scene and place it in the stage
        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage


// PART TWO:        
        tf.setOnAction(e -> {
                try {
                    // Get the radius from the text field
                    double radius = Double.parseDouble(tf.getText().trim());                  

                    // Send the radius to the server
                    toServer.writeDouble(radius);
                    toServer.flush();  // 'flush()' every time to send the info thru
 
                    // Get area from the server
                    double area = fromServer.readDouble();

                    // Display to the text area
                    ta.appendText("Radius is " + radius + "\n");
                    ta.appendText("Area received from the server is "
                        + area + '\n');
                }
                catch (IOException ex) {
                    System.err.println(ex);
                }
            });      
       
       
// PART THREE:        
        try {
            // Create a socket to connect to the server
            // This is MY IP address on the MY (Mr Marques') School computer:
            //            Socket socket = new Socket("172.16.6.152", 8000);
            //     Socket socket = new Socket("130.254.204.36", 8000);
            //     Socket socket = new Socket("drake.Armstrong.edu", 8000);            
            Socket socket = new Socket("localhost", 8000);  
   
            // Pg 1147
            System.out.println("Client's local port for communication: " + socket.getLocalPort());
                   
            // Pg 1147 "InetAddress Class"
            InetAddress inetAddressServer = socket.getInetAddress();      
            System.out.println("Inside CLIENT after new Socket(\"localhost\", 8000)/socket.getInetAddress(): ");    
            System.out.println("client socket's host name = " + inetAddressServer.getHostName());
            System.out.println("client socket's IP Address = " + inetAddressServer.getHostAddress());            

            // FIRST: Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());        
            // SECOND: Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());            
           
        }
        catch (IOException ex) {
            ta.appendText(ex.toString() + '\n');
        }
         
       
    }
}
