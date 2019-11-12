package com.example.logger;

public class User {
    private String name;
    private String document;
    private String email;
    private String gender;
    private String profile;
    private String password;
    private String passwordConfirmation;

    public User(String name, String document, String email, String gender, String profile, String password, String passwordConfirmation) {
        this.name = name;
        this.document = document;
        this.email = email;
        this.gender = gender;
        this.profile = profile;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String validateCompleteInformation() {
        String error;
        if(this.name.isEmpty()) {
            error = "Por favor ingrese su nombre completo.";
        } else if(this.document.isEmpty()) {
            error = "Por favor ingrese su número de identificación.";
        } else if(this.email.isEmpty()) {
            error = "Por favor ingrese su dirección de correo.";
        } else if(this.password.isEmpty()) {
            error = "Por favor ingrese una contraseña.";
        } else if(this.passwordConfirmation.isEmpty()) {
            error = "Por favor confirme la contraseña.";
        } else if(this.gender.isEmpty()) {
            error = "Por favor seleccione su género.";
        } else {
            error = "";
        }
        return error;
    }

    public boolean validatePassword() {
        boolean isValid = false;
        if(!this.password.isEmpty() && !this.passwordConfirmation.isEmpty()) {
            if(this.password.compareTo(this.passwordConfirmation) == 0) {
                isValid = true;
            }
        }
        return isValid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getDocument() { return document; }

    public String getProfile() { return profile; }
}
