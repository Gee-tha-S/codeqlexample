package com.todo.user.model;

/*************************************************************************************************************
*
* purpose:POJO class
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/
import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

/*************************************************************************************************************
 *
 * purpose:Entity class
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

@JsonInclude(JsonInclude.Include.NON_EMPTY)
// @Document(indexName = "userelastic", type = "users")
//@Document(collection="login")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String emailId;
	private String userName;
	private String password;
	private String phoneNumber;
	private boolean activate;
	private String profilePic;

	public User() {
		// TODO Auto-generated constructor stub
	}

	

	public String getId() {
		return id;
	}



	public String setId(String id) {
		return this.id = id;
	}



	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", emailId=" + emailId + ", userName=" + userName + ", password=" + password
				+ ", phoneNumber=" + phoneNumber + ", activate=" + activate + ", profilePic=" + profilePic + "]";
	}

}
