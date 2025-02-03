package ShoujoKageki.patches;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.bag.LastWord;
import ShoujoKageki.character.KarenCharacter;
import ShoujoKageki.powers.BasePower;
import ShoujoKageki.powers.patch.StrengthPowerPatch;
import basemod.BaseMod;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ExtraAchievementUnlockPatch {
    @SpirePatch2(
            clz = AbstractRelic.class,
            method = "obtain"
    )
    public static class AchievementArrogant {
        public static void Postfix() {
            if (AbstractDungeon.player != null) {
                if (AbstractDungeon.player.relics.size() > 25) {
                    UnlockTracker.unlockAchievement("Karen:Arrogant");
                }
            }
        }
    }


    @SpireInitializer
    public static class Initializer {
        public static HashMap<AbstractCard, Integer> MoveToBagCntMap = new HashMap<>();
        private static int RarePowerCnt = 0;

        public static void initialize() {
            BaseMod.subscribe(new OnPlayerTurnStartSubscriber() {
                @Override
                public void receiveOnPlayerTurnStart() {
                    OnApplyPower.strengthAddedInOneTurn = 0;
                    MoveToBagCntMap.clear();
                }
            });

            BaseMod.subscribe(new OnStartBattleSubscriber() {
                @Override
                public void receiveOnBattleStart(AbstractRoom abstractRoom) {
                    RarePowerCnt = 0;
                }
            });
            BaseMod.subscribe(new OnCardUseSubscriber() {
                @Override
                public void receiveCardUsed(AbstractCard abstractCard) {
                    if (abstractCard.rarity == AbstractCard.CardRarity.RARE && abstractCard.type == AbstractCard.CardType.POWER) {
                        if (AbstractDungeon.player instanceof KarenCharacter) {
                            RarePowerCnt++;

                            if (RarePowerCnt >= 3) {
                                UnlockTracker.unlockAchievement("Karen:Reproduce");
                            }
                        }
                    }
                }
            });
        }
    }

    public static void OnMoveCardToBag(AbstractCard card) {
        Initializer.MoveToBagCntMap.putIfAbsent(card, 0);
        int oldCnt = Initializer.MoveToBagCntMap.get(card);
        int newCnt = oldCnt + 1;
        Initializer.MoveToBagCntMap.put(card, newCnt);
        if (newCnt >= 3) {
            UnlockTracker.unlockAchievement("Karen:Promise");
        }
    }

    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = "update"
    )
    public static class OnApplyPower {

        public static int strengthAddedInOneTurn = 0;

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(ApplyPowerAction __instance, AbstractPower ___powerToApply) {
            if (___powerToApply instanceof StrengthPower && ___powerToApply.amount > 0 && __instance.target.isPlayer) {
                strengthAddedInOneTurn += ___powerToApply.amount;

                if (strengthAddedInOneTurn >= 25) {
                    UnlockTracker.unlockAchievement("Karen:Eat");
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "powers");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = DamageAllEnemiesAction.class,
            method = "update"
    )
    public static class Update_DamageAllEnemiesAction {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(DamageAllEnemiesAction __instance) {
            Optional<AbstractMonster> any = AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(m -> m instanceof CorruptHeart).findAny();
            if (any.isPresent()) {
                AbstractMonster monster = any.get();
                if (monster.isDeadOrEscaped()) {
                    ArrayList<AbstractCard> cardsPlayedThisTurn = AbstractDungeon.actionManager.cardsPlayedThisTurn;
                    if (!cardsPlayedThisTurn.isEmpty()) {
                        AbstractCard lastPlayCard = cardsPlayedThisTurn.get(cardsPlayedThisTurn.size() - 1);
                        if (lastPlayCard instanceof LastWord) {
                            UnlockTracker.unlockAchievement("Karen:LastWord2");
                        }
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractRoom.class, "monsters");
                int[] allInOrder = LineFinder.findAllInOrder(ctBehavior, finalMatcher);
                return new int[]{allInOrder[allInOrder.length - 1]};
            }
        }
    }
}
