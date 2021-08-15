package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;
import java.util.logging.Logger;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.entity.EntityPlayerMP;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.packet.Packet3Chat;
import net.minecraft.src.server.ServerCommand;
import net.minecraft.src.server.ServerConfigurationManager;
import net.minecraft.src.world.WorldServer;

public class ConsoleCommandHandler {

    public ConsoleCommandHandler(MinecraftServer minecraftserver) {
        minecraftServer = minecraftserver;
    }

    public void handleCommand(ServerCommand servercommand) {
        String command = servercommand.command;
        ICommandListener icommandlistener = servercommand.commandListener;
        String s1 = icommandlistener.getUsername();
        WorldServer worldserver = minecraftServer.worldManager;
        ServerConfigurationManager serverConfigurationManager = minecraftServer.configManager;
        if(command.toLowerCase().startsWith("help") || command.toLowerCase().startsWith("?"))
        {
            printHelp(icommandlistener);
        } else
        if(command.toLowerCase().startsWith("list"))
        {
            icommandlistener.log("Connected players: " + serverConfigurationManager.getPlayerList());
        } else
        if(command.toLowerCase().startsWith("stop"))
        {
            broadcast(s1, "Stopping the server..");
            minecraftServer.initiateShutdown();
        } else
        if(command.toLowerCase().startsWith("save-all"))
        {
            broadcast(s1, "Forcing save..");
            worldserver.func_26660_a(true, null);
            broadcast(s1, "Save complete.");
        } else
        if(command.toLowerCase().startsWith("save-off"))
        {
            broadcast(s1, "Disabling level saving..");
            worldserver.levelSaving = true;
        } else
        if(command.toLowerCase().startsWith("save-on"))
        {
            broadcast(s1, "Enabling level saving..");
            worldserver.levelSaving = false;
        } else
        if(command.toLowerCase().startsWith("op "))
        {
            String s2 = command.substring(command.indexOf(" ")).trim();
            serverConfigurationManager.opPlayer(s2);
            broadcast(s1, "Opping " + s2);
            serverConfigurationManager.sendChatMessageToPlayer(s2, "\247eYou are now op!");
        } else
        if(command.toLowerCase().startsWith("deop "))
        {
            String s3 = command.substring(command.indexOf(" ")).trim();
            serverConfigurationManager.deopPlayer(s3);
            serverConfigurationManager.sendChatMessageToPlayer(s3, "\247eYou are no longer op!");
            broadcast(s1, (new StringBuilder()).append("De-opping ").append(s3).toString());
        } else
        if(command.toLowerCase().startsWith("ban-ip "))
        {
            String s4 = command.substring(command.indexOf(" ")).trim();
            serverConfigurationManager.banIP(s4);
            broadcast(s1, (new StringBuilder()).append("Banning ip ").append(s4).toString());
        } else
        if(command.toLowerCase().startsWith("pardon-ip "))
        {
            String s5 = command.substring(command.indexOf(" ")).trim();
            serverConfigurationManager.pardonIP(s5);
            broadcast(s1, (new StringBuilder()).append("Pardoning ip ").append(s5).toString());
        } else
        if(command.toLowerCase().startsWith("ban "))
        {
            String s6 = command.substring(command.indexOf(" ")).trim();
            serverConfigurationManager.banPlayer(s6);
            broadcast(s1, (new StringBuilder()).append("Banning ").append(s6).toString());
            EntityPlayerMP entityplayermp = serverConfigurationManager.getPlayerEntity(s6);
            if(entityplayermp != null)
            {
                entityplayermp.playerNetServerHandler.kickPlayer("Banned by admin");
            }
        } else
        if(command.toLowerCase().startsWith("pardon "))
        {
            String s7 = command.substring(command.indexOf(" ")).trim();
            serverConfigurationManager.pardonPlayer(s7);
            broadcast(s1, (new StringBuilder()).append("Pardoning ").append(s7).toString());
        } else
        if(command.toLowerCase().startsWith("kick "))
        {
            String s8 = command.substring(command.indexOf(" ")).trim();
            EntityPlayerMP entityplayermp1 = null;
            for(int i = 0; i < serverConfigurationManager.playerEntities.size(); i++)
            {
                EntityPlayerMP entityplayermp5 = serverConfigurationManager.playerEntities.get(i);
                if(entityplayermp5.username.equalsIgnoreCase(s8))
                {
                    entityplayermp1 = entityplayermp5;
                }
            }

            if(entityplayermp1 != null)
            {
                entityplayermp1.playerNetServerHandler.kickPlayer("Kicked by admin");
                broadcast(s1, (new StringBuilder()).append("Kicking ").append(entityplayermp1.username).toString());
            } else
            {
                icommandlistener.log((new StringBuilder()).append("Can't find user ").append(s8).append(". No kick.").toString());
            }
        } else
        if(command.toLowerCase().startsWith("tp "))
        {
            String as[] = command.split(" ");
            if(as.length == 3)
            {
                EntityPlayerMP entityplayermp2 = serverConfigurationManager.getPlayerEntity(as[1]);
                EntityPlayerMP entityplayermp3 = serverConfigurationManager.getPlayerEntity(as[2]);
                if(entityplayermp2 == null)
                {
                    icommandlistener.log((new StringBuilder()).append("Can't find user ").append(as[1]).append(". No tp.").toString());
                } else
                if(entityplayermp3 == null)
                {
                    icommandlistener.log((new StringBuilder()).append("Can't find user ").append(as[2]).append(". No tp.").toString());
                } else
                {
                    entityplayermp2.playerNetServerHandler.teleportTo(entityplayermp3.posX, entityplayermp3.posY, entityplayermp3.posZ, entityplayermp3.rotationYaw, entityplayermp3.rotationPitch);
                    broadcast(s1, (new StringBuilder()).append("Teleporting ").append(as[1]).append(" to ").append(as[2]).append(".").toString());
                }
            } else
            {
                icommandlistener.log("Syntax error, please provice a source and a target.");
            }
        } else
        if(command.toLowerCase().startsWith("give "))
        {
            String as1[] = command.split(" ");
            if(as1.length != 3 && as1.length != 4)
            {
                return;
            }
            String s9 = as1[1];
            EntityPlayerMP entityplayermp4 = serverConfigurationManager.getPlayerEntity(s9);
            if(entityplayermp4 != null)
            {
                try
                {
                    int k = Integer.parseInt(as1[2]);
                    if(Item.itemsList[k] != null)
                    {
                        broadcast(s1, (new StringBuilder()).append("Giving ").append(entityplayermp4.username).append(" some ").append(k).toString());
                        int l = 1;
                        if(as1.length > 3)
                        {
                            l = tryParse(as1[3], 1);
                        }
                        if(l < 1)
                        {
                            l = 1;
                        }
                        if(l > 64)
                        {
                            l = 64;
                        }
                        entityplayermp4.dropPlayerItem(new ItemStack(k, l, 0));
                    } else
                    {
                        icommandlistener.log((new StringBuilder()).append("There's no item with id ").append(k).toString());
                    }
                }
                catch(NumberFormatException numberformatexception1)
                {
                    icommandlistener.log((new StringBuilder()).append("There's no item with id ").append(as1[2]).toString());
                }
            } else
            {
                icommandlistener.log((new StringBuilder()).append("Can't find user ").append(s9).toString());
            }
        } else
        if(command.toLowerCase().startsWith("time "))
        {
            String as2[] = command.split(" ");
            if(as2.length != 3)
            {
                return;
            }
            String s10 = as2[1];
            try
            {
                int j = Integer.parseInt(as2[2]);
                if("add".equalsIgnoreCase(s10))
                {
                    worldserver.setWorldTime(worldserver.getWorldTime() + (long)j);
                    broadcast(s1, (new StringBuilder()).append("Added ").append(j).append(" to time").toString());
                } else
                if("set".equalsIgnoreCase(s10))
                {
                    worldserver.setWorldTime(j);
                    broadcast(s1, (new StringBuilder()).append("Set time to ").append(j).toString());
                } else
                {
                    icommandlistener.log("Unknown method, use either \"add\" or \"set\"");
                }
            }
            catch(NumberFormatException numberformatexception)
            {
                icommandlistener.log((new StringBuilder()).append("Unable to convert time value, ").append(as2[2]).toString());
            }
        } else
        if(command.toLowerCase().startsWith("say "))
        {
            command = command.substring(command.indexOf(" ")).trim();
            minecraftLogger.info((new StringBuilder()).append("[").append(s1).append("] ").append(command).toString());
            serverConfigurationManager.sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("\247d[Server] ").append(command).toString()));
        } else
        if(command.toLowerCase().startsWith("tell "))
        {
            String as3[] = command.split(" ");
            if(as3.length >= 3)
            {
                command = command.substring(command.indexOf(" ")).trim();
                command = command.substring(command.indexOf(" ")).trim();
                minecraftLogger.info((new StringBuilder()).append("[").append(s1).append("->").append(as3[1]).append("] ").append(command).toString());
                command = (new StringBuilder()).append("\2477").append(s1).append(" whispers ").append(command).toString();
                minecraftLogger.info(command);
                if(!serverConfigurationManager.sendPacketToPlayer(as3[1], new Packet3Chat(command)))
                {
                    icommandlistener.log("There's no player by that name online.");
                }
            }
        } else
        if(command.toLowerCase().startsWith("whitelist "))
        {
            func_22113_a(s1, command, icommandlistener);
        } else
        {
            minecraftLogger.info("Unknown console command. Type \"help\" for help.");
        }
    }

    private void func_22113_a(String s, String s1, ICommandListener icommandlistener)
    {
        String as[] = s1.split(" ");
        if(as.length < 2)
        {
            return;
        }
        String s2 = as[1].toLowerCase();
        if("on".equals(s2))
        {
            broadcast(s, "Turned on white-listing");
            minecraftServer.propertyManagerObj.func_22118_b("white-list", true);
        } else
        if("off".equals(s2))
        {
            broadcast(s, "Turned off white-listing");
            minecraftServer.propertyManagerObj.func_22118_b("white-list", false);
        } else
        if("list".equals(s2))
        {
            Set set = minecraftServer.configManager.getWhiteListedIPs();
            String s5 = "";
            for(Iterator iterator = set.iterator(); iterator.hasNext();)
            {
                String s6 = (String)iterator.next();
                s5 = (new StringBuilder()).append(s5).append(s6).append(" ").toString();
            }

            icommandlistener.log((new StringBuilder()).append("White-listed players: ").append(s5).toString());
        } else
        if("add".equals(s2) && as.length == 3)
        {
            String s3 = as[2].toLowerCase();
            minecraftServer.configManager.addToWhiteList(s3);
            broadcast(s, (new StringBuilder()).append("Added ").append(s3).append(" to white-list").toString());
        } else
        if("remove".equals(s2) && as.length == 3)
        {
            String s4 = as[2].toLowerCase();
            minecraftServer.configManager.removeFromWhiteList(s4);
            broadcast(s, (new StringBuilder()).append("Removed ").append(s4).append(" from white-list").toString());
        } else
        if("reload".equals(s2))
        {
            minecraftServer.configManager.reloadWhiteList();
            broadcast(s, "Reloaded white-list from file");
        }
    }

    private void printHelp(ICommandListener icommandlistener)
    {
        icommandlistener.log("To run the server without a gui, start it like this:");
        icommandlistener.log("   java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
        icommandlistener.log("Console commands:");
        icommandlistener.log("   help  or  ?               shows this message");
        icommandlistener.log("   kick <player>             removes a player from the server");
        icommandlistener.log("   ban <player>              bans a player from the server");
        icommandlistener.log("   pardon <player>           pardons a banned player so that they can connect again");
        icommandlistener.log("   ban-ip <ip>               bans an IP address from the server");
        icommandlistener.log("   pardon-ip <ip>            pardons a banned IP address so that they can connect again");
        icommandlistener.log("   op <player>               turns a player into an op");
        icommandlistener.log("   deop <player>             removes op status from a player");
        icommandlistener.log("   tp <player1> <player2>    moves one player to the same location as another player");
        icommandlistener.log("   give <player> <id> [num]  gives a player a resource");
        icommandlistener.log("   tell <player> <message>   sends a private message to a player");
        icommandlistener.log("   stop                      gracefully stops the server");
        icommandlistener.log("   save-all                  forces a server-wide level save");
        icommandlistener.log("   save-off                  disables terrain saving (useful for backup scripts)");
        icommandlistener.log("   save-on                   re-enables terrain saving");
        icommandlistener.log("   list                      lists all currently connected players");
        icommandlistener.log("   say <message>             broadcasts a message to all players");
        icommandlistener.log("   time <add|set> <amount>   adds to or sets the world time (0-24000)");
    }

    private void broadcast(String s, String s1)
    {
        String s2 = (new StringBuilder()).append(s).append(": ").append(s1).toString();
        minecraftServer.configManager.sendChatMessageToAllPlayers("\2477(" + s2 + ")");
        minecraftLogger.info(s2);
    }

    private int tryParse(String s, int i)
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch(NumberFormatException numberformatexception)
        {
            return i;
        }
    }

    private static final Logger minecraftLogger = Logger.getLogger("Minecraft");
    private final MinecraftServer minecraftServer;

}
