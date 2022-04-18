package model;

import controller.Mensagem;
import static util.Estado.*;
import static util.Resposta.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Jucinaldo Simões de Araujo.
 * @author Murilo
 */
public class Servidor implements Runnable {

    ServerSocket serverSocket;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket sock;
    long inicio;
    long fim;
    int contador;

    /**
     * @author Jucinaldo Simoes de Araujo
     * @author Murilo
     * @param porta = especifica a porta do servidor
     */
    public void criaServidor(int porta) {
        try {
            serverSocket = new ServerSocket(porta);
        } catch (IOException ex) {
            System.out.println("Erro ao criar o servidor ---> " + ex.getMessage());
        } finally {
            System.out.println("Servidor criado [" + serverSocket.getInetAddress() + "]:[" + porta + "]\n");
        }
    }

    /**
     * @return = retorna um socket
     */
    public Socket esperaConexao() {
        try {
            sock = serverSocket.accept();
            return sock;

        } catch (IOException ex) {
            System.out.println("Erro ao criar um socket: " + ex.getMessage());

        }
        return null;

    }

    /**
     * @author Jucinaldo Simões de Araujo.Trata as conexões com o cliente ,
     * @author Murilo utilizando JSONObject ou JSONArray
     * @param s = passa o socket como parametro
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    @SuppressWarnings("unused")
    public void trataConexaoCliente() throws IOException, ClassNotFoundException {
        contador = contador + 1;
        inicio = System.currentTimeMillis();
        in = new ObjectInputStream(sock.getInputStream());
        out = new ObjectOutputStream(sock.getOutputStream());
        Mensagem m = new Mensagem();
        m = (Mensagem) in.readObject();

        if (m.getEstado().equals(LOGIN)) {
            String cpf = (String) m.getDados("cpf");
            String password = (String) m.getDados("password");
            System.out.println("Servidor recebeu um estado de: " + m.getEstado());
            if (cpf == null || cpf.length() <= 0 || password == null || password.length() <= 0) {
                Mensagem reply = new Mensagem();
                reply.setEstado(LOGINREPLY);
                reply.setResposta(FALSO);
                reply.setDados("eventName", "login:error");
                out.writeObject(reply);
                out.flush();
                System.out.println("Servidor respondeu o estado de: " + reply.getEstado());
            } else {
                Mensagem reply = new Mensagem();
                reply.setEstado(LOGINREPLY);
                reply.setResposta(VERADEIRO);
                reply.setDados("cpf", cpf);
                reply.setDados("password", password);
                reply.setDados("eventName", "login:sucess");
                out.writeObject(reply);
                out.flush();
                System.out.println("Servidor respondeu o estado de: " + reply.getEstado());
            }
        }
        fim = System.currentTimeMillis() - inicio;

    }

    /**
     * Implementa o método RUN para o Thread
     */
    @Override
    public void run() {

        try {

            trataConexaoCliente();

        } catch (IOException ex) {
            System.out.println("Problemas de I/O: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Problemas de classe: " + ex.getMessage());
        } finally {
            fecharConexoes();
            System.out.println("\nCliente desconectado...");
            System.out.println("Tempo total desta operação " + (fim / 1000) + " em segundos");
            System.out.println("Tempo total desta operação " + (fim) + "ms em milisegundos");
            System.out.println("\nTotal de conexões neste servidor = " + contador);
            System.out.println("Aguardando novos clientes.");

        }
    }

    public void fecharConexoes() {
        try {
            in.close();
            out.close();
            sock.close();
        } catch (IOException ex) {
            System.out.println("Erro ao fechar as conexões: " + ex.getMessage());
        }
    }

}
