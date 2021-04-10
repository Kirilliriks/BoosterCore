package com.booster.core.plugin;

import com.booster.api.plugin.Plugin;
import com.booster.core.BoosterServer;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {

    private final List<Plugin> plugins;

    public PluginManager(){
        this.plugins = new ArrayList<>();
        loadPlugins("plugins/");
    }

    public void loadPlugins(String path){
        File mainDirectory = new File(path);
        if (!mainDirectory.exists()) {
            if (!mainDirectory.mkdir()) throw new RuntimeException("Error with create directory");
        }
        List<File> files = Arrays.asList(mainDirectory.listFiles());
        for (File file : files){
            Plugin plugin = loadPlugin(file);
            if (plugin == null) continue;
            plugins.add(plugin);
            plugin.onEnable();
        }
    }

    public JavaPlugin loadPlugin(File pluginFile){
        JarFile jar;
        try {
            jar = new JarFile(pluginFile);
            URL jarURL = pluginFile.toURI().toURL();
            JarEntry entry = jar.getJarEntry("plugin.yml");
            if (entry == null){
                throw new RuntimeException("plugin.yml not found");
            }
            Yaml yaml = new Yaml();
            Map<String, Object> obj = yaml.load(new InputStreamReader(jar.getInputStream(entry)));
            String mainName = (String) obj.get("main");
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
            Class<?> jarClass = null;
            try {
                jarClass = classLoader.loadClass(mainName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Class<JavaPlugin> plugin = (Class<JavaPlugin>) jarClass.asSubclass(JavaPlugin.class);
            Constructor<JavaPlugin> constructor = null;
            try {
                constructor = plugin.getConstructor();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            JavaPlugin result = null;
            try {
                result = constructor.newInstance(new Object[0]);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
