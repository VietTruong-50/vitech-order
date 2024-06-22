package vn.hust.api.repository.helper;

import jakarta.persistence.ParameterMode;
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

@Repository
@Transactional
public class ProcedureCallerV2 {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * support no ref cursor
     *
     * @param procedureName
     * @param procedureParameters
     * @return
     */
    @Transactional
    public Map<String, Object> callNoRefCursor(String procedureName, List<ProcedureParameter> procedureParameters) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName(procedureName);

            List<SqlParameter> declareParameters = new ArrayList<>();
            Map<String, Object> inValues = new HashMap<>();

            for (ProcedureParameter parameter : procedureParameters) {

                if (parameter.getParameterMode().equals(ParameterMode.IN)) {
                    if (parameter.getType().equals(String.class)) {
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.VARCHAR));
                    } else if (parameter.getType().getSuperclass().equals(Number.class)){
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.NUMERIC));
                    }else if (parameter.getType().equals(Date.class)){
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.TIMESTAMP));
                    } else {
                        throw new IllegalStateException("Not support type: " + parameter.getType());
                    }
                    inValues.put(parameter.getName(), parameter.getValue());
                }

                if (parameter.getParameterMode().equals(ParameterMode.OUT)) {
                    if (parameter.getType().equals(String.class)) {
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.VARCHAR));
                    } else if (parameter.getType().getSuperclass().equals(Number.class)){
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.NUMERIC));
                    }else if (parameter.getType().equals(Date.class)){
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.TIMESTAMP));
                    } else {
                        throw new IllegalStateException("Not support type: " + parameter.getType());
                    }
                }

                if (parameter.getParameterMode().equals(ParameterMode.REF_CURSOR)) {
                    throw new IllegalArgumentException("not support parameter ref_cursor");
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


            return outputs;

        } finally {

        }
    }

    /**
     * support only one ref cursor
     *
     * @param procedureName
     * @param procedureParameters
     * @param resultParser
     * @return
     */
    @Transactional
    public Map<String, Object> callOneRefCursor(String procedureName, List<ProcedureParameter> procedureParameters,
                                                ResultSetParser<Object> resultParser) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName(procedureName);

            List<SqlParameter> declareParameters = new ArrayList<>();
            Map<String, Object> inValues = new HashMap<>();


            for (ProcedureParameter parameter : procedureParameters) {

                if (parameter.getParameterMode().equals(ParameterMode.IN)) {
                    if (parameter.getType().equals(String.class)) {
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.VARCHAR));
                    }  else if (parameter.getType().getSuperclass().equals(Number.class)){
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.NUMERIC));
                    }else if (parameter.getType().equals(Date.class)){
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.TIMESTAMP));
                    } else {
                        throw new IllegalStateException("Not support type: " + parameter.getType());
                    }
                    inValues.put(parameter.getName(), parameter.getValue());
                }

                if (parameter.getParameterMode().equals(ParameterMode.OUT)) {
                    if (parameter.getType().equals(String.class)) {
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.VARCHAR));
                    } else if (parameter.getType().getSuperclass().equals(Number.class)){
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.NUMERIC));
                    }else if (parameter.getType().equals(Date.class)){
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.TIMESTAMP));
                    } else {
                        throw new IllegalStateException("Not support type: " + parameter.getType());
                    }
                }

                if (parameter.getParameterMode().equals(ParameterMode.REF_CURSOR)) {
                    declareParameters.add(new SqlOutParameter(parameter.getName(), Types.REF_CURSOR, (RowMapper<?>) (rs, rowNum) -> resultParser.parseRs(rs)));
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


            return outputs;
        } finally {

        }
    }

    /**
     * support multiple ref cursor
     *
     * @param procedureName
     * @param procedureParameters
     * @param resultParsers
     * @return
     */
    @Transactional
    public Map<String, Object> callMultiRefCursor(String procedureName, List<ProcedureParameter> procedureParameters,
                                                  Map<String, ResultSetParser<Object>> resultParsers) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        List<ProcedureParameter> refCursorParameters = new ArrayList<>();

        try {
            simpleJdbcCall.withProcedureName(procedureName);

            List<SqlParameter> declareParameters = new ArrayList<>();
            Map<String, Object> inValues = new HashMap<>();

            for (ProcedureParameter parameter : procedureParameters) {

                if (parameter.getParameterMode().equals(ParameterMode.IN)) {
                    if (parameter.getType().equals(String.class)) {
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.VARCHAR));
                    } else if (parameter.getType().getSuperclass().equals(Number.class)){
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.NUMERIC));
                    }else if (parameter.getType().equals(Date.class)){
                        declareParameters.add(new SqlParameter(parameter.getName(), Types.TIMESTAMP));
                    } else {
                        throw new IllegalStateException("Not support type: " + parameter.getType());
                    }
                    inValues.put(parameter.getName(), parameter.getValue());
                }

                if (parameter.getParameterMode().equals(ParameterMode.OUT)) {
                    if (parameter.getType().equals(String.class)) {
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.VARCHAR));
                    } else if (parameter.getType().getSuperclass().equals(Number.class)){
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.NUMERIC));
                    }else if (parameter.getType().equals(Date.class)){
                        declareParameters.add(new SqlOutParameter(parameter.getName(), Types.TIMESTAMP));
                    } else {
                        throw new IllegalStateException("Not support type: " + parameter.getType());
                    }
                }

                if (parameter.getParameterMode().equals(ParameterMode.REF_CURSOR)) {
                    refCursorParameters.add(parameter);
                    declareParameters.add(new SqlOutParameter(parameter.getName(), Types.REF_CURSOR, (RowMapper<?>) (rs, rowNum) -> resultParsers.get(parameter.getName()).parseRs(rs)));
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

            for (ProcedureParameter oParameter : refCursorParameters) {
                ResultSetParser<Object> resultParser = resultParsers.get(oParameter.getName());
                if (resultParser == null) {
                    throw new RuntimeException(String.format("could not find result parser for param: %s",
                            oParameter.getName()));
                }
            }

            return outputs;
        } finally {

        }
    }
}
