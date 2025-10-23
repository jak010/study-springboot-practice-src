package com.practic.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;


@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private String TABLE = "member";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    RowMapper<MemberEntity> memberEntityRowMapper = (ResultSet rs, int rowNum) -> MemberEntity.builder()
            .memberId(rs.getLong("member_id"))
            .nickName(rs.getString("nick_name")) // 수정
            .email(rs.getString("email"))      // 컬럼이 있으면 사용, 없으면 기본값 가능
            .password(rs.getString("password")) // 컬럼이 있으면 사용
            .phoneNumber(rs.getString("phone_number"))
            .status(rs.getString("status") != null ? rs.getString("status") : "ACTIVE")
            .createdAt(rs.getTimestamp("created_at") != null ?
                    rs.getTimestamp("created_at").toLocalDateTime() : LocalDateTime.now())
            .updatedAt(rs.getTimestamp("updated_at") != null ?
                    rs.getTimestamp("updated_at").toLocalDateTime() : LocalDateTime.now())
            .build();


//    public MemberRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
//        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
//    }


    public MemberEntity findByMemberId(Long memberId) {
        String query = String.format("SELECT * FROM %s WHERE member_id = :memberId;", TABLE);
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        return namedParameterJdbcTemplate.queryForObject(
                query,
                mapSqlParameterSource,
                memberEntityRowMapper
        );
    }

}
