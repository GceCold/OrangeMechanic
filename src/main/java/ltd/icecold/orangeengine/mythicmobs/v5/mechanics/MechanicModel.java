package ltd.icecold.orangeengine.mythicmobs.v5.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.mechanics.CustomMechanic;
import ltd.icecold.orangeengine.OrangeMechanic;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import org.bukkit.entity.Entity;

public class MechanicModel implements ITargetedEntitySkill {
    private final String modelId;
    private final boolean nametag;
    private final boolean hitbox;
    private final boolean lockPitch;
    private final boolean lockYaw;
    private final boolean remove;
    private final boolean invisible;

    public MechanicModel(CustomMechanic holder, MythicLineConfig mlc) {
        this.modelId = mlc.getString(new String[]{"m", "mid", "model", "modelid"});
        this.hitbox = mlc.getBoolean(new String[]{"h", "hitbox"}, true);
        this.nametag = mlc.getBoolean(new String[]{"n", "name", "nametag"}, true);
        this.lockPitch = mlc.getBoolean(new String[]{"lp", "lpitch", "lockpitch"}, false);
        this.lockYaw = mlc.getBoolean(new String[]{"ly", "lyaw", "lockyaw"}, false);
        this.remove = mlc.getBoolean(new String[]{"r", "remove"}, false);
        this.invisible = mlc.getBoolean(new String[]{"i", "invis", "invisible"}, false);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        Entity entity = BukkitAdapter.adapt(target);
        if (modelManager != null) {
            if (modelId == null || modelId.equals("")){
                OrangeMechanic.LOGGER.error("技能参数配置错误");
                return SkillResult.INVALID_CONFIG;
            }
            if (this.remove && modelManager.getModelEntity(entity.getUniqueId()) != null){
                modelManager.removeModelEntity(entity.getUniqueId(),true);
                return SkillResult.SUCCESS;
            }
            if (!modelManager.getAllModelData().containsKey(modelId)){
                OrangeMechanic.LOGGER.error("未找到指定模型. "+ modelId);
                return SkillResult.INVALID_CONFIG;
            }
            if (modelManager.getModelEntity(entity.getUniqueId()) != null) {
                ModelEntity modelEntity = modelManager.getModelEntity(entity.getUniqueId());
                if (!modelEntity.getModelData().modelName.equals(modelId)) {
                    modelEntity.setModel(modelId);
                }
                modelEntity.overwriteBoundingBox(hitbox)
                        .unlockPitch(!lockPitch)
                        .unlockYaw(!lockYaw)
                        .setVisible(!invisible);
                return SkillResult.SUCCESS;
            }
            modelManager.addNewModelEntity(entity.getUniqueId(), this.modelId)
                    .overwriteBoundingBox(hitbox)
                    .unlockPitch(!lockPitch)
                    .unlockYaw(!lockYaw)
                    .setVisible(!invisible);
            entity.setCustomNameVisible(nametag);
            return SkillResult.SUCCESS;
        }
        return SkillResult.INVALID_TARGET;
    }
}
