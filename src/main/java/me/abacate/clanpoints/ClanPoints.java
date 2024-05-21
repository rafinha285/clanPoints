package me.abacate.clanpoints;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.abacate.clanpoints.managers.PlayerManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import Clans.Main;

public final class ClanPoints extends JavaPlugin implements Listener {
    public MongoClient mongoClient;
    public MongoDatabase database;
    private PlayerManager playerManager;
//    public MongoCollection<Document> playerPointsCollection;
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Iniciando database para clans");
        mongoClient = MongoClients.create("mongodb://192.168.1.20:27017/");
        database = mongoClient.getDatabase("minecraft");
        playerManager = new PlayerManager(database);
        Bukkit.getPluginManager().registerEvents(this, this);
//        playerPointsCollection =
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Bukkit.getLogger().info("clansManager "+player.getName());
        playerManager.checkAndAddPlayer(player);
    }

    @Override
    public void onDisable() {
        mongoClient.close();
        // Plugin shutdown logic
    }
}
