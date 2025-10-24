package com.practic.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private String TABLE = "member";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    RowMapper<MemberEntity> memberEntityRowMapper = (rs, rowNum) -> {
        String status = rs.getString("status");
        java.sql.Timestamp createdTs = rs.getTimestamp("created_at");
        java.sql.Timestamp updatedTs = rs.getTimestamp("updated_at");

        return MemberEntity.builder()
                .memberId(rs.getLong("member_id"))
                .nickName(rs.getString("nick_name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .phoneNumber(rs.getString("phone_number"))
                .status(status != null ? status : "ACTIVE")
                .createdAt(createdTs != null ? createdTs.toLocalDateTime() : LocalDateTime.now())
                .updatedAt(updatedTs != null ? updatedTs.toLocalDateTime() : LocalDateTime.now())
                .build();
    };


    public Optional<MemberEntity> findByMemberId(Long memberId) {
        final String query = String.format("SELECT * FROM %s WHERE member_id = :memberId;", TABLE);
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        // Hack, 25.10.23 : try~catch에 따라 Optional로 분기처리하는 코드라 이상해보인다.
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            query,
                            mapSqlParameterSource,
                            memberEntityRowMapper
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<MemberEntity> findByMemberIds(List<Integer> memberIds) {
        final String query = String.format("SELECT * FROM %s WHERE member_id in :memberIds;", TABLE);
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("memberIds", memberIds);


        return namedParameterJdbcTemplate.query(
                query,
                mapSqlParameterSource,
                memberEntityRowMapper
        );

    }


}
