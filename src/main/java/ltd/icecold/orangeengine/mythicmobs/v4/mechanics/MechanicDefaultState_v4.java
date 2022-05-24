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
import ltd.icecold.orangeengine.api.model.data.model.AnimationType;
import org.bukkit.entity.Entity;

import java.util.Locale;
import java.util.UUID;

public class MechanicDefaultState_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString type;
    private final PlaceholderString state;

    public MechanicDefaultState_v4(String skill, MythicLineConfig mlc) {
        super(skill, mlc);
        this.setAsyncSafe(false);
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.type = mlc.getPlaceholderString(new String[]{"t", "type"}, null);
        this.state = mlc.getPlaceholderString(new String[]{"s", "state"}, null);
    }

    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null && modelId.isPresent() && type.isPresent() && state.isPresent()) {
            if (!modelManager.getModelEntityMap().containsKey(abstractEntity.getUniqueId())){
                Entity entity = BukkitAdapter.adapt(abstractEntity);
                modelManager.addNewModelEntity(entity.getUniqueId(),this.modelId.get());
            }
            if (modelManager.getModelEntityMap().containsKey(abstractEntity.getUniqueId())){
                ModelEntity modelEntity = modelManager.getModelEntityMap().get(abstractEntity.getUniqueId());
                if (!modelEntity.getModelData().modelName.equals(modelId.get())){
                    modelEntity.setModel(modelId.get());
                }
                modelEntity.setDefaultState(AnimationType.valueOf(type.get().toUpperCase(Locale.ROOT)),state.get());
                return true;
            }
        }
        return false;
    }
}