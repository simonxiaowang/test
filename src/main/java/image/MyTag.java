package image;

import java.io.Serializable;

public class MyTag implements Serializable{
	
	public MyTag(String tagName, String desc, String directName) {
		this.tagName = tagName;
		this.desc = desc;
		this.directName = directName;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 3115011059116800687L;
	String tagName;
	String desc;
	String directName;
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDirectName() {
		return directName;
	}
	public void setDirectName(String directName) {
		this.directName = directName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((directName == null) ? 0 : directName.hashCode());
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
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
		MyTag other = (MyTag) obj;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (directName == null) {
			if (other.directName != null)
				return false;
		} else if (!directName.equals(other.directName))
			return false;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Tag [tagName=" + tagName + ", desc=" + desc + ", directName=" + directName + "]";
	}
	
	
}
