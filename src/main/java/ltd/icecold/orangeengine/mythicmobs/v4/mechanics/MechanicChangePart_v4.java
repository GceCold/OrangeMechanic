package ltd.icecold.orangeengine.mythicmobs.v4.mechanics;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderString;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;

public class MechanicChangePart_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString partId;
    private final PlaceholderString newModelId;
    private final PlaceholderString newPartId;

    public MechanicChangePart_v4(String skill, MythicLineConfig mlc) {
        super(skill, mlc);
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, "");
        this.partId = mlc.getPlaceholderString(new String[]{"p", "pid", "part", "partid"}, "");
        this.newModelId = mlc.getPlaceholderString(new String[]{"nm", "nmid", "newmodel", "newmodelid"}, "");
        this.newPartId = mlc.getPlaceholderString(new String[]{"np", "npid", "newpart", "newpartid"}, "");
    }

    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {
        if (modelId.isPresent() && partId.isPresent() && abstractEntity != null && abstractEntity.getBukkitEntity() != null) {
            ModelManager modelManager = OrangeEngineAPI.getModelManager();
            if (modelManager != null) {
                modelManager.changeModelPart(abstractEntity.getBukkitEntity(), modelId.get(), partId.get(), newModelId.get(), newPartId.get());
            }
            return true;
        }
        return false;
    }
}
