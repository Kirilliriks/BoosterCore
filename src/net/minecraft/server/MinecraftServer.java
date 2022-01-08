// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import com.booster.core.BoosterServer;
import com.booster.core.console.ConsoleCommandSender;
import net.minecraft.src.entity.AxisAlignedBB;
import net.minecraft.src.chunk.ChunkCoordinates;
import net.minecraft.src.ConsoleCommandHandler;
import net.minecraft.src.ConsoleLogManager;
import net.minecraft.src.ConvertProgressUpdater;
import net.minecraft.src.entity.EntityTracker;
import net.minecraft.src.ICommandListener;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.IUpdatePlayerListBox;
import net.minecraft.src.network.NetworkListenThread;
import net.minecraft.src.packet.Packet4UpdateTime;
import net.minecraft.src.PropertyManager;
import net.minecraft.src.SaveConverterMcRegion;
import net.minecraft.src.SaveOldDir;
import net.minecraft.src.server.ServerCommand;
import net.minecraft.src.server.ServerConfigurationManager;
import net.minecraft.src.server.ServerGUI;
import net.minecraft.src.ThreadCommandReader;
import net.minecraft.src.ThreadServerApplication;
import net.minecraft.src.ThreadSleepForever;
import net.minecraft.src.Vec3D;
import net.minecraft.src.world.WorldManager;
import net.minecraft.src.world.WorldServer;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinecraftServer implements Runnable, ICommandListener {

    public static Logger logger = Logger.getLogger("Minecraft");
    public static HashMap field_6037_b = new HashMap();
    public NetworkListenThread networkServer;
    public PropertyManager propertyManagerObj;
    public WorldServer worldManager;
    public ServerConfigurationManager configManager;
    private ConsoleCommandHandler commandHandler;
    private boolean serverRunning;
    public boolean serverStopped;
    int deathTime;
    public String currentTask;
    public int percentDone;
    private List field_9010_p;
    private List<ServerCommand> commands;
    public EntityTracker entityTracker;
    public boolean onlineMode;
    public boolean spawnPeacefulMobs;
    public boolean pvpOn;

    //Booster
    private BoosterServer boosterServer;
    private ConsoleCommandSender consoleSender;
    //

    public MinecraftServer() {
        serverRunning = true;
        serverStopped = false;
        deathTime = 0;
        field_9010_p = new ArrayList<>();
        commands = Collections.synchronizedList(new ArrayList<>());
        new ThreadSleepForever(this);
    }

    private boolean startServer() throws UnknownHostException {
        commandHandler = new ConsoleCommandHandler(this);
        ThreadCommandReader threadcommandreader = new ThreadCommandReader(this);
        threadcommandreader.setDaemon(true);
        threadcommandreader.start();
        ConsoleLogManager.init();
        logger.info("Starting minecraft server version Beta 1.4");
        if(Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L)
        {
            logger.warning("**** NOT ENOUGH RAM!");
            logger.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }
        logger.info("Loading properties");
        propertyManagerObj = new PropertyManager(new File("server.properties"));
        String s = propertyManagerObj.getStringProperty("server-ip", "");
        onlineMode = propertyManagerObj.getBooleanProperty("online-mode", true);
        spawnPeacefulMobs = propertyManagerObj.getBooleanProperty("spawn-animals", true);
        pvpOn = propertyManagerObj.getBooleanProperty("pvp", true);
        InetAddress inetaddress = null;
        if(s.length() > 0)
        {
            inetaddress = InetAddress.getByName(s);
        }
        int i = propertyManagerObj.getIntProperty("server-port", 25565);
        logger.info("Starting Minecraft server on " + (s.length() != 0 ? s : "*") + ":" + i);
        try
        {
            networkServer = new NetworkListenThread(this, inetaddress, i);
        }
        catch(IOException ioexception)
        {
            logger.warning("**** FAILED TO BIND TO PORT!");
            logger.log(Level.WARNING, "The exception was: " + ioexception.toString());
            logger.warning("Perhaps a server is already running on that port?");
            return false;
        }
        if(!onlineMode)
        {
            logger.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            logger.warning("The server will make no attempt to authenticate usernames. Beware.");
            logger.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            logger.warning("To change this, set \"online-mode\" to \"true\" in the server.settings file.");
        }
        configManager = new ServerConfigurationManager(this);
        entityTracker = new EntityTracker(this);

        // Booster
        boosterServer = new BoosterServer(this);
        consoleSender = new ConsoleCommandSender(boosterServer);
        //

        long l = System.nanoTime();
        String s1 = propertyManagerObj.getStringProperty("level-name", "world");
        String s2 = propertyManagerObj.getStringProperty("level-seed", "");
        long l1 = (new Random()).nextLong();
        if(s2.length() > 0)
        {
            try
            {
                l1 = Long.parseLong(s2);
            }
            catch(NumberFormatException numberformatexception)
            {
                l1 = s2.hashCode();
            }
        }
        logger.info("Preparing level \"" + s1 + "\"");
        initWorld(new SaveConverterMcRegion(new File(".")), s1, l1);
        logger.info("Done (" + (System.nanoTime() - l) + "ns)! For help, type \"help\" or \"?\"");
        return true;
    }

    private void initWorld(ISaveFormat isaveformat, String s, long l)
    {
        if(isaveformat.func_22102_a(s))
        {
            logger.info("Converting map!");
            isaveformat.func_26729_a(s, new ConvertProgressUpdater(this));
        }
        logger.info("Preparing start region");
        worldManager = new WorldServer(this, new SaveOldDir(new File("."), s, true), s, propertyManagerObj.getBooleanProperty("hellworld", false) ? -1 : 0, l);
        worldManager.addWorldAccess(new WorldManager(this));
        worldManager.difficultySetting = propertyManagerObj.getBooleanProperty("spawn-monsters", true) ? 1 : 0;
        worldManager.setAllowedSpawnTypes(propertyManagerObj.getBooleanProperty("spawn-monsters", true), spawnPeacefulMobs);
        configManager.setPlayerManager(worldManager);
        char c = '\304';
        long l1 = System.currentTimeMillis();
        ChunkCoordinates chunkcoordinates = worldManager.getSpawnPoint();
        for(int i = -c; i <= c && serverRunning; i += 16)
        {
            for(int j = -c; j <= c && serverRunning; j += 16)
            {
                long l2 = System.currentTimeMillis();
                if(l2 < l1)
                {
                    l1 = l2;
                }
                if(l2 > l1 + 1000L)
                {
                    int k = (c * 2 + 1) * (c * 2 + 1);
                    int i1 = (i + c) * (c * 2 + 1) + (j + 1);
                    outputPercentRemaining("Preparing spawn area", (i1 * 100) / k);
                    l1 = l2;
                }
                worldManager.chunkProvider.loadChunk(chunkcoordinates.posX + i >> 4, chunkcoordinates.posZ + j >> 4);
                while(worldManager.func_6156_d() && serverRunning);
            }

        }

        clearCurrentTask();
    }

    private void outputPercentRemaining(String s, int i)
    {
        currentTask = s;
        percentDone = i;
        logger.info(s + ": " + i + "%");
    }

    private void clearCurrentTask()
    {
        currentTask = null;
        percentDone = 0;
    }

    private void saveServerWorld()
    {
        logger.info("Saving chunks");
        worldManager.func_26660_a(true, null);
        worldManager.func_22088_r();
    }

    private void stopServer() {
        logger.info("Stopping server");

        // Booster
        boosterServer.stopServer();
        //

        if(configManager != null)
        {
            configManager.savePlayerStates();
        }
        if(worldManager != null)
        {
            saveServerWorld();
        }
    }

    public void initiateShutdown()
    {
        serverRunning = false;
    }

    public void run()
    {
        try
        {
            if(startServer())
            {
                long l = System.currentTimeMillis();
                long l1 = 0L;
                while(serverRunning) 
                {
                    long l2 = System.currentTimeMillis();
                    long l3 = l2 - l;
                    if(l3 > 2000L)
                    {
                        logger.warning("Can't keep up! Did the system time change, or is the server overloaded?");
                        l3 = 2000L;
                    }
                    if(l3 < 0L)
                    {
                        logger.warning("Time ran backwards! Did the system time change?");
                        l3 = 0L;
                    }
                    l1 += l3;
                    l = l2;
                    if(worldManager.isAllPlayersFullyAsleep())
                    {
                        doTick();
                        l1 = 0L;
                    } else
                    {
                        while(l1 > 50L) 
                        {
                            l1 -= 50L;
                            doTick();
                        }
                    }
                    Thread.sleep(1L);
                }
            } else
            {
                while(serverRunning) 
                {
                    commandLineParser();
                    try
                    {
                        Thread.sleep(10L);
                    }
                    catch(InterruptedException interruptedexception)
                    {
                        interruptedexception.printStackTrace();
                    }
                }
            }
        }
        catch(Throwable throwable1)
        {
            throwable1.printStackTrace();
            logger.log(Level.SEVERE, "Unexpected exception", throwable1);
            while(serverRunning) 
            {
                commandLineParser();
                try
                {
                    Thread.sleep(10L);
                }
                catch(InterruptedException interruptedexception1)
                {
                    interruptedexception1.printStackTrace();
                }
            }
            try
            {
                stopServer();
                serverStopped = true;
            }
            catch(Throwable throwable2)
            {
                throwable2.printStackTrace();
            }
            finally
            {
                System.exit(0);
            }
//            break MISSING_BLOCK_LABEL_341;
        }
        try
        {
            stopServer();
            serverStopped = true;
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }
//        break MISSING_BLOCK_LABEL_341;
//        Exception exception2;
//        exception2;
        try
        {
            stopServer();
            serverStopped = true;
        }
        catch(Throwable throwable3)
        {
            throwable3.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }
//        throw exception2;
    }

    private void doTick()
    {
        ArrayList arraylist = new ArrayList();
        for(Iterator iterator = field_6037_b.keySet().iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            int k = ((Integer)field_6037_b.get(s)).intValue();
            if(k > 0)
            {
                field_6037_b.put(s, Integer.valueOf(k - 1));
            } else
            {
                arraylist.add(s);
            }
        }

        for(int i = 0; i < arraylist.size(); i++)
        {
            field_6037_b.remove(arraylist.get(i));
        }

        AxisAlignedBB.clearBoundingBoxPool();
        Vec3D.initialize();
        deathTime++;
        if(deathTime % 20 == 0)
        {
            configManager.sendPacketToAllPlayers(new Packet4UpdateTime(worldManager.getWorldTime()));
        }
        worldManager.tick();
        while(worldManager.func_6156_d()) ;
        worldManager.updateEntities();
        networkServer.func_715_a();
        configManager.onTick();
        entityTracker.updateTrackedEntities();
        for(int j = 0; j < field_9010_p.size(); j++)
        {
            ((IUpdatePlayerListBox)field_9010_p.get(j)).update();
        }

        try
        {
            commandLineParser();
        }
        catch(Exception exception)
        {
            logger.log(Level.WARNING, "Unexpected exception while parsing console command", exception);
        }
    }

    public void addCommand(String s, ICommandListener icommandlistener) {
        commands.add(new ServerCommand(s, icommandlistener));
    }

    public void commandLineParser() {
        ServerCommand serverCommand;
        for(; commands.size() > 0; commandHandler.handleCommand(serverCommand)) {
            serverCommand = commands.remove(0);
            // Booster
            boosterServer.dispatchCommand(serverCommand.command, consoleSender);
            //
        }

    }

    public void func_6022_a(IUpdatePlayerListBox iupdateplayerlistbox)
    {
        field_9010_p.add(iupdateplayerlistbox);
    }

    public static void main(String[] args)
    {
        try
        {
            MinecraftServer minecraftserver = new MinecraftServer();
            if(!GraphicsEnvironment.isHeadless() && (args.length <= 0 || !args[0].equals("nogui")))
            {
                ServerGUI.initGui(minecraftserver);
            }
            (new ThreadServerApplication("Server thread", minecraftserver)).start();
        }
        catch(Exception exception)
        {
            logger.log(Level.SEVERE, "Failed to start the minecraft server", exception);
        }
    }

    public File getFile(String s)
    {
        return new File(s);
    }

    public void log(String s)
    {
        logger.info(s);
    }

    public void func_25002_c(String s)
    {
        logger.warning(s);
    }

    public String getUsername()
    {
        return "CONSOLE";
    }

    public static boolean isServerRunning(MinecraftServer minecraftserver)
    {
        return minecraftserver.serverRunning;
    }

    // Booster
    public BoosterServer getBoosterServer(){
        return boosterServer;
    }
    //
}
