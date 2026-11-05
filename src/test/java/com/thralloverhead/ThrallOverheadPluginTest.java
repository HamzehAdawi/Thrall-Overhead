package com.thralloverhead;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ThrallOverheadPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ThrallOverheadPlugin.class);
		RuneLite.main(args);
	}
}
