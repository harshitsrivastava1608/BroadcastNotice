package com.example.project4.Admins;

public class AdminData {
    private String name,email,phone,image,key;

    public AdminData() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public AdminData(String name, String email, String phone,String image, String key) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.image=image;
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getKey() {
        return key;
    }
}
