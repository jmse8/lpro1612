/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Audio.AudioPlayer;
import Communication.Protocol;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import static java.lang.Integer.max;
import static java.lang.Thread.sleep;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import static java.lang.Thread.sleep;

/**
 * Implementa a interface gráfica para assistir a um jogo em tempo real.
 * @author eduardo
 */
public class SpectateGame extends javax.swing.JFrame {
    String player1, player2,player;
    int leave=0;
    int id;
    int movesPlayer1=0, movesPlayer2=0, boatsPlayer1=0, boatsPlayer2=0;
    boolean finish=false;
     AudioPlayer butMainSound = new AudioPlayer();
    
    public void butMainSound(){
        butMainSound.getName("button2.wav");
        butMainSound.soundBegin();
    }
    
    /**
     * Cria os tabuleiros dos dois jogadores presentes no jogo. 
     * @param id id do jogo
     */
    public SpectateGame(int id) {
        initComponents();
        this.id=id;
        System.out.println("ID DE JOGO"+id);
        JPanel gui = new JPanel(new GridLayout(1, 2, 50, 5));
        gui.setBorder(new EmptyBorder(50, 25, 5, 5));
        SpectateGame.OwnBoard board = new SpectateGame.OwnBoard();
        gui.add(board);
        SpectateGame.EnemyBoard enemyBoard = new SpectateGame.EnemyBoard();
        gui.add(enemyBoard);
        this.setContentPane(gui);
        this.pack();
        initComponents();
        this.setSize(1000, 650);
         moves moves = new moves(board,enemyBoard);
        new Thread(moves).start();
    }
    
    public class OwnBoard extends JPanel {

        boolean place_boats = false;
        private String carrier, battleship, cruiser, submarine, destroyer;
        public CellPane[] cells = new CellPane[100];

