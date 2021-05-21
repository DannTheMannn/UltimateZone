package ultimatezone.ultimatezone;




import com.google.common.collect.Lists;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;


import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.session.SessionOwner;


import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;



import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.List;
import java.util.Vector;


public class CommandCreate implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

            World world =  new BukkitWorld(player.getWorld());
            RegionManager regions = container.get(world);



            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);


            SessionManager manger = WorldEdit.getInstance().getSessionManager();





            try {
                com.sk89q.worldedit.entity.Player actor =  BukkitAdapter.adapt(player); // WorldEdit's native Player class extends Actor
                LocalSession localSession = manger.get(actor);
                World selectionWorld = localSession.getSelectionWorld();
                Region selection = localSession.getSelection(selectionWorld);


                ProtectedRegion region = new ProtectedCuboidRegion(args[0], selection.getMinimumPoint(), selection.getMaximumPoint());





                List<ProtectedRegion> allRegions = Lists.newArrayList();
                for (ProtectedRegion r : regions.getRegions().values()) {
                    allRegions.add(r);
                }


                List<ProtectedRegion> overlapping = region.getIntersectingRegions(allRegions);
                sender.sendMessage(String.valueOf(overlapping));
                if(selection.getArea() > 2500){

                    sender.sendMessage("zu groß");
                }
                else if (regions.getRegionCountOfPlayer(localPlayer) > 1){

                    sender.sendMessage("zu viele Zonen");
                }
                else if (!overlapping.isEmpty()){

                    sender.sendMessage("überlappen");
                }
                else{
                    DefaultDomain owner = region.getOwners();
                    owner.addPlayer(localPlayer);
                    region.setOwners(owner);

                    regions.addRegion(region);
                    region.setPriority(10);
                }


            } catch (IncompleteRegionException e) {
                e.printStackTrace();
                sender.sendMessage("markier mal Junge");
            }




        }



        return true;
    }



}
