package ltd.icecold.orangeengine.mythicmobs.v4.targeters;

import com.google.common.collect.Sets;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderDouble;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderString;
import io.lumine.xikage.mythicmobs.skills.targeters.IEntitySelector;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import ltd.icecold.orangeengine.api.model.data.bone.ExtraBoneData;
import ltd.icecold.orangeengine.api.model.data.bone.Quaternion;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TargeterModelPart_v4 extends IEntitySelector {
    private final PlaceholderString modelId;
    private final PlaceholderString partId;
    private final PlaceholderDouble x;
    private final PlaceholderDouble y;
    private final PlaceholderDouble z;

    public TargeterModelPart_v4(MythicLineConfig mlc) {
        super(mlc);
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
