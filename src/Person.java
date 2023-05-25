public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private int familyMembers;
    private String phone;

    public Person( String firstName, String lastName, int familyMembers,String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.familyMembers = familyMembers;
        this.phone=phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(int familyMembers) {
        this.familyMembers = familyMembers;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", first Name='" + firstName +
                ", last Name='" + lastName +
                ", family Members=" + familyMembers
                +"phone=" +phone;
    }

}
