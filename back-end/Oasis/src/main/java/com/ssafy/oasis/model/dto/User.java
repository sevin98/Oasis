package com.ssafy.oasis.model.dto;

public class User {
	private int id;
	private String userId;
	private String password;
	private String email;
	private int age;
	private String gender;
	private String profileImage;

	public User() {
	}

	public User(int id, String userId, String password, String email, int age, String gender, String profileImage) {
		super();
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.profileImage = profileImage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", password=" + password + ", email=" + email + ", age=" + age
				+ ", gender=" + gender + ", profileImage=" + profileImage + "]";
	}

}
