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
    private final String state;
    private final String modelId;
    private final Integer transition;
    private final boolean immediately;
    private final Integer identifier;

    public MechanicModelState(CustomMechanic holder, MythicLineConfig mlc) {
        this.state = mlc.getString(new String[]{"s", "state"});
        this.modelId = mlc.getString(new String[]{"m", "mid", "model", "modelid"});
        this.immediately = mlc.getBoolean(new String[]{"n", "now"}, false);
        this.transition = mlc.getInteger(new String[]{"t", "transition"}, 10);
        this.identifier = mlc.getInteger(new String[]{"i", "identifier"}, 0);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            if (!modelManager.getModelEntityMap().containsKey(target.getUniqueId()) && this.modelId != null && !"".equals(this.modelId)) {
                Entity entity = BukkitAdapter.adapt(target);
                modelManager.addNewModelEntity(entity.getUniqueId(), this.modelId);
            }
            if (modelManager.getModelEntityMap().containsKey(target.getUniqueId()) && this.state != null && !"".equals(state)) {
                ModelEntity modelEntity = modelManager.getModelEntityMap().get(target.getUniqueId());
                if (!immediately) {
                    modelEntity.playAnimation(this.state, transition, identifier);
                } else {
                    modelEntity.setAnimation(this.state, transition, identifier);
                }
                return SkillResult.SUCCESS;
            }
        }
        return SkillResult.INVALID_TARGET;
    }
}