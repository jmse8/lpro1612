/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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

import Logic.*;
import Communication.*;
import static java.lang.Integer.max;
import static java.lang.Thread.sleep;

/**
 * Implementa a interface gráfica para rever um jogo já terminado
 * @author jmse8
 */
public class BoardReviewGUI extends javax.swing.JFrame {

    int i = 0;
    int k = 0;
    int id;
    String player;
    String myMoves, opponentMoves;
    boolean start_game = false;
    String play;
    Color background;
    int vertical = 1;
    String lastMove = "";
    boolean finish = false;
    String username = "";
    String player1, player2;
    boolean leave=false;
    
   /**
    * Cria e inicializa o tabuleiro do jogo a rever, com os barcos dos dois jogadores nas respetivas posições
    * @param id id do jogo
    * @param player1 Username do jogador 1
    * @param player2 Username do jogador 2
    */
    public BoardReviewGUI(int id, String player1, String player2) {

        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.player = player1;

        gameInfo gameinfo = new gameInfo(id);
        String[] gameInfo = gameinfo.getInfo();
        String[] boatsP1 = gameInfo[0].split(",");
        String[] boatsP2 = gameInfo[1].split(",");
        String[] movesP1 = gameInfo[2].split(",");
        String[] movesP2 = gameInfo[3].split(",");

        JPanel gui = new JPanel(new GridLayout(1, 2, 50, 5));
        gui.setBorder(new EmptyBorder(50, 25, 5, 5));
        BoardReviewGUI.OwnBoard board = new BoardReviewGUI.OwnBoard();
        gui.add(board);
        BoardReviewGUI.EnemyBoard enemyBoard = new BoardReviewGUI.EnemyBoard();
        gui.add(enemyBoard);
        this.setContentPane(gui);
        this.pack();
        //    username=login.getUsername();
        initComponents();
        this.setSize(1000, 650);
        board.placeBoats(boatsP1);
        enemyBoard.placeBoats(boatsP2);
        moves moves = new moves(movesP1, movesP2, board, enemyBoard);
        new Thread(moves).start();
        
    }

    /**
     * Implementa o tabuleiro do jogador 1
     */
    public class OwnBoard extends JPanel {

        boolean place_boats = false;
        private String carrier, battleship, cruiser, submarine, destroyer;
        public CellPane[] cells = new CellPane[100];

        /**
         * Inicializa e constrói um novo tabuleiro do jogador 1
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
         * Coloca os barcos do jogador 1 nas posições certas.
         * @param boats Vetor de Strings com as células ocupadas pelos barcos
         */
        public void placeBoats(String[] boats) {
            for (int i = 1; i < boats.length; i++) {
                cells[Integer.parseInt(boats[i])].setBackground(Color.DARK_GRAY);
            }
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

    /**
     * Implementa o tabuleiro do jogador 2.
     */
    public class EnemyBoard extends JPanel {

        CellPane2[] cells = new CellPane2[100];

        /**
         * Inicializa e constrói um novo tabuleiro para o jogador 2.
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
         * Coloca os barcos do jogador 2 nas posições certas.
         * @param boats Vetor de Strings com as células ocupadas pelos barcos
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
     * Simula as jogadas feitas pelos jogadores, ao ritmo de 1 jogada/s.
     */
    public class moves implements Runnable {

        String[] movesP1;
        String[] movesP2;
        BoardReviewGUI.OwnBoard board;
        BoardReviewGUI.EnemyBoard enemy;

        public moves(String[] movesP1, String[] movesP2, BoardReviewGUI.OwnBoard board, BoardReviewGUI.EnemyBoard enemy) {
            this.movesP1 = movesP1;
            this.movesP2 = movesP2;
            this.board = board;
            this.enemy = enemy;
        }

        @Override
            public void run(){
        int numberofmoves = max(movesP1.length, movesP2.length);
            int move = 0;
            int i = 0, j = 0;
            final Color defaultBackground = board.cells[1].defaultBackground;

            while (i < movesP1.length || j < movesP2.length) {
                
                if (player.equals(player1)) {
                  
                    move = Integer.parseInt(movesP1[i] );
                   System.out.println(player + "making move" + i + "/" + movesP1.length);
               
                    if (enemy.cells[move].getBackground() == defaultBackground) {
                        enemy.cells[move].setBackground(Color.BLUE);
                        player = player2;
                        i++;
                    } else if (enemy.cells[move].getBackground() == Color.DARK_GRAY) {
                        enemy.cells[move].setBackground(Color.RED);
                        i++;
                    }
                } else if (player.equals(player2)) {
                    move = Integer.parseInt(movesP2[j]);
                    System.out.println(player + "making move" + j + "/" + movesP2.length);
                    if (board.cells[move].getBackground() == defaultBackground) {
                        board.cells[move].setBackground(Color.BLUE);
                        player = player1;
                        j++;
                    } else if (board.cells[move].getBackground() == Color.DARK_GRAY) {
                        board.cells[move].setBackground(Color.RED);
                        j++;
                    }
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                }
                if(leave)break;
            }
            if(!leave){
                if (player.equals(player1)) {
                    JOptionPane.showMessageDialog(null, player1 + " won!!!");
                } else {
                    JOptionPane.showMessageDialog(null, player2 + " won!!!");
                }
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

        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("BaShip");
        jLabel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText(player1);

        jLabel2.setText(player2);

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel1)
                        .addGap(217, 217, 217)
                        .addComponent(jLabel2)
                        .addGap(82, 82, 82)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        MainMenuGUI mm = new MainMenuGUI();
        mm.setVisible(true);
        this.setVisible(false);
        leave=false;
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
            java.util.logging.Logger.getLogger(BoardReviewGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BoardReviewGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BoardReviewGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BoardReviewGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BoardReviewGUI(1, null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
