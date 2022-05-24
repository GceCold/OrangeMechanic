package ltd.icecold.orangeengine.mythicmobs.v5.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.mechanics.CustomMechanic;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import org.bukkit.entity.Entity;

public class MechanicSwapModel implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString newModelId;


    public MechanicSwapModel(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.newModelId = mlc.getPlaceholderString(new String[]{"n", "nid", "newmodel", "newmodelid"}, null);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        if (this.newModelId == null || !this.newModelId.isPresent() || this.newModelId.get() == null) {
            return SkillResult.INVALID_CONFIG;
        }
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            Entity entity = BukkitAdapter.adapt(target);
            ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
            if (modelEntity != null) {
                modelEntity.setModel(newModelId.get());
                return SkillResult.SUCCESS;
            }
        }
        return SkillResult.INVALID_TARGET;
    }
}
