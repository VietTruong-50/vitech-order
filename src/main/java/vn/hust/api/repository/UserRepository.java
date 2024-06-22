package vn.hust.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.hust.api.model.User;
import vn.hust.api.repository.helper.ProcedureCallerV3;
import vn.hust.api.repository.helper.ProcedureParameter;
import vn.hust.common.Common;
import vn.hust.common.constant.DatabaseStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final ProcedureCallerV3 procedureCallerV3;

    public void saveUser(User user) {
        Map<String, Object> outputs = procedureCallerV3.callNoRefCursor("customer_create_new",
                List.of(
                        ProcedureParameter.inputParam("prs_username", String.class, user.getUsername()),
                        ProcedureParameter.inputParam("prs_first_name", String.class, user.getFirstName()),
                        ProcedureParameter.inputParam("prs_last_name", String.class, user.getLastName()),
                        ProcedureParameter.inputParam("prs_email", String.class, user.getEmail()),
                        ProcedureParameter.inputParam("prs_password", String.class, user.getPassword()),
                        ProcedureParameter.outputParam("out_result", String.class)
                )
        );
        String result = (String) outputs.get("out_result");
        if (!DatabaseStatus.Success.equals(result)) throw new RuntimeException("customer_create_new failed!");

    }

    public Optional<User> findByEmail(String email) {
        Map<String, Object> outputs = procedureCallerV3.callOneRefCursor("customer_detail_by_email",
                List.of(
                        ProcedureParameter.inputParam("prs_email", String.class, email),
                        ProcedureParameter.outputParam("out_result", String.class),
                        ProcedureParameter.refCursorParam("out_cur")
                ), User.class
        );

        List<User> outList = (List<User>) outputs.get("out_cur");

        return !Common.isNullOrEmpty(outList) ? Optional.of(outList.get(0)) : Optional.empty();
    }
}
