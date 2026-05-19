package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonPlaceholderUser {
    private int id;
    private String name;
    private String username;
    private String email;
}
