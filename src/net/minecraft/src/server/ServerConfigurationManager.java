package net.minecraft.src.server;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.IPlayerFileData;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.TileEntity;
import net.minecraft.src.world.WorldServer;
import net.minecraft.src.block.BlockFire;
import net.minecraft.src.chunk.ChunkCoordinates;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.entity.EntityPlayerMP;
import net.minecraft.src.network.NetLoginHandler;
import net.minecraft.src.packet.Packet;
import net.minecraft.src.packet.Packet3Chat;
import net.minecraft.src.packet.Packet70;
import net.minecraft.src.packet.Packet9;

public class ServerConfigurationManager {

    public static Logger logger = Logger.getLogger("Minecraft");
    public List<EntityPlayerMP> playerEntities;
    private MinecraftServer mcServer;
    private ISaveHandler playerManagerObj;
    private int maxPlayers;
    private Set bannedPlayers;
    private Set bannedIPs;
    private Set ops;
    private Set whiteListedIPs;
    private File bannedPlayersFile;
    private File ipBanFile;
    private File opFile;
    private File whitelistPlayersFile;
    private IPlayerFileData playerNBTManagerObj;
    private boolean whiteListEnforced;

    public ServerConfigurationManager(MinecraftServer minecraftserver) {
        playerEntities = new ArrayList();
        bannedPlayers = new HashSet();
        bannedIPs = new HashSet();
        ops = new HashSet();
        whiteListedIPs = new HashSet();
        mcServer = minecraftserver;
        bannedPlayersFile = minecraftserver.getFile("banned-players.txt");
        ipBanFile = minecraftserver.getFile("banned-ips.txt");
        opFile = minecraftserver.getFile("ops.txt");
        whitelistPlayersFile = minecraftserver.getFile("white-list.txt");
        playerManagerObj = new ISaveHandler(minecraftserver);
        maxPlayers = minecraftserver.propertyManagerObj.getIntProperty("max-players", 20);
        whiteListEnforced = minecraftserver.propertyManagerObj.getBooleanProperty("white-list", false);
        readBannedPlayers();
        loadBannedList();
        loadOps();
        loadWhiteList();
        writeBannedPlayers();
        saveBannedList();
        saveOps();
        saveWhiteList();
    }

    public void setPlayerManager(WorldServer worldserver)
    {
        playerNBTManagerObj = worldserver.func_26661_o().func_22090_d();
    }

    public int getMaxTrackingDistance()
    {
        return playerManagerObj.func_26687_b();
    }

    public void playerLoggedIn(EntityPlayerMP entityPlayerMP)
    {
        playerEntities.add(entityPlayerMP);
        playerNBTManagerObj.readPlayerData(entityPlayerMP);
        mcServer.worldManager.chunkProvider.loadChunk((int)entityPlayerMP.posX >> 4, (int)entityPlayerMP.posZ >> 4);
        for(; mcServer.worldManager.getCollidingBoundingBoxes(entityPlayerMP, entityPlayerMP.boundingBox).size() != 0; entityPlayerMP.setPosition(entityPlayerMP.posX, entityPlayerMP.posY + 1.0D, entityPlayerMP.posZ)) { }
        mcServer.worldManager.entityJoinedWorld(entityPlayerMP);
        playerManagerObj.func_26682_a(entityPlayerMP);
    }

    public void func_613_b(EntityPlayerMP entityplayermp)
    {
        playerManagerObj.func_26688_c(entityplayermp);
    }

    public void playerLoggedOut(EntityPlayerMP entityplayermp)
    {
        playerNBTManagerObj.writePlayerData(entityplayermp);
        mcServer.worldManager.func_22085_d(entityplayermp);
        playerEntities.remove(entityplayermp);
        playerManagerObj.func_26681_b(entityplayermp);
    }

    public EntityPlayerMP login(NetLoginHandler netloginhandler, String s, String s1)
    {
        if(bannedPlayers.contains(s.trim().toLowerCase()))
        {
            netloginhandler.kickUser("You are banned from this server!");
            return null;
        }
        if(!isAllowedToLogin(s))
        {
            netloginhandler.kickUser("You are not white-listed on this server!");
            return null;
        }
        String s2 = netloginhandler.netManager.getRemoteAddress().toString();
        s2 = s2.substring(s2.indexOf("/") + 1);
        s2 = s2.substring(0, s2.indexOf(":"));
        if(bannedIPs.contains(s2))
        {
            netloginhandler.kickUser("Your IP address is banned from this server!");
            return null;
        }
        if(playerEntities.size() >= maxPlayers)
        {
            netloginhandler.kickUser("The server is full!");
            return null;
        }
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayerMP entityplayermp = playerEntities.get(i);
            if(entityplayermp.username.equalsIgnoreCase(s))
            {
                entityplayermp.playerNetServerHandler.kickPlayer("You logged in from another location");
            }
        }

