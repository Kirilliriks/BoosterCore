package net.minecraft.src.server;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class ServerGuiFocusAdapter extends FocusAdapter
{

    ServerGuiFocusAdapter(ServerGUI servergui)
    {
        mcServerGui = servergui;
    }

    public void focusGained(FocusEvent focusevent)
    {
    }

    final ServerGUI mcServerGui; /* synthetic field */
}
