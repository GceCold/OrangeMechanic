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
import org.bukkit.entity.Entity;

import java.awt.*;

public class MechanicTint implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString partId;
    private final PlaceholderString color;

    public MechanicTint(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.partId = mlc.getPlaceholderString(new String[]{"p", "pid", "part", "partid"}, null);
        this.color = mlc.getPlaceholderString(new String[]{"c", "color"}, "#FFFFFF");
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        if (modelId.isPresent() && partId.isPresent() && color.isPresent()) {
            Entity entity = BukkitAdapter.adapt(target);
            ModelManager modelManager = OrangeEngineAPI.getModelManager();
            if (modelManager != null) {
                ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
                if (modelEntity != null) {
                    if (!modelEntity.getModelData().modelName.equals(modelId.get())) {
                        modelEntity.setModel(modelId.get());
                    }
                    Color color1 = new Color(Integer.decode("#" + color.get().replace("#", "")));
                    modelEntity.boneColor(partId.get(), color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha());
                    return SkillResult.SUCCESS;
                }
            }
        }
        return SkillResult.CONDITION_FAILED;
    }
}
