package eu.proxyepic.com.listeners;

import eu.epicraft.com.data.yaml.RankUnit;
import eu.epicraft.com.manager.moderation.MuteManager;
import eu.proxyepic.com.Proxy;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent e){
        ProxiedPlayer player = (ProxiedPlayer) e.getSender();
        MuteManager.checkDuration(player.getUniqueId());
        if(MuteManager.isMute(player.getUniqueId())){
            if(e.getMessage().startsWith("/")) return;
            e.setCancelled(true);
            player.sendMessage(MuteManager.forbideChatMessage(player.getUniqueId()));
        }

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String stringDate = dateFormat.format(date);
        addLog(player.getName(), "[" + stringDate + " | " + Proxy.getInstance().getProxy().getPlayer(player.getUniqueId()).getServer().getInfo().getName() + "] " + RankUnit.getRank(player.getUniqueId()).getName().replace("§", "") + " " + player.getName() + " » " + e.getMessage());
    }
    public static void addLog(String name, String message) {
        try {
            File file = new File(Proxy.getInstance().getDataFolder() + "/logs/", name + ".txt");
            if (!file.getParentFile().exists()) file.getParentFile().mkdir();
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(message);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
