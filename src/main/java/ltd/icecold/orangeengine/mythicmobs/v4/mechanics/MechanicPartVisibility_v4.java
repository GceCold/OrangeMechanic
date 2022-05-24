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
import ltd.icecold.orangeengine.api.model.data.bone.ExtraBoneData;
import org.bukkit.entity.Entity;

public class MechanicPartVisibility_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString partId;
    private final boolean flag;

    public MechanicPartVisibility_v4(String holder, MythicLineConfig mlc) {
        super(holder, mlc);
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.partId = mlc.getPlaceholderString(new String[]{"p", "pid", "part", "partid"}, null);
        this.flag = mlc.getBoolean(new String[]{"v", "visible", "visibility"}, false);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
        Entity entity = BukkitAdapter.adapt(target);
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
            if (modelEntity != null) {
                if (!modelEntity.getModelData().modelName.equals(modelId.get())) {
                    modelEntity.setModel(modelId.get());
                }
                ExtraBoneData extraBoneData = new ExtraBoneData();
                extraBoneData.visible = flag;
                modelEntity.addBoneExtraData(partId.get(), extraBoneData);
                return true;
            }
        }
        return false;
    }
}
