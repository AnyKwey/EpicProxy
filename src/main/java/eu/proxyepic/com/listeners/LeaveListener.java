package eu.proxyepic.com.listeners;

import eu.epicraft.com.data.yaml.PlayerInfos;
import eu.epicraft.com.manager.moderation.ModManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerDisconnectEvent e){
        ProxiedPlayer player = e.getPlayer();
        PlayerInfos.updateLastConnection(player.getName());
        if(ModManager.isInMod(player.getUniqueId())){
            ModManager.remove(player.getUniqueId());
        }
    }
}
