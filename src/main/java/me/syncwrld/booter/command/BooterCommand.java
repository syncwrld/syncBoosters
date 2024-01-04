package me.syncwrld.booter.command;

import co.aikar.commands.BaseCommand;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class BooterCommand extends BaseCommand {

  private final List<String> helpMessage = new ArrayList<>();

  public boolean isInstance(Object o, Class<?> clazz) {
    return clazz.isInstance(o);
  }

  public boolean isPlayer(Object o) {
    return o instanceof org.bukkit.entity.Player;
  }

  public boolean isConsole(Object o) {
    return o instanceof org.bukkit.command.ConsoleCommandSender;
  }

  public void sendActionbar(org.bukkit.command.CommandSender sender, String message) {
    if (sender instanceof org.bukkit.entity.Player) {
      org.bukkit.entity.Player player = (org.bukkit.entity.Player) sender;
      ActionBar.sendActionBar(player, transform(message, sender));
    }
  }

  public void sendTitle(org.bukkit.command.CommandSender sender, String title, String subtitle) {
    if (sender instanceof org.bukkit.entity.Player) {
      org.bukkit.entity.Player player = (org.bukkit.entity.Player) sender;
      Titles.sendTitle(
          player,
          replaceChars(title.replace("$name", nameOf(player))),
          replaceChars(subtitle.replace("$name", nameOf(player))));
    }
  }

  public String replaceChars(String message) {
    return message.replace("&", "§").replace("<nl>", "\n").replace("\n\n", "\n \n");
  }

  public void setHelpMessage(List<String> helpMessage) {
    this.helpMessage.addAll(helpMessage);
  }

  public void sendHelp(org.bukkit.command.CommandSender sender) {
    for (String message : this.helpMessage) {
      sender.sendMessage(replaceChars(message.replace("$name", nameOf(sender))));
    }
  }

  public void ifConsoleElse(
      org.bukkit.command.CommandSender sender, Runnable positive, Runnable negative) {
    if (isConsole(sender)) {
      positive.run();
    } else {
      negative.run();
    }
  }

  public void ifElse(Supplier<Boolean> condition, Runnable positive, Runnable negative) {
    if (condition.get()) {
      positive.run();
    } else {
      negative.run();
    }
  }

  public String nameOf(org.bukkit.command.CommandSender sender) {
    return isPlayer(sender) ? ((org.bukkit.entity.Player) sender).getName() : "Console";
  }

  public String ipv4Of(org.bukkit.command.CommandSender sender) {
    return isPlayer(sender)
        ? ((org.bukkit.entity.Player) sender).getAddress().getHostName()
        : "127.0.0.1";
  }

  public String worldOf(org.bukkit.command.CommandSender sender) {
    return isPlayer(sender) ? ((org.bukkit.entity.Player) sender).getWorld().getName() : "linux";
  }

  public String transform(String message, org.bukkit.command.CommandSender sender) {
    return replaceChars(message)
        .replace("$name", nameOf(sender))
        .replace("$ip", ipv4Of(sender))
        .replace("$world", worldOf(sender));
  }

  public org.bukkit.entity.Player asPlayer(org.bukkit.command.CommandSender sender) {
    return isPlayer(sender) ? (org.bukkit.entity.Player) sender : null;
  }

  public void sendMessage(org.bukkit.command.CommandSender sender, String message) {
    sender.sendMessage(transform(message, sender));
  }
  
}
