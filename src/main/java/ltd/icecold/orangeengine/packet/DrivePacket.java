package ltd.icecold.orangeengine.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import ltd.icecold.orangeengine.OrangeMechanic;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Locale;

public class DrivePacket extends PacketAdapter {

    public DrivePacket() {
        super(OrangeMechanic.instance, ListenerPriority.HIGH, PacketType.Play.Client.STEER_VEHICLE);
    }

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

        boolean fly = false;
        List<MetadataValue> orangeDriver = vehicle.getMetadata("orange_driver");
        for (MetadataValue metadataValue : orangeDriver) {
            if (metadataValue != null) {
                Plugin owningPlugin = metadataValue.getOwningPlugin();
                if (owningPlugin != null && owningPlugin.isEnabled()){
                    if (metadataValue.value() instanceof FixedMetadataValue value){
                        fly = ((String)value.value()).equalsIgnoreCase("flying");
                        break;
                    }
                }
            }
        }

        if (fly)
            total.setY((jumping && playerLocation.getY() < 256) ? 0.5 : 0.0);
        else
            total.setY(jumping && vehicle.isOnGround() ? 0.5 : 0.0);
        if (!vehicle.isOnGround()) total.multiply(0.3);
        vehicle.setVelocity(vehicle.getVelocity().add(total));
    }
}
