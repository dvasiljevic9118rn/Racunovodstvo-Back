package rs.raf.demo.requests;

import lombok.Data;
import rs.raf.demo.model.Permission;

import java.util.List;

@Data
public class UpdateUserRequest {

    private Long userId;

    private String firstName;

    private String lastName;

    private String username;

    private List<Permission> permissions;
}
