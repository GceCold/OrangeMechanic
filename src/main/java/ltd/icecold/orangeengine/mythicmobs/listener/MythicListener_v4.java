package ltd.icecold.orangeengine.mythicmobs.listener;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicTargeterLoadEvent;
import ltd.icecold.orangeengine.mythicmobs.v4.mechanics.*;
import ltd.icecold.orangeengine.mythicmobs.v4.targeters.TargeterModelPart_v4;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Locale;

public class MythicListener_v4 implements Listener {
//    @EventHandler
//    public void onMythicMobSpawnEvent(MythicMobSpawnEvent event){
//        Entity entity = event.getEntity();
//        for (String skills : event.getMobType().getConfig().getStringList("Skills")) {
//            if (skills.startsWith("model")){
//                MythicLineConfig mlc = MythicLineConfig.of(skills);
//                String modelId = mlc.getPlaceholderString(new String[] { "m", "mid", "model", "modelid" }, null).get();
//            }
//        }
//    }
//
//    @EventHandler
//    public void onMythicMobDespawnEvent(MythicMobDespawnEvent event){
//        Entity entity = event.getEntity();
//        for (String skills : event.getMobType().getConfig().getStringList("Skills")) {
//            if (skills.startsWith("model")){
//                MythicLineConfig mlc = MythicLineConfig.of(skills);
//                String modelId = mlc.getPlaceholderString(new String[] { "m", "mid", "model", "modelid" }, null).get();
//            }
//        }
//    }


    @EventHandler
    public void onMythicMechanicLoadEvent(MythicMechanicLoadEvent event) {
        String name = event.getMechanicName();
        switch (name.toUpperCase(Locale.ENGLISH)) {
            case "MODEL", "DISGUISE" ->                 event.register(new MechanicModel_v4(name, event.getConfig()));
            case "STATE" ->                             event.register(new MechanicModelState_v4(name, event.getConfig()));
            case "TINT" ->                              event.register(new MechanicTint_v4(name, event.getConfig()));
            case "SWAPMODEL" ->                         event.register(new MechanicSwapModel_v4(name, event.getConfig()));
            case "DEFAULTSTATE" ->                      event.register(new MechanicDefaultState_v4(name, event.getConfig()));
            case "CHANGEPART" ->                        event.register(new MechanicChangePart_v4(name, event.getConfig()));
            case "SUBMODEL" ->                          event.register(new MechanicSubModel_v4(name, event.getConfig()));
            case "PETRIFY" ->                           event.register(new MechanicPetrify_v4(name, event.getConfig()));
            case "SETMODELTAG", "SETMODELTAGVISIBLE" -> event.register(new MechanicSetTag_v4(name, event.getConfig()));
            case "UNDISGUISE" ->                        event.register(new MechanicUnDisguise_v4(name, event.getConfig()));
            case "LOCKHEAD","LOCKMODELHEAD" ->          event.register(new MechanicLockHead_v4(name, event.getConfig()));
            case "PARTVISIBILITY" ->                    event.register(new MechanicPartVisibility_v4(name, event.getConfig()));
        }
    }

    @EventHandler
    public void onMythicTargeterLoad(MythicTargeterLoadEvent event) {
        String name = event.getTargeterName();
        final String[] split = name.split(":", 2);
        final String upperCase = name.toUpperCase(Locale.ENGLISH);
        if ("MODELPART".equals(upperCase)) {
            event.register(new TargeterModelPart_v4(event.getConfig()));
        }
    }
}
