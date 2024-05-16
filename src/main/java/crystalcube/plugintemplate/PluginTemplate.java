package crystalcube.plugintemplate;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PluginTemplate extends JavaPlugin {

    private final MyConfig config = new MyConfig(this);
    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("plugintemplate")).setExecutor(new MyCommandExecutor(this));
    }

    @Override
    public void onDisable() {
        config.saveConfig();
    }

    public MyConfig getMyConfig(){ return config; }
}
