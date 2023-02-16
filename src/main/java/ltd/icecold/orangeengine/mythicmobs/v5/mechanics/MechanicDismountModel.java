package ltd.icecold.orangeengine.mythicmobs.v5.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.core.skills.mechanics.CustomMechanic;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;

public class MechanicDismountModel implements ITargetedEntitySkill {

    private String pbone;

    public MechanicDismountModel(CustomMechanic holder, MythicLineConfig mlc) {
        this.pbone = mlc.getString(new String[]{"p", "pbone"});
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
            for (String seat : this.pbone.trim().split(",")) {
                modelEntity.dismountSeat(seat);
            }
            return SkillResult.SUCCESS;
        }

        return SkillResult.CONDITION_FAILED;
    }
}