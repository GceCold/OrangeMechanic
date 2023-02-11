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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class MechanicPetrify implements ITargetedEntitySkill {
    private final String modelId;

    public MechanicPetrify(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getString(new String[]{"m", "mid", "model", "modelid"});
    }

    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        if (modelId != null && !"".equals(modelId)) {
            Entity entity = BukkitAdapter.adapt(target);
            ModelManager modelManager = OrangeEngineAPI.getModelManager();
            if (modelManager != null && entity instanceof LivingEntity livingEntity && modelManager.getModelEntityMap().containsKey(entity.getUniqueId()) && modelManager.getAllModelData().containsKey(modelId)) {
                livingEntity.setAI(false);
                ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
                modelEntity.inactive(modelId);
                return SkillResult.SUCCESS;
            }
        }
        return SkillResult.CONDITION_FAILED;
    }
}