package com.epam.redkin.web.controller.command;

import com.epam.redkin.web.controller.command.common.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory factory = new CommandFactory();
    private static final Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {
    }

    public static CommandFactory commandFactory() {
        if (factory == null) {
            factory = new CommandFactory();
        }
        return factory;
    }

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("home", new HomeCommand());
        commands.put("i18n", new I18NCommand());

        /* commands.put("logout", new LogoutCommand());
        commands.put("pdf_builder", new PdfBuilderCommand());
        commands.put("no_command", new NoCommand());*/

        commands.put("redirect", null);

        // admin commands
        /*commands.put("main", new MainCommand());
        commands.put("users", new ShowUserListCommand());
        commands.put("services", new EditServicesCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("edit_client", new EditClientCommand());
        commands.put("profile", new ProfileCommand());
        commands.put("add_tariff", new AddTariffCommand());
        commands.put("edit_tariff", new EditTariffCommand());
        commands.put("remove_tariff", new RemoveTariffCommand());*/

        // user commands
        commands.put("search_routes", new SearchRoutesCommand());
        commands.put("orders", new OrderCommand());
        commands.put("route", new DetailRouteCommand());
        commands.put("select_station_and_carriage_type", new SelectStationAndCarriageTypeCommand());
        commands.put("select_carriage_and_count_seats", new SelectCarriageAndSeatsCommand());
        commands.put("select_seats", new SelectSeatsCommand());
        /*commands.put("account", new AccountCommand());
        commands.put("personal_data", new PersonalDataCommand());
        commands.put("user_profile", new UserProfileCommand());
        commands.put("transactions", new TransactionCommand());
        commands.put("save_profile", new SaveUserProfileCommand());*/
    }


    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        return commands.get(action);
    }
}
