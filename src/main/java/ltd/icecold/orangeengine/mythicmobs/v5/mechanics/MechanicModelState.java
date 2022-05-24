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

import java.util.UUID;

public class MechanicModelState implements ITargetedEntitySkill {
    private final PlaceholderString state;
    private final PlaceholderString modelId;

    public MechanicModelState(CustomMechanic holder, MythicLineConfig mlc) {
        this.state = mlc.getPlaceholderString(new String[]{"s", "state"}, null, new String[0]);
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        if (this.state.get() == null && this.modelId.get() == null) {
            return SkillResult.INVALID_CONFIG;
        }
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            if (!modelManager.getModelEntityMap().containsKey(target.getUniqueId())) {
                Entity entity = BukkitAdapter.adapt(target);
                modelManager.addNewModelEntity(entity.getUniqueId(), this.modelId.get());
            }
            if (modelManager.getModelEntityMap().containsKey(target.getUniqueId())) {
                ModelEntity modelEntity = modelManager.getModelEntityMap().get(target.getUniqueId());
                modelEntity.playAnimation(this.state.get());
                return SkillResult.SUCCESS;
            }
        }
        return SkillResult.INVALID_TARGET;
    }
}