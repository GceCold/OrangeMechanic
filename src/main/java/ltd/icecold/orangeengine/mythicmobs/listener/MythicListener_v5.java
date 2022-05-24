package ltd.icecold.orangeengine.mythicmobs.listener;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.mythic.bukkit.events.MythicTargeterLoadEvent;
import ltd.icecold.orangeengine.mythicmobs.v5.mechanics.*;
import ltd.icecold.orangeengine.mythicmobs.v5.targeters.TargeterModelPart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Locale;


public class MythicListener_v5 implements Listener {
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
            case "MODEL", "DISGUISE" ->                 event.register(new MechanicModel        (event.getContainer(), event.getConfig()));
            case "STATE" ->                             event.register(new MechanicModelState   (event.getContainer(), event.getConfig()));
            case "TINT" ->                              event.register(new MechanicTint         (event.getContainer(), event.getConfig()));
            case "SWAPMODEL" ->                         event.register(new MechanicSwapModel    (event.getContainer(), event.getConfig()));
            case "DEFAULTSTATE" ->                      event.register(new MechanicDefaultState (event.getContainer(), event.getConfig()));
            case "CHANGEPART" ->                        event.register(new MechanicChangePart   (event.getContainer(), event.getConfig()));
            case "SUBMODEL" ->                          event.register(new MechanicSubModel     (event.getContainer(), event.getConfig()));
            case "PETRIFY" ->                           event.register(new MechanicPetrify      (event.getContainer(), event.getConfig()));
            case "SETMODELTAG", "SETMODELTAGVISIBLE" -> event.register(new MechanicSetTag       (event.getContainer(), event.getConfig()));
            case "UNDISGUISE" ->                        event.register(new MechanicUnDisguise   (event.getContainer(), event.getConfig()));
            case "LOCKHEAD","LOCKMODELHEAD" ->          event.register(new MechanicLockHead     (event.getContainer(), event.getConfig()));
            case "PARTVISIBILITY" ->                    event.register(new MechanicPartVisibility(event.getContainer(), event.getConfig()));
        }
    }

    @EventHandler
    public void onMythicTargeterLoad(MythicTargeterLoadEvent event) {
        String name = event.getTargeterName();
        final String[] split = name.split(":", 2);
        final String upperCase = name.toUpperCase(Locale.ENGLISH);
        if ("MODELPART".equals(upperCase)) {
            event.register(new TargeterModelPart(event.getConfig()));
        }
    }
}
