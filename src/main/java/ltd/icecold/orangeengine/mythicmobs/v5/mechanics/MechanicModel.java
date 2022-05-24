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

public class MechanicModel implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final boolean nametag;
    private final boolean hitbox;
    private final boolean lockPitch;
    private final boolean lockYaw;


    public MechanicModel(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.hitbox = mlc.getBoolean(new String[]{"h", "hitbox"}, true);
        this.nametag = mlc.getBoolean(new String[]{"n", "name", "nametag"}, true);
        this.lockPitch = mlc.getBoolean(new String[]{"lp", "lpitch", "lockpitch"}, false);
        this.lockYaw = mlc.getBoolean(new String[]{"ly", "lyaw", "lockyaw"}, false);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        if (this.modelId == null || !this.modelId.isPresent() || this.modelId.get() == null) {
            return SkillResult.INVALID_CONFIG;
        }
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            Entity entity = BukkitAdapter.adapt(target);
            modelManager.addNewModelEntity(entity.getUniqueId(), this.modelId.get())
                    .overwriteBoundingBox(hitbox)
                    .unlockPitch(!lockPitch)
                    .unlockYaw(!lockYaw);
            entity.setCustomNameVisible(nametag);
            return SkillResult.SUCCESS;
        }
        return SkillResult.INVALID_TARGET;
    }
}
