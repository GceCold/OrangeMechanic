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
import ltd.icecold.orangeengine.api.data.bone.ExtraBoneData;
import org.bukkit.entity.Entity;

public class MechanicPartVisibility implements ITargetedEntitySkill {
    private final String modelId;
    private final String partId;
    private final boolean flag;

    public MechanicPartVisibility(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getString(new String[]{"m", "mid", "model", "modelid"});
        this.partId = mlc.getString(new String[]{"p", "pid", "part", "partid"});
        this.flag = mlc.getBoolean(new String[]{"v", "visible", "visibility"}, false);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        Entity entity = BukkitAdapter.adapt(target);
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
            if (modelEntity != null) {
//                if (modelId != null && !"".equals(modelId) && modelManager.getAllModelData().containsKey(modelId) && !modelEntity.getModelData().modelName.equals(modelId)) {
//                    modelEntity.setModel(modelId);
//                }
                ExtraBoneData extraBoneData = new ExtraBoneData();
                extraBoneData.visible = flag;
                modelEntity.addBoneExtraData(partId, extraBoneData);
                return SkillResult.SUCCESS;
            }
        }
        return SkillResult.CONDITION_FAILED;
    }
}
