package ShoujoKageki_Karen;

import ShoujoKageki.Log;
import ShoujoKageki.ShoujoKagekiManager;
import ShoujoKageki.ShoujokagekiPath;
import ShoujoKageki.base.SharedRelic;
import ShoujoKageki_Karen.character.KarenCharacter;
import ShoujoKageki_Karen.patches.OnRelicChangePatch;
import ShoujoKageki_Karen.potions.AwakePotion;
import ShoujoKageki_Karen.potions.BagPotion;
import ShoujoKageki_Karen.potions.ShinePotion;
import ShoujoKageki_Karen.reward.ShineCardReward;
import ShoujoKageki_Karen.screen.BagPileViewScreen;
import ShoujoKageki_Karen.screen.BlackMarketScreen;
import ShoujoKageki_Karen.variables.DefaultCustomVariable;
import ShoujoKageki_Karen.variables.DefaultSecondMagicNumber;
import ShoujoKageki_Karen.variables.DisposableVariable;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static ShoujoKageki_Karen.Res.*;
import static ShoujoKageki_Karen.character.KarenCharacter.Enums.CardColor_Karen;
import static ShoujoKageki_Karen.character.KarenCharacter.Enums.Karen;

@SpireInitializer
public class ShoujoKagekiKarenManager implements ISubscriber, EditCardsSubscriber,
        EditRelicsSubscriber, EditStringsSubscriber,
        EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber, RelicGetSubscriber {
    public static final Logger logger = LogManager.getLogger(KarenPath.ModName);

    public static final List<AbstractCard> allKarenCardList = new ArrayList<>();

    public ShoujoKagekiKarenManager() {
        BaseMod.subscribe(this);

        Log.logger.info("Creating the color " + CardColor_Karen.toString());
        BaseMod.addColor(KarenCharacter.Enums.CardColor_Karen,
                KarenRenderColor.cpy(),
                KarenRenderColor.cpy(),
                KarenRenderColor.cpy(),
                KarenRenderColor.cpy(),
                KarenRenderColor.cpy(),
                KarenRenderColor.cpy(),
                KarenRenderColor.cpy(),
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY,
                ENERGY_ORB_DEFAULT_GRAY, ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT,
                POWER_DEFAULT_GRAY_PORTRAIT, ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        Log.logger.info("Done creating the color");
    }

    public static void initialize() {
        Log.logger.info("========================= Initializing ShoujoKageki Karen Mod. . =========================");
        new ShoujoKagekiKarenManager();
        Log.logger.info("========================= /ShoujoKageki Karen Initialized. Hello World./ =========================");
    }


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        Log.logger.info("Beginning to edit strings for mod with ID: " + ShoujokagekiPath.getModId());
        String lang = ShoujoKagekiManager.getLang();
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                KarenPath.getResPath("/localization/" + lang + "/ShoujoKageki-Card-Strings.json"));

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                KarenPath.getResPath("/localization/" + lang + "/ShoujoKageki-Power-Strings.json"));

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                KarenPath.getResPath("/localization/" + lang + "/ShoujoKageki-Relic-Strings.json"));

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                KarenPath.getResPath("/localization/" + lang + "/ShoujoKageki-Event-Strings.json"));

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                KarenPath.getResPath("/localization/" + lang + "/ShoujoKageki-Potion-Strings.json"));

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                KarenPath.getResPath("/localization/" + lang + "/ShoujoKageki-Character-Strings.json"));

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                KarenPath.getResPath("/localization/" + lang + "/ShoujoKageki-Orb-Strings.json"));

        BaseMod.loadCustomStringsFile(UIStrings.class,
                KarenPath.getResPath("/localization/" + lang + "/ShoujoKageki-UI-Strings.json"));

        Log.logger.info("Done edittting strings");
    }

    @Override
    public void receiveEditCharacters() {

        Log.logger.info("Beginning to edit characters. " + "Add " + Karen.toString());
        BaseMod.addCharacter(new KarenCharacter("Karen", Karen), Karen_BUTTON,
                Karen_PORTRAIT, Karen);
        Log.logger.info("Added " + Karen.toString());

        Log.logger.info("Beginning to add potions.");
        BaseMod.addPotion(ShinePotion.class, Color.GOLD, CardHelper.getColor(255.0f, 230.0f, 230.0f), Color.GOLD, ShinePotion.POTION_ID, Karen);
        BaseMod.addPotion(BagPotion.class, KarenRenderColor.cpy(), Color.GOLD, KarenRenderColor.cpy(), BagPotion.POTION_ID, Karen);
        BaseMod.addPotion(AwakePotion.class, KarenRenderColor.cpy(), KarenRenderColor.cpy(), KarenRenderColor.cpy(), AwakePotion.POTION_ID, Karen);
        Log.logger.info("Added potions.");
    }

    @Override
    public void receiveEditCards() {
        Log.logger.info("Adding variables");
        // Add the Custom Dynamic Variables
        Log.logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        BaseMod.addDynamicVariable(new DisposableVariable());

        Log.logger.info("Adding cards");
        String cardsClassPath = this.getClass().getPackage().getName() + ".cards";
        new AutoAdd(ShoujokagekiPath.getModId()).packageFilter(cardsClassPath).setDefaultSeen(true).any(AbstractCard.class, (info, card) -> {
            BaseMod.addCard(card);
            if (info.seen) {
                UnlockTracker.unlockCard(card.cardID);
            }
            if (card.color == CardColor_Karen) {
                allKarenCardList.add(card);
            }
        });
        Log.logger.info("Done adding cards!");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files
                .internal(KarenPath.getResPath("/localization/" + ShoujoKagekiManager.getLang() + "/ShoujoKageki-Keyword-Strings.json"))
                .readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json,
                com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            if (ShoujoKagekiManager.getLang().equals("zhs")) {
                for (com.evacipated.cardcrawl.mod.stslib.Keyword keyword : keywords) {
                    BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                    Log.logger.info("-----------------add keyword: " + keyword.PROPER_NAME);
                }
            } else {
                for (com.evacipated.cardcrawl.mod.stslib.Keyword keyword : keywords) {
                    BaseMod.addKeyword(ShoujokagekiPath.getModId().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                    Log.logger.info("-----------------add keyword: " + keyword.PROPER_NAME);
                }
            }
        }
    }

    @Override
    public void receiveEditRelics() {
        String relicClassPath = this.getClass().getPackage().getName() + ".relics";
        Log.logger.info("===============Adding relics: search in " + relicClassPath);
        for (com.evacipated.cardcrawl.modthespire.ModInfo info : Loader.MODINFOS) {
            Log.logger.info(info.ID);
        }
        new AutoAdd(ShoujokagekiPath.getModId()).packageFilter(relicClassPath).any(CustomRelic.class, (info, relic) -> {
            if (relic.getClass().isAnnotationPresent(SharedRelic.class)) {
                BaseMod.addRelic(relic, RelicType.SHARED);
            } else {
                BaseMod.addRelicToCustomPool(relic, CardColor_Karen);
            }
//			if (info.seen || relic.tier == RelicTier.STARTER)
            UnlockTracker.markRelicAsSeen(relic.relicId);
            Log.logger.info("Adding relics: " + relic.relicId);
        });
        Log.logger.info("Done adding relics!");
    }

    @Override
    public void receivePostInitialize() {
//        BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        BaseMod.addCustomScreen(new BlackMarketScreen());
        BaseMod.addCustomScreen(new BagPileViewScreen());

        ShineCardReward.register();

        // =============== /EVENTS/ =================
//        BaseMod.addEvent(RealTimeEvent.ID, RealTimeEvent.class, TheCity.ID);

        logger.info("Done loading badge Image and mod options");
    }

    @Override
    public void receiveRelicGet(AbstractRelic abstractRelic) {
        OnRelicChangePatch.publishOnRelicChange();
    }
}
