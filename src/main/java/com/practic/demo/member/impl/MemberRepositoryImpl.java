package com.practic.demo.member.impl;

import com.practic.demo.member.MemberEntity;
import com.practic.demo.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private String TABLE = "member";

    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public MemberEntity save(MemberEntity memberEntity) {
        // Java Text Block(Java 15이상)
        final String insertQuery = """
                        INSERT INTO %s (
                          nick_name,
                          email,
                          password,
                          phone_number,
                          status
                        )
                        VALUE (:nickName, :email, :password, :phone_number, :status);
                """;

        final String query = String.format(insertQuery, TABLE);
        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nickName", memberEntity.getNickName())
                .addValue("email", memberEntity.getEmail())
                .addValue("password", memberEntity.getPassword())
                .addValue("phone_number", memberEntity.getPhoneNumber())
                .addValue("status", memberEntity.getStatus());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(query, params, keyHolder);

        long key = Objects.requireNonNull(keyHolder.getKey()).longValue();
        memberEntity.setMemberId(key);


        return memberEntity;
    }

    @Override
    public Optional<MemberEntity> findMemberByEmail(String email) {
        final String query = String.format("SELECT * FROM %s WHERE email = :email;", TABLE);
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);

        // Hack, 25.10.23 : try~catch에 따라 Optional로 분기처리하는 코드라 이상해보인다.
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            query,
                            params,
                            new BeanPropertyRowMapper<>(MemberEntity.class)
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MemberEntity> findMemberById(Long memberId) {
        final String query = String.format("SELECT * FROM %s WHERE member_id = :memberId;", TABLE);
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        // Hack, 25.10.23 : try~catch에 따라 Optional로 분기처리하는 코드라 이상해보인다.
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            query,
                            mapSqlParameterSource,
                            new BeanPropertyRowMapper<>(MemberEntity.class)
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<MemberEntity> findByMemberIds(List<Integer> memberIds) {
        final String query = String.format("SELECT * FROM %s WHERE member_id in (:memberIds);", TABLE);
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("memberIds", memberIds);


        return namedParameterJdbcTemplate.query(
                query,
                mapSqlParameterSource,
                new BeanPropertyRowMapper<>(MemberEntity.class)
        );

    }

    @Override
    public MemberEntity updateMemberStatus(MemberEntity memberEntity, String status) {

        final String query = String.format("""
                UPDATE %s SET
                status = :status,
                updated_at = :updatedAt
                WHERE member_id = :memberId;
                """, TABLE);
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status", status);
        params.addValue("memberId", memberEntity.getMemberId());
        params.addValue("updatedAt", LocalDateTime.now());

        int result = namedParameterJdbcTemplate.update(query, params);

        if (result < 1) {
            return memberEntity;
        }

        memberEntity.setStatus(status);
        return memberEntity;
    }


}
