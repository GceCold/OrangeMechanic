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

import java.awt.*;

public class MechanicTint_v4 extends SkillMechanic implements ITargetedEntitySkill {
    private final PlaceholderString modelId;
    private final PlaceholderString partId;
    private final PlaceholderString color;

    public MechanicTint_v4(String skill, MythicLineConfig mlc) {
        super(skill, mlc);
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.partId = mlc.getPlaceholderString(new String[]{"p", "pid", "part", "partid"}, null);
        this.color = mlc.getPlaceholderString(new String[]{"c", "color"}, "#FFFFFF");
    }

    @Override
    public boolean castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {
        if (modelId.isPresent() && partId.isPresent() && color.isPresent()) {
            Entity entity = BukkitAdapter.adapt(abstractEntity);
            ModelManager modelManager = OrangeEngineAPI.getModelManager();
            if (modelManager != null) {
                ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
                if (!modelEntity.getModelData().modelName.equals(modelId.get())) {
                    modelEntity.setModel(modelId.get());
                }
                Color color1 = new Color(Integer.decode("#" + color.get().replace("#", "")));
                modelEntity.boneColor(partId.get(), color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha());
                return true;
            }
        }
        return false;
    }
}
