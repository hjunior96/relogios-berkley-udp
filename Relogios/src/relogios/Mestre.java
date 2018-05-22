/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogios;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hamilton
 */
public class Mestre {

    /**
     * @author Hamilton Executa as funções do nó Mestre
     * @param escravos - ips dos escravos
     * @param ipMestre - ip do nó mestre
     * @param tempo - relógio a ser inicializado
     * @param limite - limite de diferença tolerado entre os relógios
     */
    public void executar(List<String> escravos, String ipMestre, int tempo, int limite) {
        try {
            while (true) {
                //Variável para receber os horários dos escravos
                byte[] receiveData = new byte[1024];
                //Variável para armazenar os valores de ajuste para os escravos
                byte[] sendData = new byte[1024];
//                List<DatagramSocket> sockets = new ArrayList<>();
//                List<DatagramPacket> pacotes = new ArrayList<>();
//                List<InetAddress> ips = new ArrayList<>();
//                List<Integer> portas = new ArrayList<>();
//                List<Integer> horarios = new ArrayList<>();

                
                DatagramPacket pacote;
                InetAddress ip;
                int porta;

                //pega as informações vindas dos escravos
//                for (String escravo : escravos) {
//                    porta = Integer.parseInt(escravo.substring(escravo.indexOf(":"), escravo.length()));
                
//                    sockets.add(socket);

                pacote = new DatagramPacket(receiveData, receiveData.length);
//                    pacotes.add(pacote);
               
                ip = pacote.getAddress();
                porta = pacote.getPort();
                DatagramSocket socket=new DatagramSocket(porta);
                socket = new DatagramSocket(porta);
                socket.receive(pacote);
//                    ips.add(ip);
//                    portas.add(porta);
//                }

                //Pega os horarios dos relógios
                int horarioMestre = limite;
                int contador = 0, soma = 0, media;
                String horarioNaoFormatado;
                int horas;
                int minutos;
                int horarioConvertido;
                //calcula a média 
//                for (DatagramPacket pack : pacotes) {
                horarioNaoFormatado = new String(pacote.getData());
                horas = Integer.parseInt(horarioNaoFormatado.substring(0, horarioNaoFormatado.indexOf(":")));
                minutos = Integer.parseInt(horarioNaoFormatado.substring(horarioNaoFormatado.indexOf(":")));
                horarioConvertido = horas * 60 + minutos;
//                horarios.add(horarioConvertido);
                    //Se estiver dentro do intervalo permitido, incrementa contador e adiciona o horário na soma
                if (horarioConvertido - horarioMestre < tempo || horarioConvertido - horarioMestre > tempo * -1) {
                    contador++;
                    soma += horarioConvertido;
                }
//                }

                //se contador for zero(não foi recuperado nenhum relógio ou todos estão fora do limite, média fica zero
                media = contador == 0 ? 0 : soma / contador;
                int valorEnviado;
                //Calcula valores de correção (Média de diferença - diferença entre horario escravo e horario mestre
//                for (int i = 0; i < sockets.size(); i++) {
                valorEnviado = media - (horarioConvertido - horarioMestre);
                sendData = ByteBuffer.allocate(4).putInt(valorEnviado).array();
                    //Manda para as outras máquinas ressincronizarem seus relógios
                socket.send(new DatagramPacket(sendData, sendData.length, ip, porta));
//                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Mestre.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Mestre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
