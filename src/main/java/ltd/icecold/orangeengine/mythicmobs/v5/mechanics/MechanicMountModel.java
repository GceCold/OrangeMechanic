package ltd.icecold.orangeengine.mythicmobs.v5.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.core.skills.mechanics.CustomMechanic;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.core.OrangeEngine;
import org.bukkit.metadata.FixedMetadataValue;

public class MechanicMountModel implements ITargetedEntitySkill {

    private String pbone;
    private String mode;

    public MechanicMountModel(CustomMechanic holder, MythicLineConfig mlc) {
        this.pbone = mlc.getString(new String[]{"p", "pbone"});
        this.mode = mlc.getString(new String[]{"m", "mode"},"Walking");
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        AbstractEntity model = data.getCaster().getEntity();
        ModelManager modelManager = OrangeEngineAPI.getModelManager();

        if (modelManager == null)
            return SkillResult.ERROR;

        ModelEntity modelEntity = modelManager.getModelEntity(model.getUniqueId());
        if (modelEntity == null)
            return SkillResult.INVALID_TARGET;

        if (this.pbone != null && !this.pbone.equals("")) {
            String[] seat = this.pbone.trim().split(",");
            modelEntity.setSeatBone(seat);
        }

        model.setMetadata("orange_driver", new FixedMetadataValue(OrangeEngine.getInstance(), mode != null ? mode : "Walking"));
        if (modelEntity.getSeatBone().size() > 0){
            for (String seat : modelEntity.getSeatBone()) {
                if (!modelEntity.getMountEntity().containsKey(seat)){
                    modelEntity.mountPlayer(target.getUniqueId(),seat);
                    break;
                }
            }
            return SkillResult.SUCCESS;
        }else {
            model.getBukkitEntity().addPassenger(target.getBukkitEntity());
        }

        return SkillResult.CONDITION_FAILED;
    }
}
