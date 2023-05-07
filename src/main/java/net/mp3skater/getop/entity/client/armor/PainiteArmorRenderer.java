package net.mp3skater.getop.entity.client.armor;

import net.mp3skater.getop.item.custom.PainiteArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class PainiteArmorRenderer extends GeoArmorRenderer<PainiteArmorItem> {
    public PainiteArmorRenderer() {
        super(new PainiteArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
    }
}
