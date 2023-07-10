package ltd.icecold.orangeengine.mythicmobs.v5.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.mechanics.CustomMechanic;
import ltd.icecold.orangeengine.OrangeMechanic;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import ltd.icecold.orangeengine.api.data.model.AnimationType;
import org.bukkit.entity.Entity;

import java.util.Locale;
import java.util.UUID;

public class   MechanicDefaultState implements ITargetedEntitySkill {
    private final String modelId;
    private final String type;
    private final String state;

    public MechanicDefaultState(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getString(new String[]{"m", "mid", "model", "modelid"});
        this.type = mlc.getString(new String[]{"t", "type"});
        this.state = mlc.getString(new String[]{"s", "state"});
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            if (type == null && state == null){
                OrangeMechanic.LOGGER.error("技能参数配置错误");
                return SkillResult.INVALID_CONFIG;
            }
            if (!modelManager.getModelEntityMap().containsKey(target.getUniqueId()) && modelId != null) {
                modelManager.addNewModelEntity(target.getUniqueId(), this.modelId);
            }
            ModelEntity modelEntity = modelManager.getModelEntityMap().get(target.getUniqueId());
//            if (modelId != null && !"".equals(modelId) && !modelEntity.getModelData().modelName.equals(modelId)) {
//                modelEntity.setModel(modelId);
//            }
            modelEntity.setDefaultState(AnimationType.valueOf(type.toUpperCase(Locale.ROOT)), state);
            return SkillResult.SUCCESS;
        }
        return SkillResult.CONDITION_FAILED;
    }
}