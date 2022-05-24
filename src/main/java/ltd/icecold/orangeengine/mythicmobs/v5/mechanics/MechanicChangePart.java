package ltd.icecold.orangeengine.mythicmobs.v5.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.core.skills.mechanics.CustomMechanic;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;

public class MechanicChangePart implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString partId;
    private final PlaceholderString newModelId;
    private final PlaceholderString newPartId;

    public MechanicChangePart(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.partId = mlc.getPlaceholderString(new String[]{"p", "pid", "part", "partid"}, null);
        this.newModelId = mlc.getPlaceholderString(new String[]{"nm", "nmid", "newmodel", "newmodelid"}, null);
        this.newPartId = mlc.getPlaceholderString(new String[]{"np", "npid", "newpart", "newpartid"}, null);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            if (target != null && target.getBukkitEntity() != null) {
                modelManager.changeModelPart(target.getBukkitEntity(), modelId.get(), partId.get(), newModelId.get(), newPartId.get());
                return SkillResult.SUCCESS;
            }
        }
        return SkillResult.INVALID_TARGET;
    }
}
