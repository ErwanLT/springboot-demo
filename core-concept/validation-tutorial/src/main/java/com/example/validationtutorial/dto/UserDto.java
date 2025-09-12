package com.example.validationtutorial.dto;

import com.example.validationtutorial.validation.Password;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @Password(message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and be between 8 and 20 characters long")
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 100, message = "Age should not be greater than 100")
    private int age;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @URL(message = "Website URL should be valid")
    private String website;

    @PastOrPresent(message = "Registration date cannot be in the future")
    private LocalDate registrationDate;

    @CreditCardNumber(message = "Credit card number should be valid")
    private String creditCardNumber;

    @NotEmpty(message = "Hobbies cannot be empty")
    @Size(min = 1, max = 5, message = "User must have between 1 and 5 hobbies")
    private List<String> hobbies;

    @NotNull(message = "Preferences cannot be null")
    private List<String> preferences;

}
