package pojos;

import lombok.Data;

@Data
public class UserResponse {
    private String name;
    private String job;
    private int id;
    private String createdAt;
}
