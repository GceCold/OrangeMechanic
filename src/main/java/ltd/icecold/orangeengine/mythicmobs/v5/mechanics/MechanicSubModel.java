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

import java.util.Iterator;

import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class MechanicSubModel implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString partId;
    private final PlaceholderString sModelId;
    private final PlaceholderString sPartId;
    private final boolean remove;

    public MechanicSubModel(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.partId = mlc.getPlaceholderString(new String[]{"p", "pid", "part", "partid"}, null);
        this.sModelId = mlc.getPlaceholderString(new String[]{"sm", "smid", "submodel", "submodelid"}, null);
        this.sPartId = mlc.getPlaceholderString(new String[]{"sp", "spid", "subpart", "subpartid"}, null);
        this.remove = mlc.getBoolean(new String[]{"r", "remove"}, false);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        String modelId = this.modelId.get(data, target);
        String partId = this.partId.get(data, target);
        String subModelId = this.sModelId.get(data, target);
        String subPartId = this.sPartId.get(data, target);
        if (modelId != null && partId != null && subPartId != null && subModelId != null) {
            Entity entity = BukkitAdapter.adapt(target);
            ModelManager modelManager = OrangeEngineAPI.getModelManager();
            if (modelManager != null) {
                ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
                if (modelEntity != null) {
                    if (!remove)
                        modelEntity.addSubModel(subModelId, subPartId);
                    else
                        modelEntity.removeSubModel(subModelId, subPartId);
                    return SkillResult.SUCCESS;
                }
            }
        }
        return SkillResult.CONDITION_FAILED;
    }
}