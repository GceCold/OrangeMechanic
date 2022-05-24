package ltd.icecold.orangeengine.mythicmobs.v4.mechanics;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderString;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import org.bukkit.entity.Entity;

public class MechanicUnDisguise_v4 extends SkillMechanic implements ITargetedEntitySkill {
    public MechanicUnDisguise_v4(String holder, MythicLineConfig mlc) {
        super(holder, mlc);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            Entity entity = BukkitAdapter.adapt(target);
            modelManager.removeModelEntity(entity.getUniqueId(), true);
            return true;
        }
        return false;
    }
}
