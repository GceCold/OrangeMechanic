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

public class MechanicLockHead implements ITargetedEntitySkill {
    private final boolean lockPitch;
    private final boolean lockYaw;

    public MechanicLockHead(CustomMechanic holder, MythicLineConfig mlc) {
        this.lockPitch = mlc.getBoolean(new String[]{"lp", "lpitch", "lockpitch"}, false);
        this.lockYaw = mlc.getBoolean(new String[]{"ly", "lyaw", "lockyaw"}, false);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            Entity entity = BukkitAdapter.adapt(target);
            ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
            if (modelEntity != null) {
                if (lockPitch) modelEntity.setLockPitch(entity.getLocation().getPitch());
                if (lockYaw) modelEntity.setLockYaw(entity.getLocation().getPitch());
            }
        }
        return SkillResult.SUCCESS;
    }
}