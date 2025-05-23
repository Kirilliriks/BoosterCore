package com.booster.api;

import com.booster.core.command.CommandHandler;

import java.util.logging.Logger;

public interface Server {

    Logger getLogger();

    CommandHandler getCommandHandler();
}
