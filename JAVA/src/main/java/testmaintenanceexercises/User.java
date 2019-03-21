package testmaintenanceexercises;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "Users")
public class User {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name= "Memory")
	private Long memoryValue;
	
	public User() {
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMemory(Long value) {
		this.memoryValue = value;
	}
	
	public Long getMemory() {
		return this.memoryValue;
	}
	
}
