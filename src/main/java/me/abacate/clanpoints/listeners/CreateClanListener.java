package me.abacate.clanpoints.listeners;

import Clans.Events.ClanCreateEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CreateClanListener implements Listener {

    @EventHandler
    public void onClanCreate(ClanCreateEvent event) {
        Player owner = event.getOwner();
        String clanName = event.getClanName();

        // Agora você pode fazer algo com o proprietário e o nome do clã
        // Por exemplo, imprimir uma mensagem no console:
        System.out.println(owner.getName() + " criou um novo clã chamado " + clanName);
    }
}