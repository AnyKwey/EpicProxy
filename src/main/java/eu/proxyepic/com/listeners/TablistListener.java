package eu.proxyepic.com.listeners;

import eu.proxyepic.com.Proxy;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by AnyKwey
 */
public class TablistListener implements Listener {

    @EventHandler
    public void onTab(PostLoginEvent e){
        ProxiedPlayer player = e.getPlayer();
        player.setTabHeader(new TextComponent(Proxy.getInstance().getConfig("config").getString("tab.header").replace("&", "§")), new TextComponent(Proxy.getInstance().getConfig("config").getString("tab.footer").replace("&", "§")));
    }
}
