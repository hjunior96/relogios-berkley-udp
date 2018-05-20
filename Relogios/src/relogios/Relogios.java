/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hamilton
 */
public class Relogios {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String flag = "-m";
            String ipPorta = "xxx.xxx.xxx.xxx:xxxx";
            int limite = 10;
            int tempo = 112;
            String arquivoEscravos = "xxxxxxxxx.txt";
            Path path = Paths.get(arquivoEscravos);
            List<String> escravos = Files.readAllLines(path);
            
            String arquivoLog = "xxxxxxxx.txt";
            if(flag.equals("-m")){
                //comportamento de mestre
                new Mestre().executar(escravos, ipPorta, tempo, limite);
            }else{
                //comportamento de mestre
                new Escravo().executar(ipPorta, limite);
            }
        } catch (IOException ex) {
            Logger.getLogger(Relogios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
