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
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class MechanicChangePart implements ITargetedEntitySkill {
    private final String modelId;
    private final String partId;
    private String newModelId;
    private final String newPartId;

    public MechanicChangePart(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getString(new String[]{"m", "mid", "model", "modelid"});
        this.partId = mlc.getString(new String[]{"p", "pid", "part", "partid"});
        this.newModelId = mlc.getString(new String[]{"nm", "nmid", "newmodel", "newmodelid"});
        this.newPartId = mlc.getString(new String[]{"np", "npid", "newpart", "newpartid"});
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null && target != null) {
            if (partId == null || newPartId == null){
                OrangeMechanic.LOGGER.error("技能参数配置错误");
                return SkillResult.INVALID_CONFIG;
            }
            if (!modelManager.getModelEntityMap().containsKey(target.getBukkitEntity().getUniqueId()) && modelId != null) {
                modelManager.addNewModelEntity(target.getBukkitEntity().getUniqueId(), this.modelId);
            }
            if (newModelId == null){
                newModelId = modelManager.getModelEntityMap().get(target.getUniqueId()).getModelData().modelName;
            }
            modelManager.getModelEntity(target.getBukkitEntity().getUniqueId()).changeModelPart(modelId, partId, newModelId, newPartId);
            return SkillResult.SUCCESS;

        }
        return SkillResult.INVALID_TARGET;
    }
}
