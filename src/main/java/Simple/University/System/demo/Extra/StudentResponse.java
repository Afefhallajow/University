package Simple.University.System.demo.Extra;


import lombok.Data;

@Data
public class StudentResponse {
    Long id;
    String name;
    Double gpa;

    public StudentResponse() {
    }

    public StudentResponse(Long id, String name, Double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }
}
