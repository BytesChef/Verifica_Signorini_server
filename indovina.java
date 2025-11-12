
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class indovina extends Thread {
    Socket s;
    int segreto;

    public indovina(Socket mioSocket) throws NumberFormatException, IOException {
        this.s = mioSocket;
        segreto = ThreadLocalRandom.current().nextInt(1, 101); // 1 incluso, 101 escluso
    }

   

    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out;
            out = new PrintWriter(s.getOutputStream(), true);
            out.println("WELCOME INDOVINA v1 RANGE 1 100");
            int tries = 0;
            int min = 1; // default
            int max = 100; // default
            do {

                System.out.println(segreto);

                String comando = in.readLine();
                String[] parts = comando.split(" ");

                if (parts.length == 2) {                                // prova ad indovinare
                    int n1 = Integer.parseInt(parts[1]);

                    if (parts[0].equals("GUESS") && n1 > min && n1 < max) {
                        tries++;

                        if (n1 == segreto) {
                            out.println("OK CORRECT in T=" + tries);
                            break;
                        }
                        if (n1 > segreto) {
                            out.println("HINT LOWER");

                        }
                        if (n1 < segreto) {
                            out.println("HINT HIGHER");
                        }
                    }
                    else{
                        out.println("ERR SYNTAX");
                    }
                   
                }

                if (parts.length == 3) { // cambio RANGE

                    int n1 = Integer.parseInt(parts[1]);
                    int n2 = Integer.parseInt(parts[2]);

                    if (parts[0].equals("RANGE") && n1 < n2 && tries == 0) {
                        min = n1;
                        max = n2;
                        out.println("OK RANGE" + " " + min + " " + max);
                        segreto = ThreadLocalRandom.current().nextInt(min, max); // 1 incluso, 101 escluso
                    }

                }
                if (comando.equals("STATS")) {
                    out.println("INFO RANGE "+ min +" "+ max + "; "+ " TRIES " + tries);
                }

                if (comando.equals("NEW")) {
                    out.println("OK NEW");
                    segreto = ThreadLocalRandom.current().nextInt(min, max); // 1 incluso, 101 escluso
                    out.println("WELCOME INDOVINA v1 RANGE" + " " + min + " " + max);
                    tries = 0;

                }
 
                if (comando.equals("QUIT")) {
                    out.println("BYE");
                /*     mioSocket.close(); */
                }
            

            } while (true);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
