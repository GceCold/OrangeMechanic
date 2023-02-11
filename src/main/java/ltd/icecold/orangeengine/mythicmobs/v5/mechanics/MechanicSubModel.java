package ltd.icecold.orangeengine.mythicmobs.v5.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.ThreadSafetyLevel;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.mechanics.CustomMechanic;

import java.util.Iterator;

import ltd.icecold.orangeengine.OrangeMechanic;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class MechanicSubModel implements ITargetedEntitySkill {
    private String modelId;
    private final String partId;
    private String sModelId;
    private final String sPartId;
    private final boolean remove;

    public MechanicSubModel(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getString(new String[]{"m", "mid", "model", "modelid"});
        this.partId = mlc.getString(new String[]{"p", "pid", "part", "partid"});
        this.sModelId = mlc.getString(new String[]{"sm", "smid", "submodel", "submodelid"});
        this.sPartId = mlc.getString(new String[]{"sp", "spid", "subpart", "subpartid"});
        this.remove = mlc.getBoolean(new String[]{"r", "remove"}, false);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager == null) {
            return SkillResult.CONDITION_FAILED;
        }

        if (modelId == null || "".equals(modelId)) {
            if (modelManager.getModelEntity(target.getUniqueId()) == null){
                return SkillResult.CONDITION_FAILED;
            }
            modelId = modelManager.getModelEntity(target.getUniqueId()).getModelData().modelName;
        }

        if (sModelId == null || "".equals(sModelId)) {
            sModelId = modelId;
        }

        if ((sPartId == null || "".equals(sPartId)) && !remove) {
            OrangeMechanic.LOGGER.error("SubModel参数缺失subpart");
            return SkillResult.INVALID_CONFIG;
        }

        ModelEntity modelEntity = modelManager.getModelEntity(target.getUniqueId());

        if (modelEntity == null){
            OrangeMechanic.LOGGER.error("未找到模型实体");
            return SkillResult.INVALID_TARGET;
        }

        if (remove){
            modelEntity.removeSubModel(modelId, sPartId);
        }else {
            modelEntity.addSubModel(partId, sModelId, sPartId);
        }
        return SkillResult.SUCCESS;
    }
}