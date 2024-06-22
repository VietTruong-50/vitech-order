package vn.hust.api.repository.helper;

import jakarta.persistence.ParameterMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.*;

@Slf4j
@Repository
@Transactional
public class ProcedureCaller {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public List<Object> call(String procedureName, List<ProcedureParameter> procedureParameters) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName(procedureName);

            List<SqlParameter> declareParameters = new ArrayList<>();
            Map<String, Object> inValues = new HashMap<>();

            ProcedureParameter refCursorParameter = null;
            List<ProcedureParameter> outParameters = new ArrayList<>();

            for (ProcedureParameter parameter : procedureParameters) {


                if (parameter.getParameterMode().equals(ParameterMode.IN)) {
                    if (parameter.getType().equals(String.class)) {
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.VARCHAR));
                    } else if (parameter.getType().getSuperclass().equals(Number.class)){
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.NUMERIC));
                    } else if (parameter.getType().equals(Date.class)){
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.TIMESTAMP));
                    } else {
                        throw new IllegalStateException("Not support type: " + parameter.getType());
                    }
                    inValues.put(parameter.getName(), parameter.getValue());
                }

                if (parameter.getParameterMode().equals(ParameterMode.REF_CURSOR)) {
                    refCursorParameter = parameter;
                    declareParameters.add(new SqlOutParameter(parameter.getName(), Types.REF_CURSOR, (RowMapper<?>) (rs, rowNum) -> ObjectResultParser.parse(rs)));
                }

                if (parameter.getParameterMode().equals(ParameterMode.OUT)) {
                    outParameters.add(parameter);
                    if (parameter.getType().equals(String.class)) {
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.VARCHAR));
                    } else if (parameter.getType().getSuperclass().equals(Number.class)){
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.NUMERIC));
                    } else if (parameter.getType().equals(Date.class)){
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.TIMESTAMP));
                    } else {
                        throw new IllegalStateException("Not support type: " + parameter.getType());
                    }
                }
            }
            simpleJdbcCall.declareParameters(declareParameters.toArray(new SqlParameter[declareParameters.size()]));
            SqlParameterSource in = new MapSqlParameterSource().addValues(inValues);


            Map<String, Object> outputs;
            try {
                outputs = simpleJdbcCall.execute(in);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException("Error when call procedure: " + procedureName, e);
            }
            List<Object> resultList = new ArrayList<>();

            if (refCursorParameter != null) {
                try {
                    resultList = (List<Object>) outputs.get(refCursorParameter.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            List<Object> finalRes = new ArrayList<>();

            for (ProcedureParameter oParameter : outParameters) {
                finalRes.add(outputs.get(oParameter.getName()));
            }
            finalRes.add(resultList);

            return finalRes;
        } finally {

        }
    }

}
