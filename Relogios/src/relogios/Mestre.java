/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogios;

import java.util.List;

/**
 *
 * @author Hamilton
 */
public class Mestre {
    
    /**
     * @author Hamilton
     * Executa as funções do nó Mestre
     * @param escravos - ips dos escravos 
     * @param ipMestre - ip do nó mestre
     * @param tempo - relógio a ser inicializado
     * @param limite - limite de diferença tolerado entre os relógios
     */
    public void executar(List<String> escravos, String ipMestre, int tempo, int limite ){
        //Pega os horarios dos relógios
        int horarioMestre = limite;
        int[] horarios = {72,102,119};
        //Variável contadora para usar no cálculo da média
        int contador=0, soma=0, media;
        //Calcula a média de diferença entre eles considerando o limite passado por parâmetro
        for(int i = 0; i< horarios.length; i++){
            //Se estiver dentro do intervalo permitido, incrementa contador e adiciona o horário na soma
            if(horarios[i] - horarioMestre < tempo || horarios[i] - horarioMestre > tempo*-1){
                contador++;
                soma+=horarios[i];
            }               
        }
        //se contador for zero(não foi recuperado nenhum relógio ou todos estão fora do limite, média fica zero
        media = contador==0 ? 0 : soma/contador;
        
        //Calcula valores de correção (Média de diferença - diferença entre horario escravo e horario mestre
        for(int i =0; i < horarios.length; i++){
            horarios[i] = media - (horarios[i] - horarioMestre);
        }
        //Manda para as outras máquinas ressincronizarem seus relógios
        
        
    }
}
