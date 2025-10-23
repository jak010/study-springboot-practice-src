CREATE TABLE demo.member (
    member_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '회원 고유 ID',
    nick_name VARCHAR(100) NOT NULL COMMENT '닉네임',
    email VARCHAR(255) UNIQUE COMMENT '이메일 주소',
    password VARCHAR(255) COMMENT '비밀번호 (암호화 저장)',
    phone_number VARCHAR(20) COMMENT '전화번호',
    status ENUM('ACTIVE', 'INACTIVE', 'BLOCKED') NOT NULL DEFAULT 'ACTIVE' COMMENT '회원 상태',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (member_id),
    INDEX idx_email (email),
    INDEX idx_status (status)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '회원 정보 테이블';


INSERT INTO demo.member ( nick_name, email, password, phone_number, status ) VALUES
('jako', 'jako@example.com', 'hashed_pw_123', '010-1234-5678', 'ACTIVE'),
('hana', 'hana@example.com', 'hashed_pw_456', '010-9876-5432', 'ACTIVE'),
('minsu', 'minsu@example.com', 'hashed_pw_789', '010-1111-2222', 'INACTIVE'),
('sora', 'sora@example.com', 'hashed_pw_abc', '010-3333-4444', 'BLOCKED');
