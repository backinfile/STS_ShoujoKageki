package ShoujoKageki.reskin.skin;

import ShoujoKageki.Log;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.lang.reflect.Method;

public class AbstractSkin {
    public AbstractPlayer player;
    public final String SkinId;
    public final String atlasUrl;
    public final String skeletonUrl;
    public String name;
    public String info;
    public String jumpUrl;

    public AbstractSkin(String skinId, String atlasUrl, String skeletonUrl, String name, String info, String jumpUrl) {
        SkinId = skinId;
        this.atlasUrl = atlasUrl;
        this.skeletonUrl = skeletonUrl;
        this.name = name;
        this.info = info;
        this.jumpUrl = jumpUrl;
    }

    public AbstractSkin(String skinId, String atlasUrl, String skeletonUrl, String name, String info) {
        this(skinId, atlasUrl, skeletonUrl, name, info, "");
    }

    public void onLoad() {
        loadAnimation(atlasUrl, skeletonUrl, 1f);
    }

    protected void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        Method method = ReflectionHacks.getCachedMethod(AbstractCreature.class, "loadAnimation", String.class, String.class, float.class);
        try {
            method.invoke(player, atlasUrl, skeletonUrl, scale);
        } catch (Exception e) {
            Log.logger.error("", e);
        }
    }
}
