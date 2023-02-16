package ltd.icecold.orangeengine;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import ltd.icecold.orangeengine.api.event.PluginVerifyPostEvent;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.citizen.OrangeModelTrait;
import ltd.icecold.orangeengine.mythicmobs.listener.MythicListener_v5;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.TraitInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;

public class OrangeMechanic extends JavaPlugin implements Listener {
    private ProtocolManager protocolManager;
    public static final Logger LOGGER = LogManager.getLogger("OrangeMechanic");

    @Override
    public void onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager();
        if (!Bukkit.getPluginManager().getPlugin("OrangeEngine").isEnabled()) {
            Bukkit.getConsoleSender().sendMessage("§6[§eOrangeMechanic§6] > §c未找到§eOrangeEngine");
            return;
        }
        Bukkit.getPluginManager().registerEvents(this, this);
        boolean isNewMM = false;
        try {
            Class.forName("io.lumine.mythic.api.skills.SkillResult");
            isNewMM = true;
        } catch (ClassNotFoundException var3) {
        }
        if (isNewMM) {
            Bukkit.getConsoleSender().sendMessage("§6[§eOrangeMechanic§6] > " + ChatColor.AQUA + "已检测到" + ChatColor.GREEN + "MythicMobs " + ChatColor.GOLD + "v5");
            Bukkit.getPluginManager().registerEvents(new MythicListener_v5(), this);
        } else {
            Bukkit.getConsoleSender().sendMessage("§6[§eOrangeMechanic§6] > " + ChatColor.RED + "插件已不再支持 "+ "MythicMobs "+ "v4");
        }
        if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
            Bukkit.getConsoleSender().sendMessage("§6[§eOrangeMechanic§6] > " + ChatColor.AQUA + "已检测到" + ChatColor.GREEN + "Citizens");
            CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(OrangeModelTrait.class).withName("model"));
        }

        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.HIGH, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.isCancelled())
                    return;

                Player player = event.getPlayer();
                Entity vehicle = player.getVehicle();
                if (vehicle == null || !vehicle.hasMetadata("orange_driver")) {
                    return;
                }
                PacketContainer packet = event.getPacket();
                Float swSpeed = packet.getFloat().read(0);
                Float adSpeed = packet.getFloat().read(1);
                Boolean jumping = packet.getBooleans().read(0);

                Location playerLocation = player.getLocation();
                vehicle.setRotation(playerLocation.getYaw(),playerLocation.getPitch());
                Vector direction = playerLocation.getDirection();
                Vector sideways = direction.clone().crossProduct(new Vector(0, -1, 0));
                Vector total = direction.multiply(adSpeed / 10).add(sideways.multiply(swSpeed / 5));

                total.setY(jumping && vehicle.isOnGround() ? 0.5 : 0.0);
                if (!vehicle.isOnGround()) total.multiply(0.4);
                vehicle.setVelocity(vehicle.getVelocity().add(total));
            }
        });

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }


    @EventHandler
    public void onOrangeInit(PluginVerifyPostEvent event) {
        if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
            for (NPCRegistry npcRegistry : CitizensAPI.getNPCRegistries()) {
                for (NPC npc : npcRegistry.sorted()) {
                    if (npc.isSpawned()) {
                        OrangeModelTrait traitNullable = npc.getTraitNullable(OrangeModelTrait.class);
                        if (traitNullable != null && !"".equals(traitNullable.getModelName()) && OrangeEngineAPI.getModelManager().getAllModelData().containsKey(traitNullable.getModelName())) {
                            OrangeEngineAPI.getModelManager().addNewModelEntity(npc.getEntity().getUniqueId(), traitNullable.getModelName());
                        }
                    }
                }
            }
        }
    }
}
