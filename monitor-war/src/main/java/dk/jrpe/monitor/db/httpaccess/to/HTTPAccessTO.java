package dk.jrpe.monitor.db.httpaccess.to;

/**
 * Immutable TO object for HTTP access data.
 * @author Jörgen Persson
 */
public class HTTPAccessTO {
    private final String httpStatus;
    private final String ipAddress;
    private final String action;
    private final String url;
    private final String date;
    private final String dateToMinute;    
    private final String dateTime;    
    private final Long requests;
    
    public String getHttpStatus() {
        return httpStatus;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getAction() {
        return action;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getDateToMinute() {
        return dateToMinute;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Long getRequests() {
        return requests;
    }

    private HTTPAccessTO(Builder builder) {
        this.httpStatus = builder.httpStatus;
        this.ipAddress = builder.ipAddress;
        this.date = builder.date;
        this.dateToMinute = builder.dateToMinute;
        this.dateTime = builder.dateTime;
        this.action = builder.action;
        this.url = builder.url;
        this.requests = builder.requests;
    }
    
    public static class Builder{
        private String httpStatus;
        private String ipAddress;
        private String date;
        private String dateTime;
        private String dateToMinute;    
        private Long requests = new Long("0");
        
        private String action = "GET";
        private String url = "/";
        
        public Builder setHttpStatus(String httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder setIPAdress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public Builder setDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setDateToMinute(String dateToMinute) {
            this.dateToMinute = dateToMinute;
            return this;
        }

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setRequests(Long requests) {
            this.requests = requests;
            return this;
        }
        
        public HTTPAccessTO build() {
            return new HTTPAccessTO(this);
        }
    }

    @Override
    public String toString() {
        return "HTTPAccessTO{" + "httpStatus=" + httpStatus + ", ipAddress=" + ipAddress + ", action=" + action + ", url=" + url + ", date=" + date + ", dateToMinute=" + dateToMinute + ", dateTime=" + dateTime + ", requests=" + requests + '}';
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result
				+ ((dateToMinute == null) ? 0 : dateToMinute.hashCode());
		result = prime * result
				+ ((httpStatus == null) ? 0 : httpStatus.hashCode());
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result
				+ ((requests == null) ? 0 : requests.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HTTPAccessTO other = (HTTPAccessTO) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (dateToMinute == null) {
			if (other.dateToMinute != null)
				return false;
		} else if (!dateToMinute.equals(other.dateToMinute))
			return false;
		if (httpStatus == null) {
			if (other.httpStatus != null)
				return false;
		} else if (!httpStatus.equals(other.httpStatus))
			return false;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (requests == null) {
			if (other.requests != null)
				return false;
		} else if (!requests.equals(other.requests))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
