
package Data;

import java.util.*;
import java.sql.*;

/**
 * Implementa a ligação à base de dados.
 * @author lpro1612
 */

public class DBClass {
    Connection con = null;
    /**
     * Inicializa uma nova conexão à base dados.
     */
    
    public DBClass(){
        try { 
            Class.forName("org.postgresql.Driver"); 
        } catch (ClassNotFoundException e) {
                System.err.println("error class not found");
        }
        // Connect to the database
        String user = "lpro1612";
        String password = "J!2391hel";
        String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";

        try {
            con = DriverManager.getConnection( url, user, password); 
        } catch (SQLException e){
                System.err.println("EEROR! couldn't get connection to DB");
        }
        System.out.println("Connected to DataBase !");
        
    }
    /**
     * Insere na base de dados um novo user com um determinado email, username, password, primeiro nome e último nome.
     * @param email Email do novo user.
     * @param username Username do novo user.
     * @param password Password do novo user.
     * @param firstName Primeiro nome do novo user.
     * @param lastName Último nome do novo user.
     * @return Uma String com "YOU ARE NOW REGISTERED" em caso de sucesso, uma String com "Username already in use" caso o username já esteja em uso ou "Email already in use" caso o email já esteja em uso.
     */
    public String writeToDB(String email, String username, String password, String firstName, String lastName){
        
        try {
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT username FROM users WHERE username='" +username+"'";
             ResultSet inUse=st.executeQuery(sql);
             int i=0;
             while(inUse.next())
                 i++;
             if(i>0){
                return "Username already in use";
             }
             
             sql="SELECT email FROM users WHERE email='" +email+"'";
             inUse=st.executeQuery(sql);
             i=0;
             while(inUse.next())
                 i++;
             if(i>0){
                 return "Email already in use";
               
             }
            int s = st.executeUpdate("INSERT INTO users VALUES (default, '" + email + "', '" + username + "','" + password+ "','" + firstName + "','" + lastName + "', 0, 0, 0)");
            con.close();
        } catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
        }
        
