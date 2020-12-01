package alone.studenttesting.service.dto;

import java.util.List;

public class UserEditDto {

    private Long id;
    private String enFirstName;
    private String UaFirstname;
    private String EnLastname;
    private String UaLastName;
    private String email;
    private String phone;
    private String password;
    private Long age;
    private List<Long> testIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnFirstName() {
        return enFirstName;
    }

    public void setEnFirstName(String enFirstName) {
        this.enFirstName = enFirstName;
    }

    public String getUaFirstname() {
        return UaFirstname;
    }

    public void setUaFirstname(String uaFirstname) {
        this.UaFirstname = uaFirstname;
    }

    public String getEnLastname() {
        return EnLastname;
    }

    public void setEnLastname(String enLastname) {
        this.EnLastname = enLastname;
    }

    public String getLastName_ua() {
        return UaLastName;
    }

    public void setLastName_ua(String lastName_ua) {
        this.UaLastName = lastName_ua;
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

    public String getUaLastName() {
        return UaLastName;
    }

    public void setUaLastName(String uaLastName) {
        UaLastName = uaLastName;
    }

    public List<Long> getTestIds() {
        return testIds;
    }

    public void setTestIds(List<Long> testIds) {
        this.testIds = testIds;
    }
}
