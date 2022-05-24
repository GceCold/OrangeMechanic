package ltd.icecold.orangeengine.mythicmobs.v4.mechanics;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderString;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import org.bukkit.entity.Entity;

public class MechanicLockHead_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final boolean lockPitch;
    private final boolean lockYaw;

    public MechanicLockHead_v4(String holder, MythicLineConfig mlc) {
        super(holder, mlc);
        this.lockPitch = mlc.getBoolean(new String[]{"lp", "lpitch", "lockpitch"}, false);
        this.lockYaw = mlc.getBoolean(new String[]{"ly", "lyaw", "lockyaw"}, false);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            Entity entity = BukkitAdapter.adapt(target);
            ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
            if (modelEntity != null) {
                if (lockPitch) modelEntity.setLockPitch(entity.getLocation().getPitch());
                if (lockYaw) modelEntity.setLockYaw(entity.getLocation().getPitch());
            }
        }
        return true;
    }
}