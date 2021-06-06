package com.epam.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * 
 * Holder for all commands.
 * 
 */
public class CommandContainer {
	private static final Logger log = LogManager.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {
		// common commands
		commands.put("login", new LoginCommand());		
		commands.put("logout", new LogoutCommand());
		commands.put("registration", new RegistrationCommand());


		// client commands
		commands.put("participantMainPage", new ParticipantMainPageCommand());
		commands.put("participantAccount", new ParticipantAccountCommand());
		commands.put("participantActivities", new ParticipantActivitiesCommand());
		commands.put("participantUpdate", new ParticipantUpdateCommand());
		commands.put("participantAddActivity", new ParticipantAddActivityCommand());
		commands.put("participantUpdateActivity", new ParticipantUpdateActivityCommand());

		// admin commands
		commands.put("adminMainPage", new AdminMainPageCommand());
		commands.put("activitiesToApprove", new ActivitiesToApproveCommand());
		commands.put("activitiesToDelete", new ActivitiesToDeleteCommand());

		log.debug("Command container was successfully initialized");
		log.trace("Number of commands --> " + commands.size());
	}

	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			log.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}
	
	//temporary method
	public static Command getCommand(String commandName) {
		return commands.get(commandName);
	}
}
