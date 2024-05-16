package crystalcube.plugintemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MyConfig {
    private final File configFile;
    private final FileConfiguration config;
    private final Plugin plugin;
    private boolean isEnabled;

    public MyConfig(Plugin plugin) {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        this.plugin = plugin;
        try {
            firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
        config = new YamlConfiguration();
        loadConfig();
    }

    public void saveConfig() {
        try {
            config.set("isEnabled", isEnabled);
            config.save(configFile); //saves the FileConfiguration to its File
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void firstRun(){
        if (!configFile.exists()) {                        // checks if the yaml does not exists
            configFile.getParentFile().mkdirs();         // creates the /plugins/<pluginName>/ directory if not found
            copy(plugin.getResource("config.yml"), configFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
        }
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            config.load(configFile); //loads the contents of the File to its FileConfiguration
            isEnabled = config.getBoolean("isEnabled");
            plugin.getLogger().config("config is loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isEnabled(){
        return isEnabled;
    }

    public void setIsEnabled(boolean value){
        isEnabled = value;
        saveConfig();
    }
}