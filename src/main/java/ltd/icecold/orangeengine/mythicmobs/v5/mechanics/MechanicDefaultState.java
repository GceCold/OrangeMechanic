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
import ltd.icecold.orangeengine.api.model.data.model.AnimationType;
import org.bukkit.entity.Entity;

import java.util.Locale;
import java.util.UUID;

public class MechanicDefaultState implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString type;
    private final PlaceholderString state;

    public MechanicDefaultState(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.type = mlc.getPlaceholderString(new String[]{"t", "type"}, null);
        this.state = mlc.getPlaceholderString(new String[]{"s", "state"}, null);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            if (!modelManager.getModelEntityMap().containsKey(target.getUniqueId())) {
                UUID uniqueId = target.getUniqueId();
                Entity entity = BukkitAdapter.adapt(target);
                modelManager.addNewModelEntity(entity.getUniqueId(), this.modelId.get());
            }
            if (modelManager.getModelEntityMap().containsKey(target.getUniqueId())) {
                ModelEntity modelEntity = modelManager.getModelEntityMap().get(target.getUniqueId());
                if (!modelEntity.getModelData().modelName.equals(modelId.get())) {
                    modelEntity.setModel(modelId.get());
                }
                modelEntity.setDefaultState(AnimationType.valueOf(type.get().toUpperCase(Locale.ROOT)), state.get());
                return SkillResult.SUCCESS;
            }
        }
        return SkillResult.CONDITION_FAILED;
    }
}