/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Created at: 9:09 AM - 29/10/2024
 * User: lam-nguyen
 */

package main.java.ui.model;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.PrintWriter;

public record IOService(BufferedReader input, PrintWriter output) {

    public void sendMessage(String message) {
        output.println(message);
    }

    public String readMessage() {
        try {
            return input.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }
}
