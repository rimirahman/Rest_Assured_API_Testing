public class CreateUserModel {
    public String name;
    public String email;
    public String password;
    public String phone_number;
    public String nid;
    public String role;

    public CreateUserModel(String name,String email,String password,String phone,String nid,String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone_number = phone;
        this.nid = nid;
        this.role = role;
    }
}
