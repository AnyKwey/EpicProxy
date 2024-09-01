package eu.proxyepic.com.events;

import eu.proxyepic.com.commands.*;
import eu.proxyepic.com.listeners.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * Created by AnyKwey
 */
public class EventsManager {

    private Plugin plugin;

    public EventsManager(Plugin plugin){
        this.plugin = plugin;
    }

    public void registerListeners(){
        registerListener(new JoinListener());
        registerListener(new TablistListener());
        registerListener(new PingListener());
        registerListener(new ChatListener());
        registerListener(new AWDListener());
        registerListener(new LeaveListener());
    }

    public void registerCommands(){
        registerCommand("lobby", new LobbyCommand());
        registerCommand("ban", new BanCommand());
        registerCommand("unban", new UnBanCommand());
        registerCommand("mute", new MuteCommand());
        registerCommand("unmute", new UnMuteCommand());
        registerCommand("mp", new MessagePrivateCommand());
        registerCommand("reply", new ReplyCommand());
        registerCommand("whitelist", new WhitelistCommand());
        registerCommand("maintenance", new MaintenanceCommand());
        registerCommand("staff", new StaffCommand());
    }


    private void registerListener(Listener listener){
        PluginManager pm = ProxyServer.getInstance().getPluginManager();
        pm.registerListener(plugin, listener);
    }

    private void registerCommand(String cmd, Command command){
        plugin.getProxy().getPluginManager().registerCommand(plugin, command);
    }
}
