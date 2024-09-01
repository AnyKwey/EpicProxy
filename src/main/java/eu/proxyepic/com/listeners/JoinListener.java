package eu.proxyepic.com.listeners;

import eu.epicraft.com.data.yaml.PlayerInfos;
import eu.epicraft.com.data.yaml.PlayerSettings;
import eu.epicraft.com.data.yaml.RankUnit;
import eu.epicraft.com.manager.moderation.StaffManager;
import eu.proxyepic.com.Proxy;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.event.EventHandler;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by AnyKwey
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent e){
        ProxiedPlayer player = e.getPlayer();
        PlayerInfos.update(player.getUniqueId());
        PlayerSettings.updateSettings(player);

        if(Proxy.getInstance().getConfig("whitelist").getBoolean("whitelist")){
            player.disconnect("§6§lEpicraft\n\n§cUne maintenance est mise en place actuellement.\n§9discord.gg/epicraft");
        }

        if(RankUnit.getRank(player.getUniqueId()).getPower() >= RankUnit.STAFF.getPower()){
            StaffManager.addToStaffList(player);
        }

        Proxy.getInstance().getProxy().getScheduler().schedule(Proxy.getInstance(), new Runnable(){

            @Override
            public void run() {
                PlayerInfos.updatePlayTime(player.getName());
            }
        }, 0L, 1L, TimeUnit.SECONDS);
   }
}
