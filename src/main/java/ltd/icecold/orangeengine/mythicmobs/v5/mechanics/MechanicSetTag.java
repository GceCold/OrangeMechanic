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
import org.bukkit.entity.Entity;

public class MechanicSetTag implements ITargetedEntitySkill {
    private final PlaceholderString name;
    private final boolean flag;

    public MechanicSetTag(CustomMechanic holder, MythicLineConfig mlc) {
        this.name = mlc.getPlaceholderString(new String[]{"t", "tag"}, null);
        this.flag = mlc.getBoolean(new String[]{"v", "visible"}, true);
    }

    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        Entity entity = BukkitAdapter.adapt(target);
        entity.setCustomNameVisible(flag);
        entity.setCustomName(name.get());
        return SkillResult.SUCCESS;
    }
}
