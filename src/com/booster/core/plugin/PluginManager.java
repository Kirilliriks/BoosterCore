package com.booster.core.plugin;

import com.booster.api.plugin.Plugin;
import com.booster.core.BoosterServer;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {

    private final BoosterServer boosterServer;

    private final List<Plugin> plugins;

    public PluginManager(BoosterServer boosterServer){
        this.boosterServer = boosterServer;
        this.plugins = new ArrayList<>();
        loadPlugins("plugins/");
    }

    public void loadPlugins(String path){
        File mainDirectory = new File(path);
        if (!mainDirectory.exists()) {
            if (!mainDirectory.mkdir()) throw new RuntimeException("Error with create directory");
        }
        File[] files = mainDirectory.listFiles();
        if (files == null) return;
        for (File file : files){
            JavaPlugin plugin = loadPlugin(file);
            if (plugin == null) continue;
            plugins.add(plugin);
            plugin.initialize(boosterServer);
            plugin.onEnable();
        }
    }

    public JavaPlugin loadPlugin(File pluginFile){
        JarFile jar;
        try {
            jar = new JarFile(pluginFile);

            // Get plugin.yml
            JarEntry entry = jar.getJarEntry("plugin.yml");
            if (entry == null){
                BoosterServer.logger.info("Not found plugin.yml for " + pluginFile.getName());
                return null;
            }
            Yaml yaml = new Yaml();
            Map<String, Object> obj = yaml.load(new InputStreamReader(jar.getInputStream(entry)));
            String mainName = (String) obj.get("main");
            //

            URLClassLoader classLoader = new URLClassLoader(new URL[]{pluginFile.toURI().toURL()});
            Class<?> jarClass;
            Constructor<JavaPlugin> constructor;
            try {
                jarClass = classLoader.loadClass(mainName);
                Class<JavaPlugin> plugin = (Class<JavaPlugin>) jarClass.asSubclass(JavaPlugin.class);
                constructor = plugin.getConstructor();
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }

            JavaPlugin result;
            try {
                result = constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void disablePlugins(){
        for (Plugin plugin : plugins){
            plugin.onDisable();
        }
        plugins.clear();
    }
}
