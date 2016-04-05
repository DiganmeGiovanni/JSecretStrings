
package org.jsecretstring.frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jsecretstrings.core.Crypter;

/**
 *
 * @author giovanni
 */
public class FXClient extends Application {
    private TextField inputTF;
    private TextField outputTF;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("jSecretStrings");
        
        primaryStage.setScene(this.constructUI());
        primaryStage.show();
    }
    
    private Scene constructUI() {
        VBox mainContainer = new VBox(5);
        mainContainer.setPadding(new Insets(25));
        
        // Input components
        Label inputLB = new Label("Input (You can type multiple words)");
        inputLB.setPadding(new Insets(5, 0, 0, 0));
        inputTF = new TextField();
        inputTF.setMinWidth(450);
        
        // Output components
        Label outputLB = new Label("Output");
        outputLB.setPadding(new Insets(15, 0, 0, 0));
        outputTF = new TextField();
        outputTF.setMinWidth(450);
        
        // Buttons bar
        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setPadding(new Insets(15, 0, 0, 0));
        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        
        // Encrypt/Decrypt buttons
        Button encBTN = new Button("Encrypt");
        encBTN.setOnAction(e -> { this.onEncryptAction(); });
        Button decBTN = new Button("Decrypt");
        decBTN.setOnAction(e -> { this.onDecryptAction(); });
        
        buttonsContainer.getChildren().addAll(encBTN, decBTN);
        
        // Assembly all components
        mainContainer.getChildren().addAll(
                inputLB,
                inputTF,
                outputLB,
                outputTF,
                buttonsContainer
        );
        
        Scene scene = new Scene(mainContainer);
        return scene;
    }
    
    private void onDecryptAction() {
        String inputText = inputTF.getText();
        if (inputText.trim().length() > 0) {
            
            String dlgTitle = "Type the secret word";
            String dlgHeader= "Secret word to decrypt your message";
            String dlgMsg   = "Secret word: ";
            String sWord = this.showInputDialog(dlgTitle, dlgHeader, dlgMsg);
            
            ArrayList<String> decryptedWords = 
                            Crypter.customDecrypt(inputText, sWord);
            outputTF.setText(String.join(" ", decryptedWords));
            inputTF.setText("");
        }
        else {
            String header = "Type something to decrypt";
            String msg = "You must type a message to decrypt ...";
            
            this.showErrorDialog("Error", header, msg);
        }
    }
    
    private void onEncryptAction() {
        String inputText = inputTF.getText();
        if (inputText.trim().length() > 0) {
            List<String> wordsToEncrypt = Arrays.asList(inputText.split(" "));
            
            String dlgTitle = "Type a secret word";
            String dlgHeader= "Secret word to decrypt your message";
            String dlgMsg   = "Secret word: ";
            String sWord = this.showInputDialog(dlgTitle, dlgHeader, dlgMsg);
            
            String encWords = Crypter.customEncrypt(wordsToEncrypt, sWord);
            inputTF.setText("");
            outputTF.setText(encWords);
            outputTF.selectAll();
        }
        else {
            String header = "Type something to encrypt";
            String msg = "You must type a few words to encrypt ...";
            
            this.showErrorDialog("Error", header, msg);
        }
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Generic util methods
    
    private void showErrorDialog(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        
        alert.showAndWait();
    }
    
    private String showInputDialog(String title, String header, String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(message);
        
        Optional result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get().toString();
        }
        
        return "default";
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
        launch(args);
    }

}
