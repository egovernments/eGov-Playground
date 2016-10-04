package org.egov.process.web.adaptor;

import java.lang.reflect.Type;
import org.egov.process.entity.Inbox;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class InboxJsonAdaptor implements JsonSerializer<Inbox> {
	@Override
	public JsonElement serialize(final Inbox inbox, final Type type, final JsonSerializationContext jsc) {
		final JsonObject jsonObject = new JsonObject();
		if (inbox != null) {
			if (inbox.getTaskId() != null)
				jsonObject.addProperty("taskId", inbox.getTaskId());
			else
				jsonObject.addProperty("taskId", "");
			if (inbox.getSender() != null)
				jsonObject.addProperty("sender", inbox.getSender());
			else
				jsonObject.addProperty("sender", "");
			if (inbox.getNatureOfWork() != null)
				jsonObject.addProperty("natureOfWork", inbox.getNatureOfWork());
			else
				jsonObject.addProperty("natureOfWork", "");
			if (inbox.getDetails() != null)
				jsonObject.addProperty("details", inbox.getDetails());
			else
				jsonObject.addProperty("details", "");
			if (inbox.getLink() != null)
				jsonObject.addProperty("link", inbox.getLink());
			else
				jsonObject.addProperty("link", "");
			jsonObject.addProperty("id", inbox.getId());
		}
		return jsonObject;
	}
}