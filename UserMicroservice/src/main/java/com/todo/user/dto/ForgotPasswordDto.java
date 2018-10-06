package com.todo.user.dto;

/*************************************************************************************************************
 *
 * purpose:Forgot password dto
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

public class ForgotPasswordDto {
	String newPassword;
	String confirmPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "ForgotPasswordDto [newPassword=" + newPassword + ", confirmPassword=" + confirmPassword + "]";
	}

}
