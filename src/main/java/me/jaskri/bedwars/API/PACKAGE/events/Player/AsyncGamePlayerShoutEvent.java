package me.jaskri.bedwars.API.PACKAGE.events.Player;

public class AsyncGamePlayerShoutEvent extends AsyncGamePlayerChatEvent{

    public AsyncGamePlayerShoutEvent(GamePlayer player, String message) {
        super(player, message);
        super.setFormat("%1$s %2$s: %3$s");
}
