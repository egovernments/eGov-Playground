package org.egov.notification.web.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SMSRequest {

	@JsonProperty("RequestInfo")
	private RequestInfo requestInfo = null;

	@JsonProperty("mobile_no")
	private String mobileNo = null;

	@JsonProperty("message")
	private String message = null;

	public SMSRequest requestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
		return this;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public SMSRequest mobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
		return this;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public SMSRequest message(String message) {
		this.message = message;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SMSRequest smSRequest = (SMSRequest) o;
		return Objects.equals(this.requestInfo, smSRequest.requestInfo)
				&& Objects.equals(this.mobileNo, smSRequest.mobileNo)
				&& Objects.equals(this.message, smSRequest.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(requestInfo, mobileNo, message);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class SMSRequest {\n");

		sb.append("    requestInfo: ").append(toIndentedString(requestInfo)).append("\n");
		sb.append("    mobileNo: ").append(toIndentedString(mobileNo)).append("\n");
		sb.append("    message: ").append(toIndentedString(message)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
