package com.openclassrooms.mddapi.dtos;

public class PasswordChangeRequestDTO {

    private String currentPassword;
    private String newPassword;

    // Getters et setters...
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "PasswordChangeRequestDTO{"
                + "currentPassword='" + currentPassword + '\''
                + ", newPassword='" + newPassword + '\''
                + '}';
    }
}
