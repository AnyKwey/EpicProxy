package eu.proxyepic.com;

import eu.epicraft.com.data.mysql.MySQL;
import eu.proxyepic.com.events.EventsManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by AnyKwey
 */
public class Proxy extends Plugin {

    private static Proxy instance;

    @Override
    public void onEnable() {
        instance = this;
        getProxy().registerChannel("WDL|INIT");
        getProxy().registerChannel("PERMISSIONREPL");
        createFile("config");
        createFile("mysql");
        createFile("whitelist");
        createFile("antiworlddownloader");
        MySQL.connect(getConfig("mysql").getString("mysql.host"), getConfig("mysql").getInt("mysql.port"), getConfig("mysql").getString("mysql.database"), getConfig("mysql").getString("mysql.user"), getConfig("mysql").getString("mysql.password"));
        new EventsManager(this).registerListeners();
        new EventsManager(this).registerCommands();
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
        getProxy().unregisterChannel("WDL|INIT");
        getProxy().unregisterChannel("PERMISSIONREPL");
    }

    public static Proxy getInstance() {
        return instance;
    }

    private void createFile(String fileName){
        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), fileName + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();

                if(fileName.equals("mysql")){
                    Configuration config = getConfig(fileName);
                    config.set("mysql.host", "localhost");
                    config.set("mysql.port", 3306);
                    config.set("mysql.database", "database");
                    config.set("mysql.user", "root");
                    config.set("mysql.password", "");

                    saveConfig(config, fileName);
                }

                if(fileName.equals("config")){
                    Configuration config = getConfig(fileName);
                    config.set("version", "1.20");
                    config.set("tab.header", "&eVous jouez sur &6&lEpicraft");
                    config.set("tab.footer", "&fPlus d'info: &bepicraft.eu");
                    config.set("motd.header", "&f&l»&6&l»&b&l» &6&lEpicraft &f- Mini-Jeux Fun &b&l«&6&l«&f&l«");
                    config.set("motd.footer", "&c&lRUSH&f, &e&lBRAIN&f, &b&lSKYWARS&f, &a&lUHC&f, ...");
                    config.set("motd.description", "&cEn développement...");

                    saveConfig(config, fileName);
                }

                if(fileName.equals("whitelist")){
                    Configuration config = getConfig(fileName);
                    config.set("whitelist", false);
                    config.set("players", "[]");

                    saveConfig(config, fileName);
                }

                if(fileName.equals("antiworlddownloader")){
                    Configuration config = getConfig(fileName);
                    config.set("kick-message", "&cIl est interdit de télécharger nos maps.");

                    saveConfig(config, fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Configuration getConfig(String fileName){
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), fileName + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveConfig(Configuration config, String fileName){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), fileName + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
