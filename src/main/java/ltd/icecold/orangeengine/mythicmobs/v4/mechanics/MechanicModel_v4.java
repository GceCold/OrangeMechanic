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

public class MechanicModel_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final boolean nametag;
    private final boolean hitbox;
    private final boolean lockPitch;
    private final boolean lockYaw;

    public MechanicModel_v4(String skill, MythicLineConfig mlc) {
        super(skill, mlc);
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.hitbox = mlc.getBoolean(new String[]{"h", "hitbox"}, true);
        this.nametag = mlc.getBoolean(new String[]{"n", "name", "nametag"}, true);
        this.lockPitch = mlc.getBoolean(new String[]{"lp", "lpitch", "lockpitch"}, false);
        this.lockYaw = mlc.getBoolean(new String[]{"ly", "lyaw", "lockyaw"}, false);
    }

    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (this.modelId != null && modelManager != null && this.modelId.isPresent() && this.modelId.get() != null) {
            Entity entity = BukkitAdapter.adapt(abstractEntity);
            modelManager.addNewModelEntity(entity.getUniqueId(), this.modelId.get())
                    .overwriteBoundingBox(hitbox)
                    .unlockPitch(!lockPitch)
                    .unlockYaw(!lockYaw);
            entity.setCustomNameVisible(nametag);
            return true;
        }
        return false;
    }
}
