package se.mickelus.tetra.trades;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.ConfigHandler;
import se.mickelus.tetra.blocks.forged.chthonic.ChthonicExtractorBlock;
import se.mickelus.tetra.blocks.scroll.ScrollItem;
import se.mickelus.tetra.items.forged.*;

import java.util.List;

public class TradeHandler {
    @SubscribeEvent
    public void setupWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ITrade> generic = event.getGenericTrades();
        List<VillagerTrades.ITrade> rare = event.getRareTrades();

        generic.add(new ItemsForScrapTrade(InsulatedPlateItem.instance, 1, 24, 1));
        generic.add(new ItemsForEmeraldsAndScrapTrade(LubricantDispenser.instance, 1, 8, 16, 1));
        generic.add(new ItemsForEmeraldsAndScrapTrade(ItemQuickLatch.instance, 1, 5, 16, 1));
        generic.add(new ItemsForScrapTrade(ItemBolt.instance, 1, 32, 2));

        rare.add(new ItemsForEmeraldsAndScrapTrade(StonecutterItem.instance, 1, 32, 16, 1));
        rare.add(new ItemsForEmeraldsAndScrapTrade(EarthpiercerItem.instance, 1, 24, 16, 1));
        rare.add(new ItemsForEmeraldsAndScrapTrade(CombustionChamberItem.instance, 1, 25, 16, 1));
        rare.add(new ItemsForEmeraldsAndScrapTrade(ChthonicExtractorBlock.instance, 1, 8, 16, 5));
    }

    @SubscribeEvent
    public void setupVillagerTrades(VillagerTradesEvent event) {
        if(ConfigHandler.enableVillagerTradeTables.get()) {
            VillagerProfession profession = event.getType();

            PopulateToolSmithTrades(event, profession);
            PopulateWeaponSmithTrades(event, profession);
            PopulateArmorerTrades(event, profession);
            PopulateFletcherTrades(event, profession);
            PopulateLeatherWorkerTrades(event, profession);
            PopulateMasonTrades(event, profession);
            PopulateLibrarianTrades(event, profession);
            PopulateShepherdTrades(event, profession);
            PopulateButcherTrades(event, profession);
        }
    }

    private void PopulateToolSmithTrades(VillagerTradesEvent event, VillagerProfession profession) {

        if (VillagerProfession.TOOLSMITH.equals(profession)) {
            event.getTrades().get(3).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.hammerEfficiency, 4, 1, 1, 10)
            ));
            event.getTrades().get(4).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.metalExpertise, 8, 1, 1, 20),
                    new ItemsForEmeraldsTrade(ScrollItem.hammerEfficiency, 4, 1, 1, 15)
            ));
            event.getTrades().get(5).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.metalExpertise, 8, 1, 1, 20)
            ));
        }
    }

    private void PopulateWeaponSmithTrades(VillagerTradesEvent event, VillagerProfession profession) {

        if (VillagerProfession.WEAPONSMITH.equals(profession)) {
            event.getTrades().get(3).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.hammerEfficiency, 4, 1, 1, 10)
            ));
            event.getTrades().get(4).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.metalExpertise, 8, 1, 1, 20),
                    new ItemsForEmeraldsTrade(ScrollItem.hammerEfficiency, 4, 1, 1, 15)
            ));
            event.getTrades().get(5).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.metalExpertise, 8, 1, 1, 20)
            ));
        }
    }

    private void PopulateArmorerTrades(VillagerTradesEvent event, VillagerProfession profession) {

        if (VillagerProfession.ARMORER.equals(profession)) {
            event.getTrades().get(3).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.hammerEfficiency, 4, 1, 1, 10)
            ));
            event.getTrades().get(4).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.metalExpertise, 8, 1, 1, 20),
                    new ItemsForEmeraldsTrade(ScrollItem.hammerEfficiency, 4, 1, 1, 15)
            ));
            event.getTrades().get(5).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.metalExpertise, 8, 1, 1, 20)
            ));
        }
    }

    private void PopulateFletcherTrades(VillagerTradesEvent event, VillagerProfession profession) {

        if (VillagerProfession.FLETCHER.equals(profession)) {
            event.getTrades().get(2).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.axeEfficiency, 4, 1, 1, 5)
            ));
            event.getTrades().get(3).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.woodExpertise, 8, 1, 1, 15),
                    new ItemsForEmeraldsTrade(ScrollItem.axeEfficiency, 4, 1, 1, 10)
            ));
            event.getTrades().get(4).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.woodExpertise, 8, 1, 1, 20),
                    new ItemsForEmeraldsTrade(ScrollItem.fibreExpertise, 8, 1, 1, 20)
            ));
            event.getTrades().get(5).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.fibreExpertise, 8, 1, 1, 20)
            ));
        }
    }

    private void PopulateLeatherWorkerTrades(VillagerTradesEvent event, VillagerProfession profession) {

        if (VillagerProfession.LEATHERWORKER.equals(profession)) {
            event.getTrades().get(2).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.cutEfficiency, 4, 1, 1, 5)
            ));
            event.getTrades().get(3).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.scaleExpertise, 8, 1, 1, 15),
                    new ItemsForEmeraldsTrade(ScrollItem.cutEfficiency, 4, 1, 1, 10)
            ));
            event.getTrades().get(4).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.scaleExpertise, 8, 1, 1, 20),
                    new ItemsForEmeraldsTrade(ScrollItem.skinExpertise, 8, 1, 1, 20)
            ));
            event.getTrades().get(5).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.skinExpertise, 8, 1, 1, 20)
            ));
        }
    }

    private void PopulateMasonTrades(VillagerTradesEvent event, VillagerProfession profession) {

        if (VillagerProfession.MASON.equals(profession)) {
            event.getTrades().get(2).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.hammerEfficiency, 4, 1, 1, 5)
            ));
            event.getTrades().get(3).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.stoneExpertise, 8, 1, 1, 15),
                    new ItemsForEmeraldsTrade(ScrollItem.hammerEfficiency, 4, 1, 1, 10)
            ));
            event.getTrades().get(4).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.stoneExpertise, 8, 1, 1, 20),
                    new ItemsForEmeraldsTrade(ScrollItem.gemExpertise, 8, 1, 1, 20)
            ));
            event.getTrades().get(5).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.gemExpertise, 8, 1, 1, 20)
            ));
        }
    }

    private void PopulateLibrarianTrades(VillagerTradesEvent event, VillagerProfession profession) {

        if (VillagerProfession.LIBRARIAN.equals(profession)) {
            event.getTrades().get(3).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.hammerEfficiency, 8, 1, 1, 20),
                    new ItemsForEmeraldsTrade(ScrollItem.axeEfficiency, 8, 1, 1, 20),
                    new ItemsForEmeraldsTrade(ScrollItem.cutEfficiency, 8, 1, 1, 20)
            ));
            event.getTrades().get(4).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.sturdyGuard, 16, 1, 1, 20),
                    new ItemsForEmeraldsTrade(ScrollItem.throwingKnife, 16, 1, 1, 20),
                    new ItemsForEmeraldsTrade(ScrollItem.howlingBlade, 16, 1, 1, 20)
            ));
        }
    }

    private void PopulateShepherdTrades(VillagerTradesEvent event, VillagerProfession profession) {

        if (VillagerProfession.SHEPHERD.equals(profession)) {
            event.getTrades().get(3).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.fabricExpertise, 16, 1, 1, 15)
            ));
            event.getTrades().get(4).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.fabricExpertise, 16, 1, 1, 20)
            ));
        }
    }

    private void PopulateButcherTrades(VillagerTradesEvent event, VillagerProfession profession) {

        if (VillagerProfession.BUTCHER.equals(profession)) {
            event.getTrades().get(3).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.boneExpertise, 16, 1, 1, 15)
            ));
            event.getTrades().get(4).addAll(ImmutableList.of(
                    new ItemsForEmeraldsTrade(ScrollItem.boneExpertise, 16, 1, 1, 20)
            ));
        }
    }
}
