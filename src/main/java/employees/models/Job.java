package employees.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Job {
    private Long id;
    private String position;
    private String profession;
    private String description;
    private int experience;
}
