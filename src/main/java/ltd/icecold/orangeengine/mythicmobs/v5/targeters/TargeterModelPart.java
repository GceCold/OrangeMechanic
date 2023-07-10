package ltd.icecold.orangeengine.mythicmobs.v5.targeters;

import com.google.common.collect.Sets;
import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.api.adapters.AbstractVector;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.targeters.ILocationTargeter;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import ltd.icecold.orangeengine.api.model.ModelManager;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Set;

public class TargeterModelPart implements ILocationTargeter {
    private final String modelId;
    private final String partId;
    private final double x;
    private final double y;
    private final double z;


    public TargeterModelPart(MythicLineConfig mlc) {
        this.modelId = mlc.getString(new String[]{"m", "mid", "model", "modelid"});
        this.partId = mlc.getString(new String[]{"p", "pid", "part", "partid"});
        this.x = mlc.getDouble("x", 0.0);
        this.y = mlc.getDouble("y", 0.0);
        this.z = mlc.getDouble("z", 0.0);
    }

    @Override
    public Collection<AbstractLocation> getLocations(SkillMetadata skillMetadata) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        Set<AbstractLocation> result = Sets.newHashSet();
        if (modelManager == null) return result;
        if (!modelManager.isModelCacheExists(modelId)) return result;

        Collection<Vector> boneVector = modelManager.getBoneVector(modelId, partId);
        if (boneVector == null || boneVector.isEmpty()) return result;

        for (Vector vector : boneVector) {
            AbstractLocation clone = skillMetadata.getCaster().getLocation().clone();
            clone.add(new AbstractVector(vector.getX(), vector.getY(), vector.getZ()));
            clone.add(this.x, this.y, this.z);
            result.add(clone);
        }

        return result;
    }
}
