package com.flab.openmarket.repository.typehandler;

import com.flab.openmarket.model.MemberRole;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@MappedTypes(MemberRole.class)
public class MemberRoleHandler implements TypeHandler<MemberRole> {
    @Override
    public void setParameter(PreparedStatement ps, int i, MemberRole parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public MemberRole getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return this.getRole(value);
    }

    @Override
    public MemberRole getResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return this.getRole(value);
    }

    @Override
    public MemberRole getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return this.getRole(value);
    }

    private MemberRole getRole(String value) {
        return Arrays.stream(MemberRole.values())
                .filter(memberRole -> memberRole.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
