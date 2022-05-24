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

public class MechanicSubModel_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString partId;
    private final PlaceholderString sModelId;
    private final PlaceholderString sPartId;
    private final boolean remove;

    public MechanicSubModel_v4(String holder, MythicLineConfig mlc) {
        super(holder, mlc);
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.partId = mlc.getPlaceholderString(new String[]{"p", "pid", "part", "partid"}, null);
        this.sModelId = mlc.getPlaceholderString(new String[]{"sm", "smid", "submodel", "submodelid"}, null);
        this.sPartId = mlc.getPlaceholderString(new String[]{"sp", "spid", "subpart", "subpartid"}, null);
        this.remove = mlc.getBoolean(new String[]{"r", "remove"}, false);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
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
                    return true;
                }
            }
        }
        return false;
    }
}