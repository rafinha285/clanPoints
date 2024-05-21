package me.abacate.clanpoints.managers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.abacate.clanpoints.types.ClanMongo;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class ClanManager {
    private MongoCollection<Document> clanCollection;
    private MongoCollection<Document> playerCollection;
    private PlayerManager playerManager;
    public ClanManager(MongoDatabase database){
        playerManager = new PlayerManager(database);
        playerCollection = database.getCollection("playerPoints");
        clanCollection = database.getCollection("clansPoints");
    }
    public ClanMongo getClan(String clanName){
        Document clanDoc = clanCollection.find(eq("name",clanName)).first();
        ClanMongo clan = new ClanMongo(clanDoc.getString("name"),
                (List<UUID>) clanDoc.get("members"),
                clanDoc.getInteger("points"),
                (List<String>) clanDoc.get("enemies"),
                (List<String>) clanDoc.get("allys"));
        return clan;
    }
    public String getClanFromPlayer(Player player){
        Document clanDoc = clanCollection.find(in("members",player.getUniqueId().toString())).first();
        return clanDoc.getString("name");
    }
    public boolean isEnemy(String clan1,String clan2){
//        String clanName = playerManager.getClan(player);
        ClanMongo clan = getClan(clan1);
        return clan.getEnemies().contains(clan2);
    }
}
