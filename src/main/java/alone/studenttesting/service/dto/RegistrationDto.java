package alone.studenttesting.service.dto;

import javax.validation.constraints.*;

public class RegistrationDto {

    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "english first name must not be empty")
    private String enFirstName;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "ukrainian first name must not be empty")
    private String uaFirstName;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "english last name must not be empty")
    private String enLastName;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "ukrainian last name must not be empty")
    private String uaLastName;
    @NotBlank(message = "Email field blank!")
    @Email
    private String email;
    /*@NotBlank
    @Pattern(regexp="[0-9]{8,30}")
    @NotEmpty(message = "number must be a format of +XX0XXXXXXXXX")*/
    private String phone;
    /*@NotNull(message = "Password field null!")
    @NotBlank(message = "Password field blank!")
    @Pattern(regexp="[A-Z0-9a-z]{8,30}")
    @Size(min = 8, max = 30, message = "Password field not in size 8 - 30.")*/
    private String password;
    @NotNull(message = "age must not be null")
    @Min(value=5, message="must be equal or greater than 1")
    @Max(value=70, message="must be equal or less than 70")
    private Long age;

    public String getEnFirstName() {
        return enFirstName;
    }

    public void setEnFirstName(String enFirstName) {
        this.enFirstName = enFirstName;
    }

    public String getUaFirstName() {
        return uaFirstName;
    }

    public void setUaFirstName(String uaFirstName) {
        this.uaFirstName = uaFirstName;
    }

    public String getEnLastName() {
        return enLastName;
    }

    public void setEnLastName(String enLastName) {
        this.enLastName = enLastName;
    }

    public String getUaLastName() {
        return uaLastName;
    }

    public void setUaLastName(String uaLastName) {
        this.uaLastName = uaLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
