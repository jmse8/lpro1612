/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientba;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

/**
 *Implementa a interface gŕafica de um jogo.
 * @author lpro1612
 */
public class boardGame extends javax.swing.JFrame {
    int i=0;
    int k=0;
    int id;
    String player;
    String myMoves, opponentMoves;
    boolean start_game = false;
    String play;
    Color background; 	
    int vertical=1;
    String lastMove="";
    boolean finish = false;
    String username="";
    /**
     * Cria e inicializa um novo tabuleiro de jogo.
     */
    @SuppressWarnings("empty-statement")
    public boardGame(int id) {
                SavedData login = new SavedData();
                this.id=id;
                JPanel gui = new JPanel(new GridLayout(1,2,50,5));
                 gui.setBorder(new EmptyBorder(50,25,5,5));
                 boardGame.OwnBoard board = new boardGame.OwnBoard();
                gui.add(board);
                    gui.add(new boardGame.EnemyBoard());
                this.setContentPane(gui);
                this.pack();
                username=login.getUsername();
                 initComponents();
                this.setSize(1000,650);
                
                String[] output = {"getOwner", Integer.toString(id)};// vai buscar o owner do jogo 
                
                Protocol p = new Protocol(1612);
                List<String> input = p.sendToServer(output);
             //   System.out.println(input.get(0));
                
                if(input.get(0).equals(login.getUsername()))//0wner
                {
                    player = "player1";
                    play = "player1";
                    String[] output2 = {"startGame", Integer.toString(id)};
                    int port = id+1024;
                    p = new Protocol(1612);
                    p.sendToServer(output2);
                    p = new Protocol(port);
                    String [] out = {"sendUsername", player, login.getUsername()};
                    p.sendToServer(out);
                    checkStart checkstart = new checkStart();
                    new Thread(checkstart).start();
                }
                
                else{ // outro jogador 
                    player = "player2";
                    play = "player1";
                    int port = id+1024;
                    p = new Protocol(port);
                    String [] out = {"sendUsername", player, login.getUsername()};
                    p.sendToServer(out);
                }
              (new Thread(board.new getPlays())).start();
               getChat chat = new getChat();
                new Thread(chat).start();
               // this.setContentPane(gui);
                
    }
    /**
     * Implementa o tabuleiro do user.
     */
    public class OwnBoard extends JPanel {
        boolean place_boats = false;
        private String carrier, battleship, cruiser, submarine, destroyer;
        /**
         * Inicializa e constrói um novo tabuleiro do user.
         */
        public OwnBoard() {
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    gbc.gridx = col;
                    gbc.gridy = row;

                    CellPane cellPane = new CellPane(col, row);
                    Border border = null;
                    if (row < 9) {
                        if (col < 9) {
                            border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        } else {
                            border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                        }
                    } else {
                        if (col < 9) {
                            border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                        } else {
                            border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                        }
                    }
                    cellPane.setBorder(border);
                    add(cellPane, gbc);
                }
            }
        }
        
        public class  getPlays implements Runnable{
        
        @Override
        public void run() {
             List<String> input;
             int port=id+1024;
             Protocol p = new Protocol(port);
             while(!finish){
                do{
                    String[] output3={"checkSurrender"};
                   input = p.sendToServer(output3);
                   if("surrender".equals(input.get(0))){
                       int res = JOptionPane.showOptionDialog(null, "YOU WIN", "YOUR ENEMY SURRENDERED", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                        if(res == JOptionPane.OK_OPTION)
                        {
                            MainMenu mm = new MainMenu();
                            mm.setVisible(true);
                            dispose();
                            finish=true;
                        }
                   }
                   String[] output2={"getLastPlay",player};
                   input = p.sendToServer(output2);
                   if(finish)break;
                   if(!input.get(0).equals(lastMove)){
                         break;
                   }
                   
                }while(!play.equals(player));
                    
                   lastMove=input.get(0);
                   
                   if(!"".equals(lastMove)){
                     String[] output2={"checkEnemyMove",lastMove,player};
                     input = p.sendToServer(output2);
                     
                    int jogada =  Integer.parseInt(lastMove);
                    int x = jogada /10;
                    int y= (int)((jogada/10.0 -x)*10.1);
                    if("hit".equals(input.get(0))) getComponentAt(x*40,y*40).setBackground(Color.RED);
                    else if("miss".equals(input.get(0))) getComponentAt(x*40,y*40).setBackground(Color.BLUE);
                    else if("hit::lost".equals(input.get(0))){
                        getComponentAt(x*40,y*40).setBackground(Color.RED);
                        int res = JOptionPane.showOptionDialog(null, "YOU LOST", "YOU LOST", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                        if(res == JOptionPane.OK_OPTION)
                        {
                            MainMenu mm = new MainMenu();
                            mm.setVisible(true);
                            dispose();
                        }
                    } 
                   }
                   
                   String[] output2={"checkTurn"};
                     input = p.sendToServer(output2);
                   play=input.get(0);
                   
                   
             }
         }
    }
            
         public void placeShipVerticalInBoard(int x, int y){
            
            
            if(i==0){//cruiser 5 posiçoes
                    for(int n=0; n<5; n++)
                        getComponentAt((x)*40,(n+y)*40).setBackground(Color.BLACK);                   
            }
            else if(i==1){
                    for(int n=0; n<4; n++)
                        getComponentAt((x)*40,(n+y)*40).setBackground(Color.BLACK); 
            }
            else if(i==2 || i==3){
                    for(int n=0; n<3; n++)
                        getComponentAt((x)*40,(n+y)*40).setBackground(Color.BLACK);                  
            }
            else if(i==4){
                    for(int n=0; n<2; n++)
                        getComponentAt((x)*40,(n+y)*40).setBackground(Color.BLACK);
            }
         }
         
         public void placeShipHorizontalInBoard(int x, int y){
           
            if(i==0){//cruiser 5 posiçoes
                    for(int n=0; n<5; n++)
                        getComponentAt((x+n)*40,y*40).setBackground(Color.BLACK);
            }
            
            else if(i==1){
                    for(int n=0; n<4; n++)
                        getComponentAt((x+n)*40,y*40).setBackground(Color.BLACK);
            }
            else if(i==2 || i==3){
                    for(int n=0; n<3; n++)
                        getComponentAt((x+n)*40,y*40).setBackground(Color.BLACK);
            }
            else if(i==4){
                    for(int n=0; n<2; n++)
                        getComponentAt((x+n)*40,y*40).setBackground(Color.BLACK);
 
            }
         }
        
	/**
         * Implementa, gráficamente, a mudança de cor das células na vertical a partir da posição (x,y) em que o cursor se encontra.
         * @param x Índice no eixo horizontal da célula onde o cursor se encontra.
         * @param y Índice no eixo vertical da célula onde o cursor se encontra.
         */
	public void sombraVertical(int x, int y){
            if(i==0){
                int k=5;
               if(y>5)k=10-y;
                for(int n=0; n<k; n++)
                        getComponentAt((x)*40,(n+y)*40).setBackground(Color.GRAY);
            }
            if(i==1){
                int k=4;
                if(y>6)k=10-y;
                for(int n=0; n<k; n++){
                        Component c =getComponentAt((x)*40,(n+y)*40);
                        if(c.getBackground()!=Color.BLACK)c.setBackground(Color.GRAY);
                }
                
            }   
            if(i==2 || i==3){
                int k=3;
                if(y>7)k=10-y;
                for(int n=0; n<k; n++){
                        Component c =getComponentAt((x)*40,(n+y)*40);
                        if(c.getBackground()!=Color.BLACK)c.setBackground(Color.GRAY);
                }
                
            }
            if(i==4){
                int k=2;
                if(y>8)k=10-y;
                for(int n=0; n<k; n++){
                        Component c =getComponentAt((x)*40,(n+y)*40);
                        if(c.getBackground()!=Color.BLACK)c.setBackground(Color.GRAY);
                }
                
            }
        }
        /**
         * Implementa, gráficamente, a mudança de cor das células na horizontal a partir da posição (x,y) em que o cursor se encontra.
         * @param x Índice no eixo horizontal da célula onde o cursor se encontra.
         * @param y Índice no eixo vertical da célula onde o cursos se encontra.
         */
        public void sombraHorizontal(int x, int y){
            
            if(i==0){
                int  p=5;
                if(x>5)p=10-x;
                for(int n=0; n<p; n++)
                        getComponentAt((x+n)*40,y*40).setBackground(Color.GRAY);  
            }
            if(i==1){
                int  p=4;
                
                if(x>6)p=10-x;
                for(int n=0; n<p; n++){
                       Component c= getComponentAt((x+n)*40,y*40);
                       if(c.getBackground()!=Color.BLACK)c.setBackground(Color.GRAY);
                }
            }   
            if(i==2 || i==3){
                int p=3;
                
                if(x>7)p=10-x;
                for(int n=0; n<p; n++){
                       Component c= getComponentAt((x+n)*40,y*40);
                       if(c.getBackground()!=Color.BLACK)c.setBackground(Color.GRAY);
                }  
            }
            if(i==4){
                int p=2;
                
                if(x>8)p=10-x;
                for(int n=0; n<p; n++){
                       Component c= getComponentAt((x+n)*40,y*40);
                       if(c.getBackground()!=Color.BLACK)c.setBackground(Color.GRAY);
                }  
            }
            
        }
        /**
         * Implementa, graficamente, a mudança de cor das células, na vertical, para a cor default do tabuleiro a partir da posição (x,y) onde o cursor se encontra. 
         * @param x Índice no eixo horizontal da célula onde o cursor se encontra.
         * @param y Índice no eixo vertical da célula onde o cursor se encontra.
         * @param defaultback Cor default do tabuleiro.
         */
        public void tiraSombraVertical(int x, int y, Color defaultback){
            if(i==0){
                int k=5;
                if(y>5)k=10-y;
                for(int n=0; n<k; n++){
                      Component c= getComponentAt((x)*40,(n+y)*40);
                      if(c.getBackground()!=Color.BLACK) c.setBackground(defaultback);
                }
            }
            if(i==1){
                int k=4;
                if(y>6)k=10-y;
                for(int n=0; n<k; n++){
                      Component c= getComponentAt((x)*40,(n+y)*40);
                      if(c.getBackground()!=Color.BLACK) c.setBackground(defaultback);
                }
            }
            if(i==2 || i==3){
                int k=3;
                if(y>7)k=10-y;
                for(int n=0; n<k; n++){
                      Component c= getComponentAt((x)*40,(n+y)*40);
                      if(c.getBackground()!=Color.BLACK) c.setBackground(defaultback);
                }
            }
            if(i==4){
                int k=2;
                 if(y>8)k=10-y;
                for(int n=0; n<k; n++){
                      Component c= getComponentAt((x)*40,(n+y)*40);
                      if(c.getBackground()!=Color.BLACK) c.setBackground(defaultback);
                }
            }
        }
        /**
         * Implementa, graficamente, a mudança de cor das células, na horizontal, para a cor default do tabuleiro a partir da posição (x,y) onde o cursor se encontra.
         * @param x Índice no eixo horizontal da célula onde o cursor se encontra.
         * @param y Índice no eixo vertical da célula onde o cursos se encontra.
         * @param defaultback Cor default do tabuleiro.
         */
        public void tiraSombraHorizontal(int x, int y, Color defaultback){
            if(i==0){
                int p=5;
                if(x>5)p=10-x;
                    for(int n=0; n<p; n++){
                           Component c= getComponentAt((x+n)*40,y*40);
                           if(c.getBackground()!=Color.BLACK) c.setBackground(defaultback);
                    }
            }
            if(i==1){
                int p=4;
                if(x>6)p=10-x;
                for(int n=0; n<p; n++){
                           Component c= getComponentAt((x+n)*40,y*40);
                           if(c.getBackground()!=Color.BLACK) c.setBackground(defaultback);
                    }
                
            }
            if(i==2 || i==3){
                int p=3;
                if(x>7)p=10-x;
                for(int n=0; n<p; n++){
                           Component c= getComponentAt((x+n)*40,y*40);
                           if(c.getBackground()!=Color.BLACK) c.setBackground(defaultback);
                 }
            }
            if(i==4){
                int p=2;
                if(x>8)p=10-x;
                for(int n=0; n<p; n++){
                           Component c= getComponentAt((x+n)*40,y*40);
                           if(c.getBackground()!=Color.BLACK) c.setBackground(defaultback);
                 }
                
            }
        }
        
       
        
        
         
         /**
          * Implementa cada uma das células que compõe o tabuleiro do utilizador.
          */
        public class CellPane extends JPanel {

            final Color defaultBackground=getBackground();

            /**
             * Inicializa e constrói uma nova célula no tabuleiro na posição (x,y).
             * @param x Índice no eixo horizontal da célula escolhida.
             * @param y Índice no eixo vertical da célula escolhida.
             */
            public CellPane(int x, int y) {
                addMouseListener(new MouseAdapter() {
                    @Override

		    public void mouseEntered(MouseEvent e) {
                    
                    if(vertical==1)
                        sombraVertical(x,y);
                    else sombraHorizontal(x,y);
                    }

                public void mouseExited(MouseEvent e) {
                   
                            tiraSombraVertical(x,y,defaultBackground);
                            tiraSombraHorizontal(x,y,defaultBackground);
                    
                }

                    public void mousePressed(MouseEvent e){
                        if(e.getButton() == MouseEvent.BUTTON1){
                            int port=id+1024;
                            Protocol p = new Protocol(port);
            
                           if(vertical==1 && i<5){
                                    System.out.println("testeee asdsad");
                                    String[] output = {"placeBoat", Integer.toString(x), Integer.toString(y), "vertical", Integer.toString(i), player};
                                    List<String> input = p.sendToServer(output);
                                    if(input.get(0).equals("true")){
                                        placeShipVerticalInBoard(x,y);
                                        i++;
                                    }
                                            
                                    System.out.println("no boardgame ta a retornar" +input.get(0));
                                tiraSombraHorizontal(x,y,defaultBackground);
                                 tiraSombraVertical(x,y,defaultBackground);
                                
                           }
                           else if(vertical==0 && i<5) {
                               String[] output = {"placeBoat", Integer.toString(x), Integer.toString(y), "horizontal", Integer.toString(i),player};
                                    List<String> input = p.sendToServer(output);
                                    if(input.get(0).equals("true")){
                                        placeShipHorizontalInBoard(x,y);
                                        i++;
                                    }
                                tiraSombraHorizontal(x,y,defaultBackground);
                                tiraSombraVertical(x,y,defaultBackground);
                           
                           }
             
                                
                        }
                        else{
                           if(vertical==1 && i<5) {
                               vertical=0;
                               sombraHorizontal(x,y);
                               tiraSombraVertical(x,y,defaultBackground);
                               if(getBackground()!=Color.BLACK) setBackground(Color.GRAY);
                           }
                           else if(vertical ==0 && i<5){
                               vertical=1;
                               sombraVertical(x,y);
                               tiraSombraHorizontal(x,y,defaultBackground);
                              if(getBackground()!=Color.BLACK) setBackground(Color.GRAY);
                           }
                        }
                        
                        
                    }

                });
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(40, 40);
            }
        }
        }
    
        /**
         * Implementa o tabuleiro do adversário.
         */
        public class EnemyBoard extends JPanel {
            /**
             * Inicializa e constrói um novo tabuleiro para o adversário.
             */
        public EnemyBoard() {
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    gbc.gridx = col;
                    gbc.gridy = row;

                    CellPane2 cellPane = new CellPane2(col,row);
                    Border border = null;
                    if (row < 9) {
                        if (col < 9) {
                            border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        } else {
                            border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                        }
                    } else {
                        if (col < 9) {
                            border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                        } else {
                            border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                        }
                    }
                    cellPane.setBorder(border);
                    add(cellPane, gbc);
                }
            }
        }
         
         
         
        /**
         * Implementa cada uma das células do tabuleiro do adversário.
         */        
        public class CellPane2 extends JPanel {
            
            public CellPane2(int x, int y) {
                addMouseListener(new MouseAdapter() {
                    @Override

                    public void mouseClicked(MouseEvent e){
                        int port=id+1024;
                            Protocol p = new Protocol(port);
                            System.out.println("vale o x e y :"+x+y);
                            
                            
                        if(play.equals(player) && i>4){
                            System.out.println("estou aqui");
                            String[] output = {"makePlay", Integer.toString(x), Integer.toString(y), player};
                            List<String> input = p.sendToServer(output);
                            if(input.get(0).equals("hit")){
                                setBackground(Color.RED);
                            }
                            else if(input.get(0).equals("miss")){
                                setBackground(Color.BLUE);
                                System.out.println("fica blue");
                                if("player1".equals(player)) play="player2";
                                if ("player2".equals(player))play="player1";
                            }
                            else if(input.get(0).equals("win")){
                                int res = JOptionPane.showOptionDialog(null, "YOU WIN", "YOU WIN", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                                if(res == JOptionPane.OK_OPTION)
                                {
                                    MainMenu mm = new MainMenu();
                                    mm.setVisible(true);
                                    dispose();
                                }
                            }
     
                        }
                        
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(boardGame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(40, 40);
        }
        }
        
    }
        
       
        
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        mensagem = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chat = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("BaShip");
        jLabel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton1.setText("Surrender");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Send");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        chat.setColumns(20);
        chat.setRows(5);
        jScrollPane1.setViewportView(chat);
        chat.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 425, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(74, 74, 74)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       this.setVisible(false);
       this.dispose();
       MainMenu mm = new MainMenu();
       mm.setVisible(true);
       finish=true;
       int port=1024+id;
       Protocol p = new Protocol(port);
       String[] output={"endGame"};
       p.sendToServer(output);
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       String msg=mensagem.getText().trim();
       mensagem.setText("");
       int port = 1024 +id;
       Protocol p  = new Protocol (port);
       String[] output={"chat",msg,username};
       p.sendToServer(output);
    }//GEN-LAST:event_jButton2ActionPerformed
    
     public class  getChat implements Runnable{
        
        @Override
        public void run() {
            int port = 1024+id;
            Protocol p = new Protocol(port);
            int numChat = 0;
            while (!finish){
          
                String[] output={"checkChat"};
                List<String> input=p.sendToServer(output);
                
                if(numChat != Integer.parseInt(input.get(0))){
                    numChat = Integer.parseInt(input.get(0));
                    String[] output2={"getChatMsg"};
                    List<String> input2=p.sendToServer(output2);
                    System.out.println("input2"+input2);
                    String [] data = input2.get(0).split("::--::");
                    chat.setText(chat.getText().trim() +"\n"+data[0] + ": "+data[1]);
                }
            }
        }
     }
     
     public class checkStart implements Runnable{
         @Override
         public void run() {
             Protocol p = new Protocol(1024+id);
             while(true){
                String[] output0={"checkStart"};
                 List<String> input0=p.sendToServer(output0);
                 if(!"false".equals(input0.get(0))){  
                     JOptionPane.showMessageDialog(null, "The game is about to start!");
                     break;
                 }
                 else JOptionPane.showMessageDialog(null, "Waiting for other player"); 
             }
         }
     }
    
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
            java.util.logging.Logger.getLogger(boardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(boardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(boardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(boardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new boardGame(1).setVisible(true);
            }
            
        });
        
       
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chat;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mensagem;
    // End of variables declaration//GEN-END:variables
}
