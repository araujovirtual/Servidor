package view;

import controller.Mensagem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import static util.Estado.*;
import util.Resposta;

/**
 *
 * @author Jucinaldo,Murilo.
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket s = new Socket("localhost", 5555);

        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(s.getInputStream());

        Mensagem m = new Mensagem();
        m.setEstado(LOGIN);
        m.setDados("eventName", "login");
        m.setDados("cpf", "17263475839");
        m.setDados("password", "@123mudar");

        out.writeObject(m);
        out.flush();

        m = (Mensagem) in.readObject();

        if (m.getEstado().equals(LOGINREPLY)) {
            Resposta resposta = m.getResposta();
            if (resposta == Resposta.VERADEIRO) {
                System.out.println("Sucesso no login");
                String cpf = (String) m.getDados("cpf");
                String evento = (String) m.getDados("eventName");
                String password = (String) m.getDados("password");
                System.out.println("Bem vindo: " + cpf);
                System.out.println("Sua senha: " + password);
                System.out.println("Evento: " + evento);

            } else {
                System.out.println("Erro no login");
                String evento = (String) m.getDados("eventName");
                System.out.println("Evento: " + evento);

            }
        }

        in.close();
        out.close();
        s.close();
    }

}
