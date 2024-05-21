package me.abacate.clanpoints.listeners;

import com.mongodb.client.MongoDatabase;
import me.abacate.clanpoints.ClanPoints;
import me.abacate.clanpoints.config.ConfigManager;
import me.abacate.clanpoints.managers.ClanManager;
import me.abacate.clanpoints.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.checkerframework.checker.units.qual.C;

public class PlayerDeathListener implements Listener {
    private ConfigManager configManager;
    private PlayerManager playerManager;
    private ClanManager clanManager;
    public PlayerDeathListener(MongoDatabase database, ClanPoints plugin){
        playerManager = new PlayerManager(database);
        clanManager = new ClanManager(database);
        configManager = new ConfigManager(plugin.getConfig());
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer!= null){
            int victimPoints = playerManager.getPointsPlayer(victim);
            int killerPoints = playerManager.getPointsPlayer(killer);
            boolean isEnemy = clanManager.isEnemy(clanManager.getClanFromPlayer(victim), clanManager.getClanFromPlayer(killer));
            if(isEnemy){
                if(victimPoints == 0){
                    playerManager.setPointsPlayer(killer,configManager.getEnemyPoints());
                }else{
                    playerManager.setPointsPlayer(killer,killerPoints+victimPoints);
                    playerManager.setPointsPlayer(victim,0);
                }
            }else{
                if(victimPoints == 0){
                    playerManager.setPointsPlayer(killer,configManager.getKillPoints());
                }else{
                    playerManager.setPointsPlayer(killer,killerPoints+victimPoints);
                    playerManager.setPointsPlayer(victim,0);
                }
            }
        }
    }
}
