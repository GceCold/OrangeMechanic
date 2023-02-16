package ltd.icecold.orangeengine.mythicmobs.v5.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.ThreadSafetyLevel;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.SkillMechanic;
import io.lumine.mythic.core.skills.mechanics.CustomMechanic;
import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import ltd.icecold.orangeengine.api.model.ModelEntity;
import ltd.icecold.orangeengine.api.model.ModelManager;
import org.bukkit.entity.Entity;

public class MechanicMountModel extends SkillMechanic implements ITargetedEntitySkill {
    private String pbone;
    public MechanicMountModel(CustomMechanic holder, MythicLineConfig mlc) {
        super(holder.getManager(),holder.getConfigLine(),mlc);
        this.pbone = mlc.getString(new String[]{"p", "pbone"});
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        Entity bukkitTarget = BukkitAdapter.adapt(target);
        bukkitTarget.sendMessage("Mount Test");
        System.out.println("mount");
        AbstractEntity model = data.getCaster().getEntity();
        ModelManager modelManager = OrangeEngineAPI.getModelManager();
        System.out.println(model.getBukkitEntity().getCustomName()+"   "+bukkitTarget.getCustomName());

        if (modelManager == null)
            return SkillResult.ERROR;

        ModelEntity modelEntity = modelManager.getModelEntity(model.getUniqueId());

        if (modelEntity == null)
            return SkillResult.INVALID_TARGET;

        if (this.pbone != null && !this.pbone.equals("")) {
            String[] seat = this.pbone.trim().split(",");
            modelEntity.setSeatBone(seat);
        }

        if (modelEntity.getSeatBone().size() > 0){
            for (String seat : modelEntity.getSeatBone()) {
                if (!modelEntity.getMountEntity().containsKey(seat)){
                    modelEntity.mountPlayer(target.getUniqueId(),seat);
                    break;
                }
            }
            return SkillResult.SUCCESS;
        }else {
            model.getBukkitEntity().addPassenger(target.getBukkitEntity());
        }

        return SkillResult.CONDITION_FAILED;
    }

    public ThreadSafetyLevel getThreadSafetyLevel() {
        return ThreadSafetyLevel.SYNC_ONLY;
    }
}
