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
		commands.put("participantUpdateAccount", new ParticipantUpdateAccountCommand());
		commands.put("participantChooseNewActivity", new ParticipantChooseNewActivityCommand());
		commands.put("participantAddActivity", new ParticipantAddActivityCommand());
		commands.put("addActivityDuration", new ParticipantAddActivityDurationCommand());
		commands.put("participantDeleteActivity", new ParticipantDeleteActivityCommand());

		// admin commands
		commands.put("adminMainPage", new AdminMainPageCommand());
		commands.put("activitiesToApprove", new ActivitiesToApproveCommand());
		commands.put("activitiesToDelete", new ActivitiesToDeleteCommand());
		commands.put("approveActivity", new ApproveActivityCommand());
		commands.put("rejectApproveActivity", new RejectApproveActivityCommand());
		commands.put("deleteParticipantsActivity", new AdminDeleteParticipantsActivityCommand());
		commands.put("rejectDeleteParticipantsActivity", new AdminRejectDeleteParticipantsActivityCommand());
		commands.put("createCategory", new CreateCategoryCommand());
		commands.put("adminCreateNewActivity", new AdminCreateNewActivityCommand());
		commands.put("adminSaveCreatedActivity", new AdminSaveCreatedActivityCommand());
		commands.put("categories", new CategoriesCommand());
		commands.put("deleteCategory", new DeleteCategoryCommand());
		commands.put("updateCategory", new UpdateCategoryCommand());
		commands.put("saveUpdatedCategory", new SaveUpdatedCategoryCommand());
		commands.put("activities", new ActivitiesCommand());
		commands.put("deleteActivity", new DeleteActivityCommand());
		commands.put("updateActivity", new UpdateActivityCommand());
		commands.put("saveUpdatedActivity", new SaveUpdatedActivityCommand());

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
