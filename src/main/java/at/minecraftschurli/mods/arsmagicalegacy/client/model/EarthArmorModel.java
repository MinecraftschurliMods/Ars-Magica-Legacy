package at.minecraftschurli.mods.arsmagicalegacy.client.model;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.neoforge.common.util.Lazy;

public class EarthArmorModel extends Model<Unit> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ArsMagicaApi.id("earth_armor"), "main");
    private static final Identifier TEXTURE_LOCATION = ArsMagicaApi.id("textures/armor/earth_armor.png");
    private static final Lazy<EarthArmorModel> INSTANCE = Lazy.of(EarthArmorModel::new);
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public EarthArmorModel() {
        ModelPart root = AMClientUtil.mc().getEntityModels().bakeLayer(LAYER_LOCATION);
        super(root, RenderTypes::entityCutout);
        leftArm = root.getChild("left_arm");
        rightArm = root.getChild("right_arm");
    }

    public void renderArm(SubmitNodeCollector submitNodeCollector, PoseStack stack, HumanoidArm humanoidArm, int light) {
        ModelPart arm = humanoidArm == HumanoidArm.RIGHT ? rightArm : leftArm;
        arm.resetPose();
        arm.visible = true;
        leftArm.zRot = -0.1f;
        rightArm.zRot = 0.1f;
        submitNodeCollector.submitModelPart(arm, stack, RenderTypes.entityTranslucent(TEXTURE_LOCATION), light, OverlayTexture.NO_OVERLAY, null);
    }

    public static EarthArmorModel get() {
        return INSTANCE.get();
    }

    public static LayerDefinition createLayer() {
        MeshDefinition md = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
        PartDefinition pd = md.getRoot();
        CubeDeformation scale = CubeDeformation.NONE.extend(0.26f);
        pd.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 18).addBox(-3, 2, -2, 4, 8, 4, scale), PartPose.offset(-5, 2, 0));
        pd.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1, 2, -2, 4, 8, 4, scale), PartPose.offset(5, 2, 0));
        return LayerDefinition.create(md, 64, 32);
    }
}
