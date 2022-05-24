package ltd.icecold.orangeengine.mythicmobs.v4.mechanics;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderString;
import org.bukkit.entity.Entity;

public class MechanicSetTag_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final PlaceholderString name;
    private final boolean flag;

    public MechanicSetTag_v4(String holder, MythicLineConfig mlc) {
        super(holder, mlc);
        this.name = mlc.getPlaceholderString(new String[]{"t", "tag"}, null);
        this.flag = mlc.getBoolean(new String[]{"v", "visible"}, true);
    }

    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
        Entity entity = BukkitAdapter.adapt(target);
        entity.setCustomNameVisible(flag);
        entity.setCustomName(name.get());
        return true;
    }
}
