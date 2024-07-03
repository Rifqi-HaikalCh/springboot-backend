package net.javaguides.springboot.model.properties;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
public class EmployeeProperties {

    private Long id;

    private String firstName;

    private String lastName;

    private String emailId;

    private LocalDate dateOfBirth;
}