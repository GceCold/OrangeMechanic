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
import ltd.icecold.orangeengine.api.model.data.bone.ExtraBoneData;
import org.bukkit.entity.Entity;

import java.awt.*;

public class MechanicPartVisibility implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString partId;
    private final boolean flag;

    public MechanicPartVisibility(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.partId = mlc.getPlaceholderString(new String[]{"p", "pid", "part", "partid"}, null);
        this.flag = mlc.getBoolean(new String[]{"v", "visible", "visibility"}, false);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
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
                return SkillResult.SUCCESS;
            }
        }
        return SkillResult.CONDITION_FAILED;
    }
}
