package douban;

import java.io.Serializable;

public class Movie implements Serializable{


	private static final long serialVersionUID = -8474343052189414032L;
	private String engName;
	private String chnName;
	private double rating;
	private int year;
	private boolean dundus;
	private boolean scotia;
	private boolean markham;
	
	public boolean isDundus() {
		return dundus;
	}
	public void setDundus(boolean dundus) {
		this.dundus = dundus;
	}
	public boolean isScotia() {
		return scotia;
	}
	public void setScotia(boolean scotia) {
		this.scotia = scotia;
	}
	public boolean isMarkham() {
		return markham;
	}
	public void setMarkham(boolean markham) {
		this.markham = markham;
	}
	
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getChnName() {
		return chnName;
	}
	public void setChnName(String chnName) {
		this.chnName = chnName;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return getEngName() + "\t" + getChnName() + "\t" + getRating() + "\t" + getYear();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chnName == null) ? 0 : chnName.hashCode());
		result = prime * result + ((engName == null) ? 0 : engName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + year;
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
		Movie other = (Movie) obj;
		if (chnName == null) {
			if (other.chnName != null)
				return false;
		} else if (!chnName.equals(other.chnName))
			return false;
		if (engName == null) {
			if (other.engName != null)
				return false;
		} else if (!engName.equals(other.engName))
			return false;
		if (Double.doubleToLongBits(rating) != Double.doubleToLongBits(other.rating))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	
	
}
