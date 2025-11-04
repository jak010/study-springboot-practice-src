package com.practic.demo.member.impl;

import com.practic.demo.member.MemberEntity;
import com.practic.demo.member.MemberRepository;
import com.practic.demo.member.MemberStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private String TABLE = "member";


    @Autowired
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    final private DataSource dataSource;

    @Override
    public MemberEntity save(MemberEntity memberEntity) {
        // Java Text Block(Java 15이상)
//        final String insertQuery = """
//                        INSERT INTO %s (
//                          nick_name,
//                          email,
//                          password,
//                          phone_number,
//                          status
//                        )
//                        VALUE (:nickName, :email, :password, :phone_number, :status);
//                """;
//        final String query = String.format(insertQuery, TABLE);
//        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//        namedParameterJdbcTemplate.update(query, params, keyHolder);
//        long key = Objects.requireNonNull(keyHolder.getKey()).longValue();

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE)
            .usingGeneratedKeyColumns("id");
        final MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("nickName", memberEntity.getNickName())
            .addValue("email", memberEntity.getEmail())
            .addValue("password", memberEntity.getPassword())
            .addValue("phone_number", memberEntity.getPhoneNumber())
            .addValue("status", memberEntity.getStatus())
            .addValue("created_at", LocalDateTime.now())
            .addValue("updated_at", LocalDateTime.now());

        Number key = simpleJdbcInsert.executeAndReturnKey(params);

        memberEntity.setMemberId(key.longValue());

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
    public List<MemberEntity> findMemberByIds(List<Integer> memberIds) {
        final String query = String.format("SELECT * FROM %s WHERE member_id in (:memberIds);",
            TABLE);
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("memberIds", memberIds);

        return namedParameterJdbcTemplate.query(
            query,
            mapSqlParameterSource,
            new BeanPropertyRowMapper<>(MemberEntity.class)
        );

    }

    @Override
    public Optional<MemberEntity> findMemberByNickName(String nickName) {

        final String query = String.format("SELECT * FROM %s WHERE nickname = :nickName;");
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nickName", nickName);

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
    public Optional<MemberEntity> duplicateCheck(String email, String nickName) {
        final String query = String.format(
            "SELECT * FROM %s WHERE email = :email AND nick_name = :nickName;", TABLE);
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);
        params.addValue("nickName", nickName);

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

    @Override
    public MemberEntity updateMemberPassword(MemberEntity memberEntity) {
        final String query = String.format("""
            UPDATE %s SET
              password = :password,
              updated_at = :updated_at
            WHERE member_id = :member_id;
            """, TABLE);
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("password", memberEntity.getPassword());
        params.addValue("updated_at", LocalDateTime.now());
        params.addValue("member_id", memberEntity.getMemberId());

        namedParameterJdbcTemplate.update(query, params);

        return memberEntity;

    }

    @Override
    public MemberEntity updateMemberInfo(MemberEntity memberEntity) {
        final String query = String.format("""
            UPDATE %s SET
              nick_name = :nickName,
              email = :email,
              phone_number = :phoneNumber,
              updated_at = :updatedAt
            WHERE member_id = :memberId;
            """, TABLE);
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nickName", memberEntity.getNickName());
        params.addValue("email", memberEntity.getEmail());
        params.addValue("phoneNumber", memberEntity.getPhoneNumber());
        params.addValue("updatedAt", LocalDateTime.now());
        params.addValue("memberId", memberEntity.getMemberId());

        namedParameterJdbcTemplate.update(query, params);

        return memberEntity;
    }

    @Override
    public Page<MemberEntity> findMembersByStatus(MemberStatus status, Pageable pageable) {
        return null;
    }

    @Override
    public boolean deleteMemberById(Long memberId) {
        final String query = String.format("""
            UPDATE %s SET
            status = :status,
            updated_at = :updatedAt
            WHERE member_id = :memberId;
            """, TABLE);
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status", "BLOCKED");
        params.addValue("memberId", memberId);
        params.addValue("updatedAt", LocalDateTime.now());

        int result = namedParameterJdbcTemplate.update(query, params);
        return result > 1;

    }

    @Override
    public List<MemberEntity> findMemberByCreatedDate(LocalDate start, LocalDate end) {
        final String query = String.format("""
            SELECT * 
            FROM %s
            WHERE created_at BETWEEN :start and :end;                
            """, TABLE);
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("end", end);

        return namedParameterJdbcTemplate.query(
            query,
            params,
            new BeanPropertyRowMapper<>(MemberEntity.class)
        );
    }
}
