package com.buzz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class indovina extends Thread {
    Socket s;

    private static ArrayList<String> lista1 = new ArrayList<>(Arrays.asList(
            "Marco Rossi", "Ivan Bruno", "Giulia Neri", "Luca Bianchi", "Sara Galli"));

    private static ArrayList<String> lista2 = new ArrayList<>(Arrays.asList(
            "Ciccio Bello", "Francesca Pini", "Giorgio Verdi", "Marta Lodi", "Claudio Benvenuti", "Pippo Baudo"));

    private static ArrayList<String> lista3 = new ArrayList<>(Arrays.asList(
            "Anna Rosa", "Paolo Conti", "Davide Leone", "Chiara Valli", "Elisa Greco"));

    private static final ArrayList<ArrayList<String>> liste = new ArrayList<>(
            Arrays.asList(lista1, lista2, lista3));

    public indovina(Socket mioSocket) throws NumberFormatException, IOException {
        this.s = mioSocket;
    }

    public void run() {

        try {
            System.out.println("Qualcuno si è collegato");
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out;
            out = new PrintWriter(s.getOutputStream(), true);

            out.println("Versione server: 1.0");

            int listaNum;
            do {
                String lista = in.readLine();
                if (lista.equals("!")) {
                    break;
                }
                listaNum = Integer.parseInt(lista);
                if (liste.size() < listaNum) {
                    out.println("KO");
                } else {
                    out.println("OK");
                    int giocatore = Integer.parseInt(in.readLine());
                    if (liste.get(listaNum - 1).size() < giocatore) {
                        out.println("KO");
                        System.out.println("log");
                    } else {
                        out.println("OK");
                        System.out.println("Ciao");
                        out.println(liste.get(listaNum - 1).get(giocatore - 1));
                    }
                }
            } while (true);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}