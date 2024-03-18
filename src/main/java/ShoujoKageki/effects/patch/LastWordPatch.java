package ShoujoKageki.effects.patch;

import ShoujoKageki.cards.bag.LastWord;
import ShoujoKageki.patches.AudioPatch;
import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class LastWordPatch {
    public static boolean isLastCard = false;


    @SpireInitializer
    public static class LastCardRegister {
        public static void initialize() {
            BaseMod.subscribe(new OnCardUseSubscriber() {
                @Override
                public void receiveCardUsed(AbstractCard abstractCard) {
                    isLastCard = abstractCard instanceof LastWord;
                }
            });
            BaseMod.subscribe(new OnStartBattleSubscriber() {
                @Override
                public void receiveOnBattleStart(AbstractRoom abstractRoom) {
                    CardCrawlGame.sound.stop(AudioPatch.Sound_Last_Word);
                }
            });
        }
    }

    @SpirePatch2(
            clz = AbstractMonster.class,
            method = "playBossStinger"
    )
    public static class SoundPatch {
        public static SpireReturn<Void> Prefix() {
            if (isLastCard) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
