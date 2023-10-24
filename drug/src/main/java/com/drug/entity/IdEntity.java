package com.drug.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public abstract class IdEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = -6644147333627738109L;
	@TableId(value = "id", type = IdType.AUTO)
	protected Integer id;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IdEntity)) {
			return false;
		}
		IdEntity other = (IdEntity) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			} else {
				return super.equals(obj);
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		IdEntity clone = (IdEntity) super.clone();
		clone.setId(null);
		return clone;
	}

}