        /**
         * Inicializa e constrói um novo tabuleiro do owner.
         */
        public OwnBoard() {
            setLayout(new GridBagLayout());
            int i = 0;

            GridBagConstraints gbc = new GridBagConstraints();
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    gbc.gridx = col;
                    gbc.gridy = row;

                    CellPane cellPane = new CellPane(col, row);
                    cells[row + col * 10] = cellPane;
                    i++;
                    Border border = null;
                    if (row < 9) {
                        if (col < 9) {
                            border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        } else {
                            border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                        }
                    } else if (col < 9) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                    cellPane.setBorder(border);
                    add(cellPane, gbc);
                }
            }
        }
        /**
         * Implementa gráficamente a posição dos barcos.
         * @param boats - vector de Strings que contém as posições dos barcos.
         */
        public void placeBoats(String[] boats) {
            for (int i = 1; i < boats.length; i++) {
                cells[Integer.parseInt(boats[i])].setBackground(Color.DARK_GRAY);
            }
        }

        
    public class CellPane extends JPanel {

        final Color defaultBackground = getBackground();

        /**
         * Inicializa e constrói uma nova célula no tabuleiro na posição (x,y).
         *
         * @param x Índice no eixo horizontal da célula escolhida.
         * @param y Índice no eixo vertical da célula escolhida.
         */
        public CellPane(int x, int y) {

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

        CellPane2[] cells = new CellPane2[100];

        /**
         * Inicializa e constrói um novo tabuleiro para o adversário.
         */
        public EnemyBoard() {
            setLayout(new GridBagLayout());
            //    int i = 0;
            GridBagConstraints gbc = new GridBagConstraints();
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    gbc.gridx = col;
                    gbc.gridy = row;

                    CellPane2 cellPane = new CellPane2(col, row);
                    cells[row + col * 10] = cellPane;
                    //          i++;
                    Border border = null;
                    if (row < 9) {
                        if (col < 9) {
                            border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        } else {
                            border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                        }
                    } else if (col < 9) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                    cellPane.setBorder(border);
                    add(cellPane, gbc);
                }
            }
        }
        /**
         * Implementa gráficamente a posição dos barcos.
         * @param boats - vector de Strings que contém as posições dos barcos.
         */
        public void placeBoats(String[] boats) {
            for (int i = 1; i < boats.length; i++) {
                cells[Integer.parseInt(boats[i])].setBackground(Color.DARK_GRAY);
            }
        }

        /**
         * Implementa cada uma das células do tabuleiro do adversário.
         */
        public class CellPane2 extends JPanel {

            public CellPane2(int x, int y) {

            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(40, 40);
            }
        }

    }
    /**
     * Implementa a conexão com o servidor de forma a que seja possível actualizar os tabuleiros em tempo real.
     */

    public class moves implements Runnable {
        SpectateGame.OwnBoard board;
        SpectateGame.EnemyBoard enemy;
        int enemyHits =0;
        int ownerHits =0;
        int numMovesP1 =0;
        int numMovesP2 = 0;
        /**
         * Inicializa os tabuleiros do jogo a ser observado com a posição dos barcos.
         * @param board - Tabuleiro do owner do jogo a ser preenchido com os barcos.
         * @param enemy  - Tabuleiro do adversário a ser preenchido com os seus barcos.
         */
        public moves (SpectateGame.OwnBoard board, SpectateGame.EnemyBoard enemy){
            this.board=board;
            this.enemy=enemy;
        }

        @Override
            public void run(){
            while(enemyHits <17 && ownerHits<17){
            final Color defaultBackground = board.cells[1].defaultBackground;
            int port = id + 1024;
            String[] out = {"spectateGame", "player1"};
            Protocol p = new Protocol(port);
            List<String> boatsP1 =p.sendToServer(out);
             String[] out2 = {"spectateGame", "player2"};
            List<String> boatsP2 =p.sendToServer(out2);
             String[] out3 = {"spectateGame", "moves_player1"};
             List<String> movesP1 =p.sendToServer(out3);
             String[] out4 = {"spectateGame", "moves_player2"};
             List<String> movesP2 =p.sendToServer(out4);
            
             String boats_P1[] = boatsP1.get(0).split("::");
             String boats_P2[] = boatsP2.get(0).split("::");
             String moves_P1[] = movesP1.get(0).split("::");
             String moves_P2[] = movesP2.get(0).split("::");
             if(boats_P1.length < 17 || boats_P2.length < 17) continue;
             if(moves_P1.length <2 || moves_P2.length <2)continue;
             int cell;
             for(int i = 0; i< 17; i++){
                 cell=Integer.valueOf(boats_P1[i]);
                 board.cells[cell].setBackground(Color.BLACK);
             }
             for (int k=0; k<17 ; k++){
                 cell=Integer.valueOf(boats_P2[k]);
                 enemy.cells[cell].setBackground(Color.BLACK);
             }
             for(int j=0; j<moves_P1.length; j++){
                 cell=Integer.valueOf(moves_P1[j]);
                 if(enemy.cells[cell].getBackground() == Color.BLACK){
                     ownerHits++;
                     enemy.cells[cell].setBackground(Color.RED);
                 }
                 else if(enemy.cells[cell].getBackground() == defaultBackground)enemy.cells[cell].setBackground(Color.BLUE);
             }
             for(int j=0; j<moves_P2.length; j++){
                 cell=Integer.valueOf(moves_P2[j]);
                 if(board.cells[cell].getBackground() == Color.BLACK){
                     enemyHits++;
                     board.cells[cell].setBackground(Color.RED);
                 }
                 else if(board.cells[cell].getBackground() == defaultBackground)board.cells[cell].setBackground(Color.BLUE);
             }
             numMovesP1=moves_P1.length;
             numMovesP2=moves_P2.length;
             
             while(enemyHits <17 && ownerHits<17){
                  String[] out5 = {"checkNumMoves","player1"};
                  List<String> numMovesOwner =p.sendToServer(out5);
                  String valor[] = numMovesOwner.get(0).split("::");
                  if(Integer.valueOf(valor[0]) != numMovesP1){
                      numMovesP1 = Integer.valueOf(valor[0]);
                      String[] out7 = {"getLastPlay","player2"};
                      List<String> newMove =p.sendToServer(out7);
                      String[] newPlay = newMove.get(0).split("::");
                      System.out.println("ultima jogada do P1="+newPlay[0]);
                      if(enemy.cells[Integer.valueOf(newPlay[0])].getBackground()==Color.BLACK){
                          ownerHits++;
                          enemy.cells[Integer.valueOf(newPlay[0])].setBackground(Color.RED);
                      }
                      else if ( enemy.cells[Integer.valueOf(newPlay[0])].getBackground()==defaultBackground){
                          enemy.cells[Integer.valueOf(newPlay[0])].setBackground(Color.BLUE);
                      }
                  }
                  String[] out6 = {"checkNumMoves","player2"};
                  List<String> numMovesEnemy =p.sendToServer(out6);
                  String valor2[] = numMovesEnemy.get(0).split("::");
                  if(Integer.valueOf(valor2[0]) != numMovesP2){
                      numMovesP2 = Integer.valueOf(valor2[0]);
                      String[] out8 = {"getLastPlay","player1"};
                      List<String> newMove2 =p.sendToServer(out8);
                      String[] newPlay2 = newMove2.get(0).split("::");
                      System.out.println("ultima jogada do P2="+newPlay2[0]);
                      if(board.cells[Integer.valueOf(newPlay2[0])].getBackground()==Color.BLACK){
                          enemyHits++;
                          board.cells[Integer.valueOf(newPlay2[0])].setBackground(Color.RED);
                      }
                      else if(board.cells[Integer.valueOf(newPlay2[0])].getBackground()==defaultBackground){
                          board.cells[Integer.valueOf(newPlay2[0])].setBackground(Color.BLUE);
                      }
                      
                  }
                  if (finish == true) break;
           
            }
             if(!finish) JOptionPane.showMessageDialog(null, "The game is over ! ");
            }    
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Leave");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(842, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(453, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        butMainSound();
        butMainSound.start();
        MainMenuGUI mm = new MainMenuGUI();
        mm.setVisible(true);
        this.setVisible(false);
        finish=true;
    }//GEN-LAST:event_jButton1ActionPerformed
    
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
            java.util.logging.Logger.getLogger(SpectateGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SpectateGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SpectateGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SpectateGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SpectateGame(1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

    }