package capstone.gwttrial.client.calendar;

import java.util.Map;

import capstone.gwttrial.client.Presenter;
import capstone.gwttrial.client.doevent.CreateEvent;
import capstone.gwttrial.client.login.LogoutEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class CalendarPresenter implements Presenter {

	private final EventBus eventBus;
	private final CalendarViewHandler viewHandler;
	private final String username;

	public CalendarPresenter(EventBus eventBus, CalendarViewHandler view,
			String username) {
		this.eventBus = eventBus;
		this.viewHandler = view;
		this.username = username;
	}

	public void bind() {
		viewHandler.getProfileButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// eventBus.fireEvent(new AddEvent());
			}
		});

		viewHandler.getLogoutButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LogoutEvent("logout"));
			}
		});

		Map<Button, EventDetails> buttonEvMap = CalendarWidget
				.getEventButtonMap();
		if (buttonEvMap != null && !buttonEvMap.isEmpty()) {
			for (Button button : buttonEvMap.keySet()) {
				button.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						// eventBus.fireEvent(new EditEvent("edit"));
					}
				});
			}
		}

		final HTMLTable grid = viewHandler.getCalendar();
		grid.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Cell src = grid.getCellForEvent(event);
				if (src != null) {
					int row = src.getRowIndex();
					int col = src.getCellIndex();
					Constants.logger
							.severe("CALENDARPRESENTER.JAVA: CELL SOURCE ROW, COLUMN: "
									+ row + "," + col);

					if (row != 0 && row != 1 && col != 0) {
						eventBus.fireEvent(new CreateEvent(row, col,
								"createEvent"));
					}
				} else {
					Constants.logger
							.severe("CALENDARPRESENTER.JAVA: CELL SOURCE FOR CLICKEVENT IS NULL");
				}
			}
		});
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(viewHandler.asWidget());
	}

	public String getCurrentUser() {
		return username;
	}

	public EventBus getEventBus() {
		return eventBus;
	}
}