        return "YOU ARE NOW REGISTERED";
    }
    
    /**
     * Verifica se o par username e password existe na base de dados.
     * @param username Username a verificar.
     * @param password Password a verificar.
     * @return Uma String com "LOGGED IN" em caso de sucesso ou uma string com "Login is not correct" em caso de erro.
     */
    
    public String CheckLogin(String username, String password){
        
        
        boolean check=false; 
         try {
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE username='" +username+"' AND password='" + password +"'";
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next())
            {
                Boolean valid = rs.getBoolean("valid");
                if(valid == true)
                    check=true;
            }
            con.close();
            if(check==false)
                return "Login is not correct";
            
         }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
            return ex.getMessage();
         }
         
         System.out.println("CHECK LOGIN COMPLETED.");
         setOnline(username);
        return "LOGGED IN";  
    } 
    /**
     * Coloca um user como online.
     * @param username - Username do jogador que irá ficar online.
     */
    public void setOnline(String username){
        try {
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql= "UPDATE users SET online=true WHERE username='"+username+"'";
             st.execute(sql);
             con.close();
        }catch (SQLException ex) {
            System.out.println("Error seting online "+ex);
         }
        
        
    }
    /**
     * Coloca um user como offline.
     * @param username - username do usuário que irá ficar offline.
     * @return Uma String com o resultado.
     */
    public String setOffline(String username){
        try {
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql= "UPDATE users SET online=false WHERE username='"+username+"'";
            st.executeUpdate(sql);
            con.close();
        }catch (SQLException ex) {
            System.out.println("Error seting offline "+ex);
         }
        
        return "logout";
    }
    
    /**
     * Verifica se um email existe na base de dados.
     * @param email Email a verificar. 
     * @return Uma string com New Password sent." em caso de sucesso ou "Email does not exist" em caso de erro.
     */
    public String CheckEmail(String email){
        boolean check=false;
        
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE email='" +email+"'";
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next())
                    check=true;
            con.close();
            if(check==false)
                return "Email does not exist";
            
         }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
    
        return "New Password sent.";  
    }
    /**
     * Procura,na base de dados, o username relativo a um determinado email.
     * @param email Email associado ao username pretendido.
     * @return Uma String com o username.
     */
    public String getUser(String email){
        String username = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE email='" +email+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
                username=rs.getString("username");
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
       
        return username;
        
    }
    /**
     * Procura, na base de dados, a password relativa a um determinado email.
     * @param email Associado a password pretendida.
     * @return Uma String com a password pretendida.
     */
    
    public String getPassword(String email){
        String password = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT password FROM users WHERE email='" +email+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
                password=rs.getString("password");
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        
        return password;
        
    }
    
    /**
     * Procura,na base de dados, o Primeiro Nome relativo a um determinado username.
     * @param username Username associado ao primeiro nome pretendido.
     * @return Uma string com o nome pretendido.
     */
    
    public String getFirstName(String username)
    {
        String firstName = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
                firstName = rs.getString("firstname");
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        
        return firstName;
        
    }
    /**
     * Procura, na base de dados, o Último Nome relativo a um determinado username.
     * @param username Username associado ao username pretendido.
     * @return Uma String com o nome pretendido.
     */
    public String getLastName(String username)
    {
        String lastName = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
                lastName = rs.getString("lastname");
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        
        return lastName;
        
    }
    /**
     * Procura, na base de dados, o email relativo a um determinado username.
     * @param username Username associado ao email pretendido.
     * @return Uma string com o email pretendido.
     */
    public String getEmail(String username)
    {
         String email = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
                email = rs.getString("email");
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        
        return email;
         
    }
    /**
     * Procura, na base de dados, o número de vitórias relativas a um determinado username.
     * @param username Username associado ao número de jogos ganhos pretendidos.
     * @return Uma string com o número de vitórias pretendidas.
     */
    public String getGamesWon(String username)
    {
          String won = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
                won = rs.getString("gameswon");
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        
        return won;
        
    }
    /**
     * Procura, na base de dados, o número de derrotas relativas a um determinado username.
     * @param username Username associado ao número de derrotas pretendido.
     * @return Uma string com o número de derrotas pretendido.
     */
    public String getGamesLost(String username)
    {
      String lost = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
                lost = rs.getString("gameslost");
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        
        return lost;
    }
    /**
     * Procura,na base de dados, o número de acertos relativo a um determinado username.
     * @param username Username associado ao número de acertos pretendido.
     * @return Uma String com o número de acertos pretendido.
     */
    public String getHits(String username)
    {
          String hits = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
                hits = rs.getString("hits");
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        
        return hits;
        
    }
    /**
     * Insere na base de dados uma nova password relativa a um determinado username.
     * @param username Username associado à password que se pretende mudar.
     * @param newPassword Nova password.
     * @return Uma String com "Password modifyed with success" em caso de sucesso, "Error on modifying Password" em caso de erro.
     */
    public String modifyPassword(String username, String newPassword)
    {
        String sql = "UPDATE users SET password = '" + newPassword + "' WHERE username = '" + username + "';";
       
       try{
           String user = "lpro1612";
           String passw = "J!2391hel";
           String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
           con = DriverManager.getConnection( url, user, passw); 
           Statement st = con.createStatement();
           st.executeUpdate(sql);
           con.close();
       }
       catch(SQLException e)
       {
           System.out.println("ERROR INSERTING: " + e);
           return "Error on modifying Password";
       }
       
       return "Password modifyed with success";
    }
    /**
     * Procura na base de dados todos os jogadore inscritos.
     * @return Uma String com todos os usernames do jogadores inscritos separados pelo caracter especial "::".
     */
    public String getPlayers(){
        String players = "";
         ResultSet rs ;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE online=true ";
            rs = st.executeQuery(sql);
             while(rs.next()){
                 String username=rs.getString("username");
                 players=players+"::"+username;
            }
            con.close(); 
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
      
        return players;
    } 
    /**
     * Procura,na base de dados, o id relativo a um determinado username(owner).
     * @param owner Username cujo id se pretende obter.
     * @return Um inteiro com o id pretendido.
     */
    public int getId(String owner){
        int id=0;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE username='" + owner + "'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
                id = rs.getInt("id");
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        
        return id;
    }
    /**
     * Procura,na base de dados, o username relativo a um determinado id.
     * @param id Id, na base de dados, do username que se pretende.
     * @return Uma String com o username pretendido.
     */
    public String getUserById(int id){
        String owner="";
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM users WHERE id = " + id;
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
                owner = rs.getString("username");
            System.out.println("owner: " + owner);
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        System.out.println(owner);
        return owner;
    }
    /**
     * Insere na base de dados um novo jogo com um determinado nome, password, tipo e Id do criador.
     * @param gameName Nome do novo jogo.
     * @param password Password do novo jogo. Null no caso do jogo ser "Public".
     * @param type Tipo do novo jogo, "Public" ou "Private".
     * @param ownerId ID do criador do jogo na base de dados. 
     * @return Uma String com "Game Created" em caso de sucesso, "Game name already in use" em caso de o nome do jogo já estar em utilização.
     */
    public String insertGame(String gameName, String password, String type, int ownerId){
         try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            String state = "waiting";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="INSERT INTO games (id,name,password,type,creator,state) VALUES(default,'" + gameName + "', '" + password + "','" + type+ "','" + ownerId + "','" + state + "')";
            int s = st.executeUpdate(sql);
            con.close();
         }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
            return "Game name already in use";
         }
         return "Game Created";
    }
    /**
     * Procura na base de dados todos os jogos já criados.
     * @param gamestate
     * @return Uma lista de Strings com as informações de cada jogo em cada posiçao da lista. 
     */
    public List<String> getGames(String gamestate){
          
         ResultSet rs ;
         int i=0;
          List<String> list = new ArrayList<>();
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM games WHERE state='" + gamestate + "'";
            rs = st.executeQuery(sql);

             while(rs.next()){
                 int id = rs.getInt("id");
                 String gameName=rs.getString("name");
                 String type=rs.getString("type");
                 int ownerId=rs.getInt("creator");
                 System.out.println("ownerId: " + ownerId);
                 String owner=getUserById(ownerId);
                 String state=rs.getString("state");
                 if(gamestate.equals("end"))
                 {
                     int opponent = rs.getInt("opponent");
                     state = getUserById(opponent);
                 }
                 
                String str=id+"::"+gameName+"::"+type+"::"+owner+"::"+state;
                list.add(str);
                 i++;
             }
        con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        
      
  
        return list;
    }
    /**
     * Procura,na base de dados, o id do jogo relativo a um determinado nome de jogo.
     * @param gameName Nome do jogo cujo Id se pretende.
     * @return Uma String com o id do jogo pretendido.
     */
    public String getGameId (String gameName){
       String id="";
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM games WHERE name='"+gameName+"'";
            ResultSet rs = st.executeQuery(sql);

            if(rs.next()){
                id = rs.getString("id");
            }
            con.close();
        }catch(SQLException ex){
            System.out.println("Error getting game id"+ ex);
        }
        return id;
    }
    /**
     * Obtem o id do owner de um jogo.
     * @param gameId Id do jogo do qual se pretende obter o owner.
     * @return Um inteiro com o id do owner do jogo pretendido.
     */
    public int getGameOwner(int gameId)
    {
        int id = 0;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM games WHERE id=" + gameId;
            ResultSet rs = st.executeQuery(sql);

            if(rs.next()){
                id = rs.getInt("creator");
            }
            con.close();
        }catch(SQLException ex){
            System.out.println("Error getting game id"+ ex);
        }
        return id;   
    }
    /**
     * Coloca na base de dados um jogador que participou num jogo.
     * @param gameId Id do jogo.
     * @param playerId Id do jogador que participou no jogo.
     * @return Uma String com o resultado.
     */
    public String setOpponent(int gameId, int playerId)
    {
         try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="UPDATE games SET opponent= " + playerId + " WHERE id = " + gameId + ";";
            int rs = st.executeUpdate(sql);
            con.close();
        }catch(SQLException ex){
             System.out.println("Error on ending game "+ ex);
        }
         return "opponent was set";
    }
    
    /**
     * Insere na base de dados o jogo com o id indicado em argumento, na tabela onde irá ser guardada informação durante o decorrer do jogo. 
     * @param id id do jogo a guardar
     * @return String "Game Started" em caso de sucesso; "GAME already exists" no caso do jogo já existir na tabela; "Error on startGame" em caso de erro
     */
    public String startGame(int id)
    {
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql = "SELECT * FROM game_info WHERE id =" + id +";";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                 return "GAME already exists";
             }
            
            sql="INSERT INTO game_info VALUES(" + id + ");";
            
            int s = st.executeUpdate(sql);
            con.close();
        }
        catch (SQLException ex) {
            System.out.println("error on startGame: "+ex);
            return "Error on startGame";
        }
       
        return "Game Started";
    }
    /**
     * Insere na base de dados as posições dos barcos de um determinado jogador para um determinado jogo.
     * @param id id do jogo em que se pretende colocar os barcos.
     * @param player Jogador que está a colocar os barcos, "player1" ou "player2".
     * @param boats Posições dos barcos a colocar concatenadas numa só string.
     * @return Uma String com "Boats placed" em caso de sucesso; "Error placing boats" em caso de erro.
     */
    public String place_boats(int id, String player, String boats)
    {
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql;
            if(player.equals("player1"))
                sql="UPDATE game_info SET boats_p1 = '" + boats + "' WHERE id = " + id;
            else
                sql="UPDATE game_info SET boats_p2 = '" + boats + "' WHERE id = " + id;
            
            int s = st.executeUpdate(sql);
            con.close();
        }
         
        catch (SQLException ex) {
            System.out.println("error placing boats: "+ex);
            return "Error placing boats";
        }
        return "Boats placed";
    }
    /**
     * Verifica se as posições dos barcos do jogador indicado estão guardadas na base de dados.
     * @param id id do jogo a verificar.
     * @param player jogador a verificar.
     * @return 
     */
    public String check_boats(int id, String player)
    {
        String boats = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM game_info WHERE id = " + id + ";";
            ResultSet rs = st.executeQuery(sql);
            if(player.equals("player1"))
            {
                if(rs.next())
                    boats = rs.getString("boats_p2");
            }
            else
            {
                if(rs.next())
                    boats = rs.getString("boats_p1");
            }
            con.close();
        }catch(SQLException ex){
            System.out.println("Error on checking boats: "+ ex);
        }
        System.out.println(boats);
        return boats;
    }
    /**
     * Termina um jogo.
     * @param id Id do jogo a terminar.
     */
    public void endGame(int id){
        
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="UPDATE games SET state='end' WHERE id = " + id + ";";
            int rs = st.executeUpdate(sql);
            con.close();
        }catch(SQLException ex){
             System.out.println("Error on ending game "+ ex);
        }
    }
    /**
     * Colocar um jogo como a decorrer.
     * @param id Id do jogo que está a decorrer.
     */
    public void gameIsLive(int id){
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="UPDATE games SET state='live' WHERE id = " + id + ";";
            int rs = st.executeUpdate(sql);
            con.close();
        }catch(SQLException ex){
             System.out.println("Error on starting game: "+ ex);
        }
        
    }
    
    /**
     * Verifica as jogadas do player indicado guardadas na base de dados.
     * @param id id do jogo a verificar.
     * @param player jogador a verificar.
     * @return String com as jogadas em caso de sucesso; "Error on checking moves" em caso de erro.
     */
    public String check_moves(int id, String player)
    {
        String moves = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM game_info WHERE id = " + id + ";";
            ResultSet rs = st.executeQuery(sql);
            if(player.equals("player1"))
            {
                if(rs.next())
                    moves = rs.getString("moves_p1");
            }
            else
            {
                if(rs.next())
                    moves = rs.getString("moves_p2");
            }
            con.close();
        }catch(SQLException ex){
            System.out.println("Error on checking moves: "+ ex);
            return "Error on checking moves";
        }
        System.out.println(moves);
        if(moves == null)
            moves = "";
        
        return moves;
    }
    /**
     * Guarda na base de dados a jogada de um determinado jogador num determinado jogo.
     * @param id id do jogador que efectua a jogada.
     * @param player 
     * @param move
     * @return Uma String com uma mensagem para o utilizador saber o resultado na inserção na base de dados.
     */
    public String save_move(int id, String player, String move)
    {
        String moves = null;
        try{
            moves = check_moves(id, player);
            if(moves == null)
                moves = move;
            else
                moves = moves + ":" + move;
            
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql;
            if(player.equals("player1"))
                sql = "UPDATE game_info SET moves_p1 = '" + moves + "' WHERE id = " + id;
            else
                sql = "UPDATE game_info SET moves_p2 = '" + moves + "' WHERE id = " + id;
            
            int s = st.executeUpdate(sql);
           
            con.close();
        }catch(SQLException ex){
            System.out.println("Error on saving move: "+ ex);
        }
        return "Move successfully saved";
    }
    /**
     * Actualiza a informação de um jogador após um jogo.
     * @param winner - Username do vencedor do jogo.
     * @param loser - Username do derrotado do jogo.
     * @param hitsWinner - Número de acertos do vencedor.
     * @param hitsLoser - Número de acertos do derrotado.
     */
    public void updateUserInfo(String winner, String loser, int hitsWinner, int hitsLoser){
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="UPDATE users SET gameswon=gameswon+1, hits=hits+'"+hitsWinner+"' WHERE username = '" +winner+"'";
           st.executeUpdate(sql);
            String sql2="UPDATE users SET gameslost=gameslost+1, hits=hits+'"+hitsLoser+"' WHERE username ='" +loser+"'";
             st.executeUpdate(sql2);
        }catch(SQLException e){
            System.out.println("Error updating game ending info: "+ e);
        }
    }
    /**
     * Actualiza a informação de um jogo.
     * @param id - Id do jogo cuja informação vai ser actualizada.
     * @param boatsPlayer1 - Posições dos barcos do owner do jogo.
     * @param boatsPlayer2 Posições dos barcos do adversário do jogo.
     * @param movesPlayer1 Jogadas efectuadas pelo owner do jogo.
     * @param movesPlayer2 Jogadas efectuadas pelo adversário.
     */
    public void updateGameInfo(int id, String boatsPlayer1, String boatsPlayer2, String movesPlayer1, String movesPlayer2){
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="UPDATE game_info SET boats_p1='" +boatsPlayer1+"',boats_p2='" + boatsPlayer2+"', moves_p1='"+movesPlayer1+"', moves_p2='"+movesPlayer2+"' WHERE id ='"+id+"'";
           st.executeUpdate(sql);
           con.close();
           }catch(SQLException e){
            System.out.println("Error updating game info: "+ e);
        }
    }
    /**
     * Envia uma mensagem para o chat de jogo.
     * @param msg - Mensagem a enviar.
     * @param username - Username do usuário que está a enviar a mensagem.
     * @param id - Id do jogo em que a mensageme está a ser enviada.
     */
    public void sendMsg (String msg, String username, int id){
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="INSERT INTO chat VALUES (default, '" + id + "', '" + username +"', '" +msg + "')";
           st.executeQuery(sql);
           con.close();
           }catch(SQLException e){
            System.out.println("Error sending chat message: "+ e);
        }
    }
    /**
     * Obter a última mensagem de chat de jogo.
     * @param id Id do jogo.
     * @return String com a mensagem de chat.
     */
    public String getChatMsg (int id) {
        String result="";
        String username="";
        String msg ="";
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * from chat WHERE game_id= '"+id+ "' order by id desc limit 1";
            ResultSet rs =st.executeQuery(sql);
            if(rs.next()){
                username = rs.getString("sender");
                msg = rs.getString("message");
                result= username + "::--::"+msg;
            }
            con.close();
           }catch(SQLException e){
            System.out.println("Error sending chat message: "+ e);
        }
        
        return result;
    }
    /**
     * Obtem informação de um jogo como posições de barcos e jogadas.
     * @param id Id do jogo a obter informação.
     * @return String com a informação pretendida.
     */
  public String getGameInfo(int id){
          
         ResultSet rs ;
         String gameInfo = null;
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="SELECT * FROM game_info WHERE id=" + id;
            rs = st.executeQuery(sql);

            if(rs.next()){
                 String boatsP1=rs.getString("boats_p1");
                 String boatsP2=rs.getString("boats_p2");
                 String movesP1=rs.getString("moves_p1");
                 String movesP2=rs.getString("moves_p2");
                
                 gameInfo = boatsP1 + "::" + boatsP2 + "::" + movesP1 + "::" + movesP2;
            }
            con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }
        
      
  
        return gameInfo;
    }
    /**
     * Bane um usuário.
     * @param username - Username do usuário a banir. 
     */
    public void banUser(String username){
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="UPDATE users SET valid = FALSE WHERE username = '" + username + "'"; 
           st.executeUpdate(sql);
           con.close();
           }catch(SQLException e){
            System.out.println("Error updating game info: "+ e);
        }
    }
    /**
     * Tirar o ban de um jogador.
     * @param username - Username do jogador ao qual se prentede tirar o ban.
     */
     public void unbanUser(String username){
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="UPDATE users SET valid = TRUE WHERE username = '" + username + "'"; 
           st.executeUpdate(sql);
           con.close();
           }catch(SQLException e){
            System.out.println("Error updating game info: "+ e);
        }
    }
     /**
      * Obtem a lista de jogadores ordenada pelo número de vitórias.
      * @return Uma Lista de Strings em que em cada posição tem informação sobre um usuário.
      */
     public List<String> getUsersRanking(){
          
         ResultSet rs ;
         int i=0;
          List<String> list = new ArrayList<>();
        try{
            String user = "lpro1612";
            String passw = "J!2391hel";
            String url = "jdbc:postgresql://dbm.fe.up.pt/lpro1612";
            con = DriverManager.getConnection( url, user, passw); 
            Statement st = con.createStatement();
            String sql="select * from users ORDER BY gameswon DESC";
            rs = st.executeQuery(sql);

             while(rs.next()){
                 String username=rs.getString("username");
                 int gameswon=rs.getInt("gameswon");
                 int gameslost=rs.getInt("gameslost");
                 int hits=rs.getInt("hits");
                  
                String str=username+"::"+gameswon+"::"+gameslost+"::"+hits;
                list.add(str);
                 i++;
             }
        con.close();
        }catch (SQLException ex) {
            System.out.println("error inserting: "+ex);
         }

        return list;
    }
 
}
