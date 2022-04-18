/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.io.IOException;
import java.net.Socket;
import model.Servidor;

/**
 *
 * @author usuario
 */
public class Principal {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Servidor servidor = new Servidor();
        servidor.criaServidor(5555);
        while (true) {
            Socket s = servidor.esperaConexao();
            Thread t = new Thread(servidor);
            t.start();
        }

    }

}
