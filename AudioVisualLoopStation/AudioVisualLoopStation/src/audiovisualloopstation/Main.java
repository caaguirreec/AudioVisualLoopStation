/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiovisualloopstation;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author SOFTWARE3
 */
public class Main extends javax.swing.JFrame {

 /**
  * Global variables
 */    
AudioFileFormat.Type aFF_T = AudioFileFormat.Type.WAVE;
AudioFormat aF = new AudioFormat(8000.0F, 16, 1, true, false);
TargetDataLine tD;
int RecordName;
boolean record=false;
int i;
int j;
int modu;

ArrayList audio = new ArrayList();

 /**
  * Thread to record
 */ 
class CapThread extends Thread {
public void run() {
try {
tD.open(aF);
tD.start();


File f = new File("/home/pi/NetBeansProjects/AudioVisualLoopStation/dist/recordings/"+RecordName+".wav");
audio.add(RecordName+".wav");
AudioSystem.write(new AudioInputStream(tD), aFF_T, f);
}catch (Exception e){}
}
}
 class LoopThread extends Thread {
          @Override
          public void run() {
        
          {System.out.println("Looping running.");
              startlooping();}
          }
          public void startlooping(){
              String fileName; 
         
              fileName = "/home/pi/NetBeansProjects/AudioVisualLoopStation/dist/recordings/"+audio.get(i-1);
               InputStream in = null;
                try {
                    in = new FileInputStream(fileName);
                   } catch (FileNotFoundException e) {
                    System.out.println("Media file not present.");
                   
                   }
                    AudioStream as = null;
               try {
                    as = new AudioStream(in);
               } catch (IOException e) {
                   e.printStackTrace();
               }
               
   try {
        do {AudioPlayer.player.start(as);
        } while (as.available() > 0);
        
            //startlooping();
        
    } catch (IOException ex) {
        System.out.println("Looping problem.");
    }

  }
          }  
class CapThreadStop extends Thread {
@Override
public void run() {

    try {
            tD.close();
            File source = new File("/home/pi/NetBeansProjects/AudioVisualLoopStation/dist/recordings/1.wav");
            File target = new File("/home/pi/NetBeansProjects/AudioVisualLoopStation/dist/recordings/1.mp3");
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(new Integer(128000));
            audio.setChannels(new Integer(1));
            audio.setSamplingRate(new Integer(44100));
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("mp3");
            attrs.setAudioAttributes(audio);
            Encoder encoder = new Encoder();
            System.out.println("guardando...");
            try {
                encoder.encode(source, target, attrs);
            } catch (IllegalArgumentException | EncoderException ex) {
        
            }
            source.delete();
}catch (Exception e){}
}
}
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 239, Short.MAX_VALUE)
        );

        jLabel1.setText("jLabel1");

        jButton1.setText("Play");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
  
       // (new File("/home/pi/NetBeansProjects/AudioVisualLoopStation/dist"+"/recordings/")).mkdirs();
       
        pedalpush();

    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       
       new Thread(new LoopThread()).start(); 
   
              
    }//GEN-LAST:event_jButton1ActionPerformed
         
  public void pedalpush(){
   final GpioController gpio = GpioFactory.getInstance();
        i=0;
      
         
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,PinPullResistance.PULL_DOWN);
        // create and register gpio pin listener
  myButton.addListener((GpioPinListenerDigital) (GpioPinDigitalStateChangeEvent event) -> {
           
            modu=j%2;
     
            
            // display pin state on console
            System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
           
            if("HIGH".equals(event.getState().toString()) && modu==0) 
            {   i++;
                jLabel1.setText("Start Recording: "+i+".wav");
                jPanel1.setBackground(Color.red);
             
             
             RecordName=i;
             DataLine.Info dLI = new DataLine.Info(TargetDataLine.class,aF);
                try {
                    tD = (TargetDataLine)AudioSystem.getLine(dLI);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
             new CapThread().start();
             System.out.println("Grabando..."+modu);
              j++;
            }
            ////////////////////////////////////////////////////////
            //if("LOW".equals(event.getState().toString()))
            if("HIGH".equals(event.getState().toString()) && modu!=0) 
            {jLabel1.setText("Stop Recording: "+i+".wav");
            jPanel1.setBackground(Color.WHITE);
            tD.close();
           
            System.out.println("Grabación finalizada!"+modu);
            
            j++;
            
            
            } 
        });
  
  
  
  }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
