//package ShoujoKageki.reskin.patches;
//
//
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.evacipated.cardcrawl.modthespire.lib.ByRef;
//import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
//import com.evacipated.cardcrawl.modthespire.lib.Matcher;
//import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
//import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.core.Settings;
//import com.megacrit.cardcrawl.helpers.FontHelper;
//import com.megacrit.cardcrawl.helpers.Hitbox;
//import com.megacrit.cardcrawl.helpers.ImageMaster;
//import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
//import com.megacrit.cardcrawl.helpers.input.InputHelper;
//import com.megacrit.cardcrawl.localization.UIStrings;
//import com.megacrit.cardcrawl.screens.CharSelectInfo;
//import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
//import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
//import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
//import java.util.ArrayList;
//import java.util.Iterator;
//import javassist.CtBehavior;
//import reskinContent.reskinContent;
//import reskinContent.skinCharacter.AbstractSkinCharacter;
//import reskinContent.skinCharacter.AutomatonSkin;
//import reskinContent.skinCharacter.ChampSkin;
//import reskinContent.skinCharacter.GuardianSkin;
//import reskinContent.skinCharacter.HexaghostSkin;
//import reskinContent.skinCharacter.SlimeBoundSkin;
//import reskinContent.skinCharacter.SneckoSkin;
//
//public class CharacterSelectScreenPatches {
//    private static final UIStrings uiStrings;
//    public static final String[] TEXT;
//    public static Hitbox reskinRight;
//    public static Hitbox reskinLeft;
//    private static float reskin_Text_W;
//    private static float reskin_W;
//    private static float reskinX_center;
//    public static float allTextInfoX;
//    public static float allTextInfoY;
//    private static boolean bgIMGUpdate;
//    public static AbstractSkinCharacter[] characters;
//
//    public CharacterSelectScreenPatches() {
//    }
//
//    static {
//        uiStrings = CardCrawlGame.languagePack.getUIString(reskinContent.makeID("ReSkin"));
//        TEXT = uiStrings.TEXT;
//        reskin_Text_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[1], 9999.0F, 0.0F);
//        reskin_W = reskin_Text_W + 200.0F * Settings.scale;
//        reskinX_center = 600.0F * Settings.scale;
//        allTextInfoX = 0.0F;
//        allTextInfoY = 0.0F;
//        bgIMGUpdate = false;
//        characters = new AbstractSkinCharacter[]{new GuardianSkin(), new SlimeBoundSkin(), new HexaghostSkin(), new SneckoSkin(), new ChampSkin(), new AutomatonSkin()};
//    }
//
//    @SpirePatch(
//            clz = CharacterSelectScreen.class,
//            method = "update"
//    )
//    public static class CharacterSelectScreenPatch_Update {
//        public CharacterSelectScreenPatch_Update() {
//        }
//
//        @SpirePostfixPatch
//        public static void Postfix(CharacterSelectScreen __instance) {
//            for(CharacterOption o: __instance.options) {
//                AbstractSkinCharacter[] var3 = reskinContent.patches.CharacterSelectScreenPatches.characters;
//                int var4 = var3.length;
//
//                for(int var5 = 0; var5 < var4; ++var5) {
//                    AbstractSkinCharacter c = var3[var5];
//                    c.InitializeReskinCount();
//                    if (o.name.equals(c.id) && o.selected && c.reskinUnlock) {
//                        if (!reskinContent.patches.CharacterSelectScreenPatches.bgIMGUpdate) {
//                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
//                            reskinContent.patches.CharacterSelectScreenPatches.bgIMGUpdate = true;
//                        }
//
//                        if (InputHelper.justClickedLeft && reskinContent.patches.CharacterSelectScreenPatches.reskinLeft.hovered) {
//                            reskinContent.patches.CharacterSelectScreenPatches.reskinLeft.clickStarted = true;
//                            CardCrawlGame.sound.play("UI_CLICK_1");
//                        }
//
//                        if (InputHelper.justClickedLeft && reskinContent.patches.CharacterSelectScreenPatches.reskinRight.hovered) {
//                            reskinContent.patches.CharacterSelectScreenPatches.reskinRight.clickStarted = true;
//                            CardCrawlGame.sound.play("UI_CLICK_1");
//                        }
//
//                        if (InputHelper.justClickedLeft && reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft.hovered && c.reskinCount > 0) {
//                            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft.clickStarted = true;
//                            CardCrawlGame.sound.play("UI_CLICK_1");
//                        }
//
//                        if (InputHelper.justClickedLeft && reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight.hovered && c.reskinCount > 0) {
//                            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight.clickStarted = true;
//                            CardCrawlGame.sound.play("UI_CLICK_1");
//                        }
//
//                        if (reskinContent.patches.CharacterSelectScreenPatches.reskinLeft.justHovered || reskinContent.patches.CharacterSelectScreenPatches.reskinRight.justHovered) {
//                            CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
//                        }
//
//                        if ((reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft.justHovered || reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight.justHovered) && c.reskinCount > 0) {
//                            CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
//                        }
//
//                        reskinContent.patches.CharacterSelectScreenPatches.reskinRight.move((float)Settings.WIDTH / 2.0F + reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 0.0F * Settings.scale);
//                        reskinContent.patches.CharacterSelectScreenPatches.reskinLeft.move((float)Settings.WIDTH / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 0.0F * Settings.scale);
//                        reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft.move((float)Settings.WIDTH / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 120.0F * Settings.scale);
//                        reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight.move((float)Settings.WIDTH / 2.0F + reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 120.0F * Settings.scale);
//                        reskinContent.patches.CharacterSelectScreenPatches.reskinLeft.update();
//                        reskinContent.patches.CharacterSelectScreenPatches.reskinRight.update();
//                        if (c.reskinCount > 0) {
//                            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft.update();
//                            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight.update();
//                        }
//
//                        if (reskinContent.patches.CharacterSelectScreenPatches.reskinRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
//                            reskinContent.patches.CharacterSelectScreenPatches.reskinRight.clicked = false;
//                            c.skins[c.reskinCount].clearWhenClick();
//                            reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.clear();
//                            if (c.reskinCount < c.skins.length - 1) {
//                                ++c.reskinCount;
//                            } else {
//                                c.reskinCount = 0;
//                            }
//
//                            c.skins[c.reskinCount].loadPortraitAnimation();
//                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
//                        }
//
//                        if (reskinContent.patches.CharacterSelectScreenPatches.reskinLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
//                            reskinContent.patches.CharacterSelectScreenPatches.reskinLeft.clicked = false;
//                            c.skins[c.reskinCount].clearWhenClick();
//                            reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.clear();
//                            if (c.reskinCount > 0) {
//                                --c.reskinCount;
//                            } else {
//                                c.reskinCount = c.skins.length - 1;
//                            }
//
//                            c.skins[c.reskinCount].loadPortraitAnimation();
//                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
//                        }
//
//                        if (reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
//                            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft.clicked = false;
//                            c.skins[c.reskinCount].clearWhenClick();
//                            reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.clear();
//                            if (c.skins[c.reskinCount].portraitAnimationType <= 0) {
//                                c.skins[c.reskinCount].portraitAnimationType = 2;
//                            } else {
//                                --c.skins[c.reskinCount].portraitAnimationType;
//                            }
//
//                            c.skins[c.reskinCount].loadPortraitAnimation();
//                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
//                        }
//
//                        if (reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
//                            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight.clicked = false;
//                            c.skins[c.reskinCount].clearWhenClick();
//                            reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.clear();
//                            if (c.skins[c.reskinCount].portraitAnimationType >= 2) {
//                                c.skins[c.reskinCount].portraitAnimationType = 0;
//                            } else {
//                                ++c.skins[c.reskinCount].portraitAnimationType;
//                            }
//
//                            c.skins[c.reskinCount].loadPortraitAnimation();
//                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
//                        }
//
//                        c.skins[c.reskinCount].update();
//                        if (c.skins[c.reskinCount].extraHitboxClickCheck()) {
//                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
//                        }
//
//                        c.InitializeReskinCount();
//                    }
//                }
//            }
//
//        }
//    }
//
//    @SpirePatch(
//            clz = CharacterOption.class,
//            method = "updateHitbox"
//    )
//    public static class CharacterOptionPatch_reloadAnimation {
//        public CharacterOptionPatch_reloadAnimation() {
//        }
//
//        @SpireInsertPatch(
//                rloc = 56
//        )
//        public static void Insert(CharacterOption __instance) {
//            reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.clear();
//            reskinContent.patches.CharacterSelectScreenPatches.bgIMGUpdate = false;
//            AbstractSkinCharacter[] var1 = reskinContent.patches.CharacterSelectScreenPatches.characters;
//            int var2 = var1.length;
//
//            for(int var3 = 0; var3 < var2; ++var3) {
//                AbstractSkinCharacter c = var1[var3];
//                c.InitializeReskinCount();
//                if (__instance.name.equals(c.id) && c.skins[c.reskinCount].portraitAnimationType != 0) {
//                    c.skins[c.reskinCount].clearWhenClick();
//                    if (c.skins[c.reskinCount].hasAnimation()) {
//                        c.skins[c.reskinCount].loadPortraitAnimation();
//                    }
//
//                    System.out.println("立绘载入2");
//                }
//            }
//
//        }
//    }
//
//    @SpirePatch(
//            clz = CharacterSelectScreen.class,
//            method = "initialize"
//    )
//    public static class CharacterSelectScreenPatch_Initialize {
//        public CharacterSelectScreenPatch_Initialize() {
//        }
//
//        @SpirePostfixPatch
//        public static void Postfix(CharacterSelectScreen __instance) {
//            reskinContent.loadSettings();
//            reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.clear();
//            reskinContent.patches.CharacterSelectScreenPatches.reskinRight = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
//            reskinContent.patches.CharacterSelectScreenPatches.reskinLeft = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
//            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
//            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
//            reskinContent.patches.CharacterSelectScreenPatches.reskinRight.move((float)Settings.WIDTH / 2.0F + reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 0.0F * Settings.scale);
//            reskinContent.patches.CharacterSelectScreenPatches.reskinLeft.move((float)Settings.WIDTH / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 0.0F * Settings.scale);
//            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft.move((float)Settings.WIDTH / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 120.0F * Settings.scale);
//            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight.move((float)Settings.WIDTH / 2.0F + reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 120.0F * Settings.scale);
//        }
//
//        @SpirePatch(
//                clz = CharacterSelectScreen.class,
//                method = "render"
//        )
//        public static class CharacterSelectScreenPatch_portraitSkeleton {
//            public CharacterSelectScreenPatch_portraitSkeleton() {
//            }
//
//            @SpireInsertPatch(
//                    rloc = 62
//            )
//            public static void Insert(CharacterSelectScreen __instance, SpriteBatch sb) {
//                Iterator var2 = __instance.options.iterator();
//
//                while(var2.hasNext()) {
//                    CharacterOption o = (CharacterOption)var2.next();
//                    AbstractSkinCharacter[] var4 = reskinContent.patches.CharacterSelectScreenPatches.characters;
//                    int var5 = var4.length;
//
//                    for(int var6 = 0; var6 < var5; ++var6) {
//                        AbstractSkinCharacter c = var4[var6];
//                        c.InitializeReskinCount();
//                        if (o.name.equals(c.id) && o.selected && c.skins[c.reskinCount].portraitAnimationType != 0) {
//                            c.skins[c.reskinCount].render(sb);
//                            if (reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.size() > 0) {
//                                for(int k = 0; k < reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.size(); ++k) {
//                                    if (!((AbstractGameEffect) reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.get(k)).isDone) {
//                                        ((AbstractGameEffect) reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.get(k)).update();
//                                        ((AbstractGameEffect) reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.get(k)).render(sb);
//                                    } else {
//                                        if (reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue_toRemove == null) {
//                                            reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue_toRemove = new ArrayList();
//                                        }
//
//                                        if (!reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue_toRemove.contains(reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.get(k))) {
//                                            reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue_toRemove.add(reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.get(k));
//                                        }
//                                    }
//                                }
//
//                                if (reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue_toRemove != null) {
//                                    reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue.removeAll(reskinContent.patches.CharacterSelectScreenPatches.char_effectsQueue_toRemove);
//                                }
//                            }
//                        }
//                    }
//                }
//
//            }
//        }
//
//        @SpirePatch(
//                clz = CharacterSelectScreen.class,
//                method = "render"
//        )
//        public static class CharacterSelectScreenPatch_Render {
//            public CharacterSelectScreenPatch_Render() {
//            }
//
//            @SpirePostfixPatch
//            public static void Initialize(CharacterSelectScreen __instance, SpriteBatch sb) {
//                Iterator var2 = __instance.options.iterator();
//
//                while(var2.hasNext()) {
//                    CharacterOption o = (CharacterOption)var2.next();
//                    AbstractSkinCharacter[] var4 = reskinContent.patches.CharacterSelectScreenPatches.characters;
//                    int var5 = var4.length;
//
//                    for(int var6 = 0; var6 < var5; ++var6) {
//                        AbstractSkinCharacter c = var4[var6];
//                        c.InitializeReskinCount();
//                        if (o.name.equals(c.id) && o.selected) {
//                            reskinContent.patches.CharacterSelectScreenPatches.reskinRight.render(sb);
//                            reskinContent.patches.CharacterSelectScreenPatches.reskinLeft.render(sb);
//                            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft.render(sb);
//                            reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight.render(sb);
//                            c.skins[c.reskinCount].extraHitboxRender(sb);
//                            if (c.skins[c.reskinCount].hasAnimation() && c.reskinUnlock) {
//                                if (!reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationLeft.hovered && !Settings.isControllerMode) {
//                                    sb.setColor(Color.LIGHT_GRAY.cpy());
//                                } else {
//                                    sb.setColor(Color.WHITE.cpy());
//                                }
//
//                                sb.draw(ImageMaster.CF_LEFT_ARROW, (float)Settings.WIDTH / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center - 36.0F * Settings.scale + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 84.0F * Settings.scale, 0.0F, 0.0F, 48.0F, 48.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 48, 48, false, false);
//                                if (!reskinContent.patches.CharacterSelectScreenPatches.portraitAnimationRight.hovered && !Settings.isControllerMode) {
//                                    sb.setColor(Color.LIGHT_GRAY.cpy());
//                                } else {
//                                    sb.setColor(Color.WHITE.cpy());
//                                }
//
//                                sb.draw(ImageMaster.CF_RIGHT_ARROW, (float)Settings.WIDTH / 2.0F + reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center - 36.0F * Settings.scale + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 84.0F * Settings.scale, 0.0F, 0.0F, 48.0F, 48.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 48, 48, false, false);
//                            }
//
//                            if (c.reskinUnlock) {
//                                if (!reskinContent.patches.CharacterSelectScreenPatches.reskinRight.hovered && !Settings.isControllerMode) {
//                                    sb.setColor(Color.LIGHT_GRAY.cpy());
//                                } else {
//                                    sb.setColor(Color.WHITE.cpy());
//                                }
//
//                                sb.draw(ImageMaster.CF_RIGHT_ARROW, (float)Settings.WIDTH / 2.0F + reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center - 36.0F * Settings.scale + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY - 36.0F * Settings.scale, 0.0F, 0.0F, 48.0F, 48.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 48, 48, false, false);
//                                if (!reskinContent.patches.CharacterSelectScreenPatches.reskinLeft.hovered && !Settings.isControllerMode) {
//                                    sb.setColor(Color.LIGHT_GRAY.cpy());
//                                } else {
//                                    sb.setColor(Color.WHITE.cpy());
//                                }
//
//                                sb.draw(ImageMaster.CF_LEFT_ARROW, (float)Settings.WIDTH / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskin_W / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center - 36.0F * Settings.scale + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY - 36.0F * Settings.scale, 0.0F, 0.0F, 48.0F, 48.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 48, 48, false, false);
//                            }
//
//                            FontHelper.cardTitleFont.getData().setScale(1.0F);
//                            FontHelper.losePowerFont.getData().setScale(0.8F);
//                            if (c.skins[c.reskinCount].hasAnimation() && c.reskinUnlock) {
//                                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, CardCrawlGame.languagePack.getUIString(reskinContent.makeID("PortraitAnimationType")).TEXT[c.skins[c.reskinCount].portraitAnimationType], (float)Settings.WIDTH / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 120.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
//                                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CardCrawlGame.languagePack.getUIString(reskinContent.makeID("PortraitAnimation")).TEXT[0], (float)Settings.WIDTH / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 170.0F * Settings.scale, Settings.GOLD_COLOR);
//                            }
//
//                            if (c.reskinUnlock) {
//                                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, reskinContent.patches.CharacterSelectScreenPatches.TEXT[0], (float)Settings.WIDTH / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 50.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
//                                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, c.skins[c.reskinCount].NAME, (float)Settings.WIDTH / 2.0F - reskinContent.patches.CharacterSelectScreenPatches.reskinX_center + reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX, reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY + 0.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
//                            }
//                        }
//                    }
//                }
//
//            }
//        }
//
//        private static class renderRelicsLocator extends SpireInsertLocator {
//            private renderRelicsLocator() {
//            }
//
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(CharacterOption.class, "renderRelics");
//                int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, methodCallMatcher);
//                return lines;
//            }
//        }
//
//        @SpirePatch(
//                clz = CharacterOption.class,
//                method = "renderInfo"
//        )
//        public static class CharacterOptionRenderInfoPatch {
//            public CharacterOptionRenderInfoPatch() {
//            }
//
//            @SpireInsertPatch(
//                    locator = reskinContent.patches.CharacterSelectScreenPatches.CharacterSelectScreenPatch_Initialize.renderRelicsLocator.class,
//                    localvars = {"infoX", "infoY", "charInfo", "flavorText"}
//            )
//            public static SpireReturn<Void> Insert(CharacterOption _instance, SpriteBatch sb, float infoX, float infoY, CharSelectInfo charInfo, @ByRef String[] flavorText) {
//                reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX = infoX - 200.0F * Settings.scale;
//                reskinContent.patches.CharacterSelectScreenPatches.allTextInfoY = infoY + 250.0F * Settings.scale;
//                AbstractSkinCharacter[] var6 = reskinContent.patches.CharacterSelectScreenPatches.characters;
//                int var7 = var6.length;
//
//                for(int var8 = 0; var8 < var7; ++var8) {
//                    AbstractSkinCharacter character = var6[var8];
//                    if (charInfo.name.equals(character.id)) {
//                        flavorText[0] = character.skins[character.reskinCount].DESCRIPTION;
//                    }
//                }
//
//                return SpireReturn.Continue();
//            }
//        }
//    }
//}
