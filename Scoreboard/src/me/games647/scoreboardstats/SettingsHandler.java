package me.games647.scoreboardstats;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public final class SettingsHandler {

    private final ScoreboardStats plugin;
    private boolean pvpstats, tempscoreboard;
    private String title, temptitle;
    private int intervall, topitems, tempshow, tempdisapper;
    private java.util.Map<String, Object> items;
    private java.util.List<String> disabledworlds;

    public SettingsHandler(final ScoreboardStats instance) {
        this.plugin = instance;
        instance.saveDefaultConfig();
        loadConfig();
    }

    private void loadConfig() {
        final org.bukkit.configuration.file.FileConfiguration config = this.plugin.getConfig();
        this.pvpstats = config.getBoolean("enable-pvpstats");
        this.title = translateAlternateColorCodes('&', config.getString("Scoreboard.Title"));
        items = config.getConfigurationSection("Scoreboard.Items").getValues(false);
        this.disabledworlds = config.getStringList("disabled-worlds");
        this.intervall = config.getInt("Scoreboard.Update-delay");
        this.tempscoreboard = config.getBoolean("Temp-Scoreboard-enabled");
        this.temptitle = translateAlternateColorCodes('&', config.getString("Temp-Scoreboard.Title"));
        this.topitems = config.getInt("Temp-Scoreboard.Items");
        this.tempshow = config.getInt("Temp-Scoreboard.Intervall-show");
        this.tempdisapper = config.getInt("Temp-Scoreboard.Intervall-disappear");
    }

    public String getTitle() {
        return title;
    }

    public boolean isPvpstats() {
        return pvpstats;
    }

    public boolean checkWorld(final String world) {
        return disabledworlds.contains(world);
    }

    public boolean isTempscoreboard() {
        return tempscoreboard;
    }

    public String getTemptitle() {
        return temptitle;
    }

    public int getIntervall() {
        return intervall; //Change to short
    }

    public int getTopitems() {
        return topitems; //Change to byte
    }

    public int getTempshow() {
        return tempshow; //Change to short
    }

    public int getTempdisapper() {
        return tempdisapper; //Change to short
    }

    public void sendUpdate(final org.bukkit.entity.Player player) {
        for (String localtitle : items.keySet()) {
            me.games647.scoreboardstats.api.Score.sendScore((
                    (org.bukkit.craftbukkit.v1_5_R2.entity.CraftPlayer) player).getHandle().playerConnection
                    , localtitle
                    , me.games647.scoreboardstats.api.VariableReplacer.getValue((String) items.get(localtitle), player), true);
        }
    }
}
