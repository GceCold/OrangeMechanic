package ltd.icecold.orangeengine.mythicmobs.v4.mechanics;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderDouble;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderString;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class MechanicPetrify_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final PlaceholderString modelId;

    public MechanicPetrify_v4(String holder, MythicLineConfig mlc) {
        super(holder, mlc);
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
    }

    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
        String model = this.modelId.get(data, target);
        if (model != null) {
            Entity entity = BukkitAdapter.adapt(target);
            ModelManager modelManager = OrangeEngineAPI.getModelManager();
            if (modelManager != null && entity instanceof LivingEntity livingEntity) {
                livingEntity.setAI(false);
                ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
                if (modelEntity != null) {
                    modelEntity.inactive(modelId.get());
                }
                return true;
            }
        }
        return false;
    }
}