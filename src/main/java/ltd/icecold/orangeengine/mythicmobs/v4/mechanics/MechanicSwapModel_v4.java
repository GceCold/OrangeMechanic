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

public class MechanicSwapModel_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString newModelId;

    public MechanicSwapModel_v4(String holder, MythicLineConfig mlc) {
        super(holder, mlc);
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.newModelId = mlc.getPlaceholderString(new String[]{"n", "nid", "newmodel", "newmodelid"}, null);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
        if (this.newModelId == null || !this.newModelId.isPresent() || this.newModelId.get() == null) {
            return false;
        }
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            Entity entity = BukkitAdapter.adapt(target);
            ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
            if (modelEntity != null) {
                modelEntity.setModel(newModelId.get());
                return true;
            }
        }
        return false;
    }
}
