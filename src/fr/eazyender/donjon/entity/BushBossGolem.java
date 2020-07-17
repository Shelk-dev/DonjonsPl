package fr.eazyender.donjon.entity;

import net.minecraft.server.v1_14_R1.EntityGolem;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.GenericAttributes;
import net.minecraft.server.v1_14_R1.World;

public class BushBossGolem extends EntityGolem{

	protected BushBossGolem(EntityTypes<? extends EntityGolem> entity, World world) {
		super(entity, world);
	}
	
	protected void initAttributes() {
        super.initAttributes();

        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(4.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35D);
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(150.0D);
    }

	

}
