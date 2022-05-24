package ltd.icecold.orangeengine;

import ltd.icecold.orangeengine.api.event.PluginVerifyPostEvent;
import ltd.icecold.orangeengine.mythicmobs.listener.MythicListener_v4;
import ltd.icecold.orangeengine.mythicmobs.listener.MythicListener_v5;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class OrangeMechanic extends JavaPlugin implements Listener {
    //用于判断OrangeEngine是否已经成功验证，或者通过OrangeEngineAPI.getModelManager() != null判断
    public static boolean hadInit = false;
    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("MythicMobs") == null || Bukkit.getPluginManager().getPlugin("OrangeEngine") == null || !Bukkit.getPluginManager().getPlugin("OrangeEngine").isEnabled()) {
            Bukkit.getConsoleSender().sendMessage("§6[§eOrangeMechanic§6] > §c未找到§eOrangeEngine");
            return;
        }
        Bukkit.getPluginManager().registerEvents(this,this);
        boolean isNewMM = false;
        try {
            Class.forName("io.lumine.mythic.api.skills.SkillResult");
            isNewMM = true;
        } catch (ClassNotFoundException var3) {
        }
        if (isNewMM) {
            Bukkit.getConsoleSender().sendMessage("§6[§eOrangeMechanic§6] > " + ChatColor.AQUA + "已检测到" + ChatColor.GREEN + "MythicMobs " + ChatColor.GOLD + "v5");
            Bukkit.getPluginManager().registerEvents(new MythicListener_v5(),this);
        } else {
            Bukkit.getConsoleSender().sendMessage("§6[§eOrangeMechanic§6] > " + ChatColor.AQUA + "已检测到" + ChatColor.GREEN + "MythicMobs " + ChatColor.GOLD + "v4");
            Bukkit.getPluginManager().registerEvents(new MythicListener_v4(),this);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onOrangeInit(PluginVerifyPostEvent event){
        hadInit = true;
    }
}
