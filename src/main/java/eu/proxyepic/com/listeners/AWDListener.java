package eu.proxyepic.com.listeners;

import eu.proxyepic.com.Proxy;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;

/**
 * Created by AnyKwey
 */
public class AWDListener implements Listener {

    private Configuration config = Proxy.getInstance().getConfig("antiworlddownloader");
    public static HashMap<String, Boolean> disableMap = new HashMap<>();

    @EventHandler
    public void onPluginMessageEvent(PluginMessageEvent e) {
        Connection p = e.getSender();
        if ("WDL|INIT".equalsIgnoreCase(e.getTag()) && e.getSender() instanceof net.md_5.bungee.api.connection.ProxiedPlayer)
            p.disconnect((BaseComponent)new TextComponent(color(this.config.getString("kick-message"))));
        if ("PERMISSIONSREPL".equalsIgnoreCase(e.getTag()) && ((Boolean)disableMap.get("worldDownloader")).booleanValue() && (new String(e.getData())).contains("mod.worlddownloader"))
            p.disconnect((BaseComponent)new TextComponent(color(this.config.getString("kick-message"))));
    }

    public String color(String s) {
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }
}
