package ltd.icecold.orangeengine.mythicmobs.v5.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.mechanics.CustomMechanic;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import org.bukkit.entity.Entity;

public class MechanicUnDisguise implements ITargetedEntitySkill {
    public MechanicUnDisguise(CustomMechanic holder, MythicLineConfig mlc) {
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            Entity entity = BukkitAdapter.adapt(target);
            modelManager.removeModelEntity(entity.getUniqueId(), true);
            return SkillResult.SUCCESS;
        }
        return SkillResult.INVALID_TARGET;
    }
}
