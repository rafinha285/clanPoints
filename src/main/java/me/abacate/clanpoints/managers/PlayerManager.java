package me.abacate.clanpoints.managers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import me.abacate.clanpoints.types.PlayerMongo;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerManager {

    private MongoCollection playerCollection;
    public PlayerManager(MongoDatabase database){
        this.playerCollection = database.getCollection("playerPoints");
    }
    public boolean isInDatabase(Player player){
        Document query = new Document("_id",player.getUniqueId());
        Object playerDoc = playerCollection.find(query).first();
        return playerDoc!=null;
    }
    public void checkAndAddPlayer(Player player) {
        Document query = new Document("_id",player.getUniqueId().toString());
        Object playerDoc = playerCollection.find(query).first();
        PlayerMongo playerMongo = new PlayerMongo(player,"",0);
//        Bukkit.getLogger().info(playerDoc.toString());
        if (playerDoc == null) {
            // O jogador não está no banco de dados, então adicionamos
            Document newPlayer = new Document("_id", playerMongo.getId().toString())
                    .append("name", playerMongo.getName())
                    .append("clan", playerMongo.getClan())  // Adiciona o jogador com um valor de clã vazio
                    .append("points", playerMongo.getPoints());
            playerCollection.insertOne(newPlayer);
            Bukkit.getLogger().info("adicionado");
        }
    }
}
