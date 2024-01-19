package ShoujoKageki.variables.patch;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.effects.PurgeCardInBattleEffect;
import ShoujoKageki.util.ActionUtils;
import ShoujoKageki.util.Utils2;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "<class>"
)
public class DisposableField2 {
    public static SpireField<CardGroup> disposedPile = new SpireField<>(() -> null);

}
