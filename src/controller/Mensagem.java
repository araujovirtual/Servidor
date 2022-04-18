/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.Serializable;
import org.json.simple.JSONObject;
import util.Estado;
import util.Resposta;

/**
 *
 * @author usuario
 */
public class Mensagem implements Serializable{
    
    private Estado estado;
    private Resposta resposta;
    private JSONObject dados;

    public void setEstado(Estado estado) {
        this.estado = estado;
        dados = new JSONObject();
    }
    
    public Estado getEstado(){
        return estado;
    }

    public void setDados(String chave, Object valor) {
        dados.put(chave, valor);
    }
    
    public Object getDados(String chave){
        return dados.get(chave);
    }

    public Resposta getResposta() {
        return resposta;
    }
    
     /**
     * @param resposta the resposta to set
     */
    public void setResposta(Resposta resposta) {
        this.resposta = resposta;
    }
    
}
