/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Data.DBClass;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe auxiliar que fornece informações sobre um jogo
 * @author jmse8
 */
public class GameUtil {

    DBClass db = new DBClass();
    private final String[] data;
    List<String> outputLine = new ArrayList<>();

    public GameUtil(String[] data) {
        this.data = data;
    }

    /**
     * Método que insere um novo jogo na base de dados
     * @return Lista de Strings com a resposta a enviar ao cliente: "Game Created" em caso de sucesso, "Game name already in use" em caso de o nome do jogo já estar em utilização.
     */
    public List<String> insertGame() {
        String gameName, password, type;
        int owner;
        gameName = data[1];
        password = data[2];
        owner = db.getId(data[3]);
        type = data[4];
        outputLine.add(db.insertGame(gameName, password, type, owner));

        return outputLine;
    }

    /**
     * Método que guarda o player2 de um jogo na base de dados
     * @return Lista de Strings com a mensagem a enviar ao cliente: "opponent was set" ou mensagem de erro
     */
    public List<String> setOpponent() {
        int gameId = Integer.parseInt(data[1]);
        String playerName = data[2];
        int playerId = db.getId(playerName);
        outputLine.add(db.setOpponent(gameId, playerId));

        return outputLine;
    }

    /**
     * Método que vai buscar o id de um jogo à base de dados
     * @return Id do jogo
     */
    public String getGameId() {
        String gameName = data[1];
        String id;
        id = db.getGameId(gameName);

        return id;
    }

    /**
     * Método que indica à base de dados que um jogo foi iniciado
     * @return Lista de Strings com a mensagem a enviar ao cliente: String "Game Started" em caso de sucesso; "GAME already exists" no caso do jogo já existir na tabela; "Error on startGame" em caso de erro
     */
    public List<String> startGame() {
        int id = Integer.parseInt(data[1]);

        outputLine.add(db.startGame(id));
        outputLine.add(data[1]);

        return outputLine;
    }

    /**
     * Método que, sendo-lhe dado o id de um jogo, retorna o criador do mesmo
     * @return Lista de Strings com o nome do criador do jogo
     */
    public List<String> getOwner() {
        int id = Integer.parseInt(data[1]);
        int ownerId = db.getGameOwner(id);
        outputLine.add(db.getUserById(ownerId));

        return outputLine;
    }
    
    /**
     * Método que insere na base de dados a posição dos barcos de um jogador
     * @return Lista de Strings com a mensagem a enviar ao cliente: "Boats placed" em caso de sucesso; "Error placing boats" em caso de erro.
     */
    public List<String> placeBoats() {
        int id = Integer.parseInt(data[1]);
        String player = data[2];
        outputLine.add(db.place_boats(id, player, data[3]));

        return outputLine;
    }
}