        return new EntityPlayerMP(mcServer, mcServer.worldManager, s, new BlockFire(mcServer.worldManager));
    }

    public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP entityplayermp)
    {
        mcServer.entityTracker.removeTrackedPlayerSymmetric(entityplayermp);
        mcServer.entityTracker.untrackEntity(entityplayermp);
        playerManagerObj.func_26681_b(entityplayermp);
        playerEntities.remove(entityplayermp);
        mcServer.worldManager.removePlayer(entityplayermp);
        ChunkCoordinates chunkcoordinates = entityplayermp.func_25049_H();
        EntityPlayerMP entityplayermp1 = new EntityPlayerMP(mcServer, mcServer.worldManager, entityplayermp.username, new BlockFire(mcServer.worldManager));
        entityplayermp1.entityId = entityplayermp.entityId;
        entityplayermp1.playerNetServerHandler = entityplayermp.playerNetServerHandler;
        if(chunkcoordinates != null)
        {
            ChunkCoordinates chunkcoordinates1 = EntityPlayer.func_25051_a(mcServer.worldManager, chunkcoordinates);
            if(chunkcoordinates1 != null)
            {
                entityplayermp1.setLocationAndAngles((float)chunkcoordinates1.posX + 0.5F, (float)chunkcoordinates1.posY + 0.1F, (float)chunkcoordinates1.posZ + 0.5F, 0.0F, 0.0F);
                entityplayermp1.func_25050_a(chunkcoordinates);
            } else
            {
                entityplayermp1.playerNetServerHandler.sendPacket(new Packet70(0));
            }
        }
        mcServer.worldManager.chunkProvider.loadChunk((int)entityplayermp1.posX >> 4, (int)entityplayermp1.posZ >> 4);
        for(; mcServer.worldManager.getCollidingBoundingBoxes(entityplayermp1, entityplayermp1.boundingBox).size() != 0; entityplayermp1.setPosition(entityplayermp1.posX, entityplayermp1.posY + 1.0D, entityplayermp1.posZ)) { }
        entityplayermp1.playerNetServerHandler.sendPacket(new Packet9());
        entityplayermp1.playerNetServerHandler.teleportTo(entityplayermp1.posX, entityplayermp1.posY, entityplayermp1.posZ, entityplayermp1.rotationYaw, entityplayermp1.rotationPitch);
        playerManagerObj.func_26682_a(entityplayermp1);
        mcServer.worldManager.entityJoinedWorld(entityplayermp1);
        playerEntities.add(entityplayermp1);
        entityplayermp1.func_20057_k();
        entityplayermp1.func_22068_s();
        return entityplayermp1;
    }

    public void onTick()
    {
        playerManagerObj.func_26685_a();
    }

    public void markBlockNeedsUpdate(int i, int j, int k)
    {
        playerManagerObj.func_26683_a(i, j, k);
    }

    public void sendPacketToAllPlayers(Packet packet)
    {
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(i);
            entityplayermp.playerNetServerHandler.sendPacket(packet);
        }

    }

    public String getPlayerList()
    {
        String s = "";
        for(int i = 0; i < playerEntities.size(); i++)
        {
            if(i > 0)
            {
                s = (new StringBuilder()).append(s).append(", ").toString();
            }
            s = (new StringBuilder()).append(s).append(((EntityPlayerMP)playerEntities.get(i)).username).toString();
        }

        return s;
    }

    public void banPlayer(String s)
    {
        bannedPlayers.add(s.toLowerCase());
        writeBannedPlayers();
    }

    public void pardonPlayer(String s)
    {
        bannedPlayers.remove(s.toLowerCase());
        writeBannedPlayers();
    }

    private void readBannedPlayers()
    {
        try
        {
            bannedPlayers.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(bannedPlayersFile));
            for(String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                bannedPlayers.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to load ban list: ").append(exception).toString());
        }
    }

    private void writeBannedPlayers()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(bannedPlayersFile, false));
            String s;
            for(Iterator iterator = bannedPlayers.iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to save ban list: ").append(exception).toString());
        }
    }

    public void banIP(String s)
    {
        bannedIPs.add(s.toLowerCase());
        saveBannedList();
    }

    public void pardonIP(String s)
    {
        bannedIPs.remove(s.toLowerCase());
        saveBannedList();
    }

    private void loadBannedList()
    {
        try
        {
            bannedIPs.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(ipBanFile));
            for(String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                bannedIPs.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to load ip ban list: ").append(exception).toString());
        }
    }

    private void saveBannedList()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(ipBanFile, false));
            String s;
            for(Iterator iterator = bannedIPs.iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to save ip ban list: ").append(exception).toString());
        }
    }

    public void opPlayer(String s)
    {
        ops.add(s.toLowerCase());
        saveOps();
    }

    public void deopPlayer(String s)
    {
        ops.remove(s.toLowerCase());
        saveOps();
    }

    private void loadOps()
    {
        try
        {
            ops.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(opFile));
            for(String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                ops.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to load ip ban list: ").append(exception).toString());
        }
    }

    private void saveOps()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(opFile, false));
            String s;
            for(Iterator iterator = ops.iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to save ip ban list: ").append(exception).toString());
        }
    }

    private void loadWhiteList()
    {
        try
        {
            whiteListedIPs.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(whitelistPlayersFile));
            for(String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                whiteListedIPs.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to load white-list: ").append(exception).toString());
        }
    }

    private void saveWhiteList()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(whitelistPlayersFile, false));
            String s;
            for(Iterator iterator = whiteListedIPs.iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to save white-list: ").append(exception).toString());
        }
    }

    public boolean isAllowedToLogin(String s)
    {
        s = s.trim().toLowerCase();
        return !whiteListEnforced || ops.contains(s) || whiteListedIPs.contains(s);
    }

    public boolean isOp(String s)
    {
        return ops.contains(s.trim().toLowerCase());
    }

    public EntityPlayerMP getPlayerEntity(String s)
    {
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(i);
            if(entityplayermp.username.equalsIgnoreCase(s))
            {
                return entityplayermp;
            }
        }

        return null;
    }

    public void sendChatMessageToPlayer(String s, String s1)
    {
        EntityPlayerMP entityplayermp = getPlayerEntity(s);
        if(entityplayermp != null)
        {
            entityplayermp.playerNetServerHandler.sendPacket(new Packet3Chat(s1));
        }
    }

    public void sendPacketToPlayersAroundPoint(double d, double d1, double d2, double d3, Packet packet)
    {
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(i);
            double d4 = d - entityplayermp.posX;
            double d5 = d1 - entityplayermp.posY;
            double d6 = d2 - entityplayermp.posZ;
            if(d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3)
            {
                entityplayermp.playerNetServerHandler.sendPacket(packet);
            }
        }

    }

    public void sendChatMessageToAllPlayers(String s)
    {
        Packet3Chat packet3chat = new Packet3Chat(s);
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayerMP entityplayermp = playerEntities.get(i);
            if(isOp(entityplayermp.username))
            {
                entityplayermp.playerNetServerHandler.sendPacket(packet3chat);
            }
        }

    }

    public boolean sendPacketToPlayer(String s, Packet packet)
    {
        EntityPlayerMP entityplayermp = getPlayerEntity(s);
        if(entityplayermp != null)
        {
            entityplayermp.playerNetServerHandler.sendPacket(packet);
            return true;
        } else
        {
            return false;
        }
    }

    public void savePlayerStates()
    {
        for(int i = 0; i < playerEntities.size(); i++)
        {
            playerNBTManagerObj.writePlayerData((EntityPlayer)playerEntities.get(i));
        }

    }

    public void sentTileEntityToPlayer(int i, int j, int k, TileEntity tileentity)
    {
    }

    public void addToWhiteList(String s)
    {
        whiteListedIPs.add(s);
        saveWhiteList();
    }

    public void removeFromWhiteList(String s)
    {
        whiteListedIPs.remove(s);
        saveWhiteList();
    }

    public Set getWhiteListedIPs()
    {
        return whiteListedIPs;
    }

    public void reloadWhiteList()
    {
        loadWhiteList();
    }
}
