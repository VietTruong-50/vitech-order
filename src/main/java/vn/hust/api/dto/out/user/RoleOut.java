package vn.hust.api.dto.out.user;

import lombok.Data;
import vn.hust.api.repository.helper.Col;

@Data
public class RoleOut {
    @Col(value = "name")
    private String name;
}
