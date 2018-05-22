/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hamilton
 */
public class Escravo {
    
    /**
     * @autor Hamilton
     * Metódo para executar o nó escravo
     * @param ipMestre
     * @param tempo 
     */
    public void executar(String ipMestre, int tempo){
        try {
            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(ipMestre.substring(0, ipMestre.lastIndexOf(":")));
            int porta = Integer.parseInt(ipMestre.substring(ipMestre.lastIndexOf(":"), ipMestre.length()));
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            System.out.println("Horário Atual: " + tempo);
            sendData = ByteBuffer.allocate(4).putInt(tempo).array();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, porta);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String novoTempo = new String(receivePacket.getData());
            System.out.println("Horário Ajustado: " + novoTempo);
            clientSocket.close();
        } catch (SocketException ex) {
            Logger.getLogger(Escravo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Escravo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
