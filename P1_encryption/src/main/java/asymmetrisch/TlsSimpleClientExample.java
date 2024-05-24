/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asymmetrisch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Beispiel, wie eine TLS-Verbindung mit einem Host aufgebaut werden kann.
 * @author tobias Urban
 */
public class TlsSimpleClientExample {

    private static final String URL = "th-koeln.de";
    private static final int PORT = 443;

    public static void main(String[] args) throws IOException {
        /* Schritt 1: Wir benötigen einen "socket" über dne die verschlüsselte 
        Kommuniaktion abläuft. */
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        /* Schritt 2: Wir verbinden uns mit dem entfernten Host (hier 
        th-koeln.de).*/
        SSLSocket socket = (SSLSocket) factory.createSocket(URL, PORT);

        /* Schritt 3: Wir senden ein GET / HTTP 1.0 an den Host, damit wir den
        Inhalt der webseite bekommen. DIes ist speziell für das HTTP 
        Protokoll. */
        PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())));
        out.println("GET / HTTP/1.0");
        out.println();
        out.flush();

        /* Schritt 4: Wir lesen die Rückagbe (hier der Inhalt von th-koeln.de) 
        aus und schreiben diesen in  die Standard ausgabe. */
        BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }

    }
}
