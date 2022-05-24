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

import java.util.UUID;

public class MechanicModelState_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final PlaceholderString state;
    private final PlaceholderString modelId;

    public MechanicModelState_v4(String skill, MythicLineConfig mlc) {
        super(skill, mlc);
        this.state = mlc.getPlaceholderString(new String[]{"s", "state"}, null);
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
    }

    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            if (!modelManager.getModelEntityMap().containsKey(abstractEntity.getUniqueId())) {
                Entity entity = BukkitAdapter.adapt(abstractEntity);
                modelManager.addNewModelEntity(entity.getUniqueId(), this.modelId.get())
                        .playAnimation(this.state.get());
                return true;
            }
            ModelEntity modelEntity = modelManager.getModelEntityMap().get(abstractEntity.getUniqueId());
            modelEntity.playAnimation(this.state.get());
            return true;
        }
        return false;
    }
}