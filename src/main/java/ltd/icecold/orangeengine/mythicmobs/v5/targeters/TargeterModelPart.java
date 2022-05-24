package ltd.icecold.orangeengine.mythicmobs.v5.targeters;

import com.google.common.collect.Sets;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderDouble;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.api.skills.targeters.IEntityTargeter;
import io.lumine.mythic.bukkit.BukkitAdapter;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;

import java.util.Collection;
import java.util.stream.Collectors;

public class TargeterModelPart implements IEntityTargeter {
    private final PlaceholderString modelId;
    private final PlaceholderString partId;
    private final PlaceholderDouble x;
    private final PlaceholderDouble y;
    private final PlaceholderDouble z;


    public TargeterModelPart(MythicLineConfig mlc) {
        this.modelId = mlc.getPlaceholderString(new String[]{"m", "mid", "model", "modelid"}, null);
        this.partId = mlc.getPlaceholderString(new String[]{"p", "pid", "part", "partid"}, null);
        this.x = mlc.getPlaceholderDouble("x", 0.0);
        this.y = mlc.getPlaceholderDouble("y", 0.0);
        this.z = mlc.getPlaceholderDouble("z", 0.0);
    }



    @Override
    public Collection<AbstractEntity> getEntities(SkillMetadata skillMetadata) {
        AbstractEntity entity = skillMetadata.getCaster().getEntity();
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        if (modelManager != null) {
            ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
            if (modelEntity != null) {
                if (!modelEntity.getModelData().modelName.equals(modelId.get())) {
                    modelEntity.setModel(modelId.get());
                }
                return entity.getBukkitEntity().getNearbyEntities(
                                modelEntity.getModelData().boundingBox.width + this.x.get(),
                                modelEntity.getModelData().boundingBox.height + this.y.get(),
                                modelEntity.getModelData().boundingBox.width + this.z.get())
                        .stream().map(BukkitAdapter::adapt)
                        .collect(Collectors.toSet());

//                ExtraBoneData extraBoneData = new ExtraBoneData();
//                extraBoneData.translate = new Quaternion((float) this.x.get(), (float) this.y.get(), (float) this.z.get());
//                modelEntity.addBoneExtraData(partId.get(), extraBoneData);
            }
        }
        return Sets.newHashSet(entity);
    }
}
