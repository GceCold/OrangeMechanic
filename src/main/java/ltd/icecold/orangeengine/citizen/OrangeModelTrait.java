package ltd.icecold.orangeengine.citizen;

import ltd.icecold.orangeengine.api.OrangeEngineAPI;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.Bukkit;

@TraitName("model")
public class OrangeModelTrait extends Trait {
    @Persist
    String modelName = "";
    public OrangeModelTrait() {
        super("model");
    }
    @Override
    public void onSpawn() {
        super.onSpawn();
        if (this.npc.isSpawned() && !"".equals(modelName)) {
            OrangeEngineAPI.npcBindModel(npc.getEntity().getUniqueId(), modelName);
        }
    }

    public void setModel(String modelName) {
        if (this.npc.isSpawned() && !"".equals(modelName) && OrangeEngineAPI.getModelManager() != null) {
            this.modelName = modelName;
            OrangeEngineAPI.getModelManager().addNewModelEntity(npc.getEntity().getUniqueId(), modelName);
        }
    }

    public String getModelName() {
        return modelName;
    }
}
