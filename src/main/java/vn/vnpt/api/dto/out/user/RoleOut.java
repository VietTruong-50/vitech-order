package vn.vnpt.api.dto.out.user;

import lombok.Data;
import vn.vnpt.api.repository.helper.Col;

@Data
public class RoleOut {
    @Col(value = "name")
    private String name;
}
