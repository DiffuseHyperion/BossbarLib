package me.tigerhix.lib.bossbar;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.World;

public final class BossbarWither extends EntityMonster implements IRangedEntity {

    private final float[] a = new float[2];
    private final float[] b = new float[2];

    public BossbarWither(World world) {
        super(world);
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9F, 3.5F);
        this.fireProof = true;
        ((Navigation) this.getNavigation()).d(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 80.0F));
        this.b_ = 50;
    }

    @Override
	protected void h() {
        super.h();
        this.datawatcher.a(17, 0);
        this.datawatcher.a(18, 0);
        this.datawatcher.a(19, 0);
        this.datawatcher.a(20, 0);
    }

    @Override
	public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Invul", this.cl());
    }

    @Override
	public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.r(nbttagcompound.getInt("Invul"));
    }

    @Override
	protected String z() {
        return "mob.wither.idle";
    }

    @Override
	protected String bo() {
        return "mob.wither.hurt";
    }

    @Override
	protected String bp() {
        return "mob.wither.death";
    }

    @Override
	public void m() {
        this.motY *= 0.6000000238418579D;
        double d0;
        double d1;
        double d2;
        if (!this.world.isClientSide && this.s(0) > 0) {
            Entity i = this.world.a(this.s(0));
            if (i != null) {
                if (this.locY < i.locY || !this.cm() && this.locY < i.locY + 5.0D) {
                    if (this.motY < 0.0D) {
                        this.motY = 0.0D;
                    }
                    this.motY += (0.5D - this.motY) * 0.6000000238418579D;
                }
                double d3 = i.locX - this.locX;
                d0 = i.locZ - this.locZ;
                d1 = d3 * d3 + d0 * d0;
                if (d1 > 9.0D) {
                    d2 = MathHelper.sqrt(d1);
                    this.motX += (d3 / d2 * 0.5D - this.motX) * 0.6000000238418579D;
                    this.motZ += (d0 / d2 * 0.5D - this.motZ) * 0.6000000238418579D;
                }
            }
        }
        if (this.motX * this.motX + this.motZ * this.motZ > 0.05000000074505806D) {
            this.yaw = (float) MathHelper.b(this.motZ, this.motX) * 57.295776F - 90.0F;
        }
        super.m();
        int var22;
        int j;
        double d8;
        double d9;
        double d10;
        for (var22 = 0; var22 < 2; ++var22) {
            j = this.s(var22 + 1);
            Entity flag = null;
            if (j > 0) {
                flag = this.world.a(j);
            }

            if (flag != null) {
                d0 = this.t(var22 + 1);
                d1 = this.u(var22 + 1);
                d2 = this.v(var22 + 1);
                d8 = flag.locX - d0;
                d9 = flag.locY + flag.getHeadHeight() - d1;
                d10 = flag.locZ - d2;
                double d7 = MathHelper.sqrt(d8 * d8 + d10 * d10);
                float f = (float) (MathHelper.b(d10, d8) * 180.0D / 3.1415927410125732D) - 90.0F;
                float f1 = (float) (-(MathHelper.b(d9, d7) * 180.0D / 3.1415927410125732D));
                this.a[var22] = this.b(this.a[var22], f1, 40.0F);
                this.b[var22] = this.b(this.b[var22], f, 10.0F);
            } else {
                this.b[var22] = this.b(this.b[var22], this.aI, 10.0F);
            }
        }
    }

    @Override
	protected void E() {
        if (this.cl() > 0) {
            this.r(this.cl() - 1);
        } else {
            super.E();
            if (this.getGoalTarget() != null) {
                this.b(0, this.getGoalTarget().getId());
            } else {
                this.b(0, 0);
            }
        }
    }

    public void n() {
        this.r(220);
        this.setHealth(this.getMaxHealth() / 3.0F);
    }

    @Override
	public void aA() {
    }

    @Override
	public int br() {
        return 4;
    }

    private double t(int i) {
        if (i <= 0) {
            return this.locX;
        }
		float f = (this.aI + 180 * (i - 1)) / 180.0F * 3.1415927F;
		float f1 = MathHelper.cos(f);
		return this.locX + f1 * 1.3D;
    }

    private double u(int i) {
        return i <= 0 ? this.locY + 3.0D : this.locY + 2.2D;
    }

    private double v(int i) {
        if (i <= 0) {
            return this.locZ;
        }
		float f = (this.aI + 180 * (i - 1)) / 180.0F * 3.1415927F;
		float f1 = MathHelper.sin(f);
		return this.locZ + f1 * 1.3D;
    }

    private float b(float f, float f1, float f2) {
        float f3 = MathHelper.g(f1 - f);
        if (f3 > f2) {
            f3 = f2;
        }
        if (f3 < -f2) {
            f3 = -f2;
        }
        return f + f3;
    }

    @Override
	public void a(EntityLiving entityliving, float f) {
    }

    @Override
	public boolean damageEntity(DamageSource damagesource, float f) {
        return false;
    }

    @Override
	protected void dropDeathLoot(boolean flag, int i) {
    }

    @Override
	protected void D() {
        this.ticksFarFromPlayer = 0;
    }

    @Override
	public void e(float f, float f1) {
    }

    @Override
	public void addEffect(MobEffect mobeffect) {
    }

    @Override
	protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(300.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.6000000238418579D);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(40.0D);
    }

    public int cl() {
        return this.datawatcher.getInt(20);
    }

    public void r(int i) {
        this.datawatcher.watch(20, i);
    }

    public int s(int i) {
        return this.datawatcher.getInt(17 + i);
    }

    public void b(int i, int j) {
        this.datawatcher.watch(17 + i, j);
    }

    public boolean cm() {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    @Override
	public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEAD;
    }

    @Override
	public void mount(Entity entity) {
        this.vehicle = null;
    }
}
