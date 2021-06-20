package cskies_hina.fishing;

import org.bukkit.event.Listener;
import java.lang.Math;
import java.util.Formatter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import static org.bukkit.event.player.PlayerFishEvent.State;
import java.util.logging.Level;

public class PlayerFishingListener implements Listener{
	
	private String command;
	private double weight;
	private int p_max_distance;
	boolean debugging = true;
	
	public PlayerFishingListener(FileConfiguration config)
	{
		p_max_distance = config.getInt("p_max_distance");
		
		command = config.getString("callOnSuccess");
		weight = Double.valueOf(config.getString("weight"));
	}
	
	@EventHandler
	private void onPlayerFish(PlayerFishEvent event)
	{		
		if(event.getState().equals(State.CAUGHT_FISH))
		{
			double result = Math.random();
			if(debugging)
			{
				Formatter formatter = new Formatter();
				formatter.format("result %3f :: %3f", result, weight);
				Bukkit.getLogger().log(Level.INFO, formatter.toString());
				formatter.close();
				}
			
			if(result < weight)
			{
				Player player = event.getPlayer();
				
				if(command.contains("@s"))
				{																					// Command likely requires server-level
					command.replace("@s", player.getName());										// permissions, so replace the usual
				}																					// target selectors with equivalents
				
				if(command.contains("@p"))
				{																					// distance must be either exact or in
					command.replace("@p", "@a[limit=1, sort=nearest, distance=.1.." + p_max_distance + "]");	// a range, so simulate @p
				}																					// by excluding the triggering player
																									// by min distance and find the nearest
																									// player within a configurable max
				
				command.split("");
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute at" + player.getName() + " " + command);
			}
		}
	}
	
}
