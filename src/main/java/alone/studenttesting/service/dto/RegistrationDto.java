package alone.studenttesting.service.dto;

import javax.validation.constraints.*;

public class RegistrationDto {

    @Pattern(regexp="[A-Za-z]{3,100}")
    @NotEmpty
    private String enFirstName;
    @Pattern(regexp="[А-ЩЬЮЯҐЄІЇа-щьюяґєії]{3,100}")
    @NotEmpty
    private String uaFirstName;
    @Pattern(regexp="[A-Za-z]{3,100}")
    @NotEmpty
    private String enLastName;
    @Pattern(regexp="[А-ЩЬЮЯҐЄІЇа-щьюяґєії]{3,100}")
    @NotEmpty
    private String uaLastName;
    @NotBlank
    @Email
    private String email;
    @Pattern(regexp="[0-9]{10,20}")
    @NotEmpty
    private String phone;
    @NotNull
    @NotBlank
    @Pattern(regexp="[A-Z0-9a-z]{8,30}")
    @Size(min = 8)
    private String password;
    @NotNull
    @Min(value=12)
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
