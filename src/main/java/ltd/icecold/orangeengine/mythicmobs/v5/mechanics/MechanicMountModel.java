package ltd.icecold.orangeengine.mythicmobs.v5.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.core.skills.mechanics.CustomMechanic;

public class MechanicMountModel implements ITargetedEntitySkill {

    private String pbone;

    public MechanicMountModel(CustomMechanic holder, MythicLineConfig mlc) {
        this.pbone = mlc.getString(new String[]{"p", "pbone"});
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        String[] seat = this.pbone.trim().split(",");

        return SkillResult.CONDITION_FAILED;
    }
}
