# Demo Project

## 📖 Overview
이 프로젝트는 Spring Boot 기반의 회원 관리 RESTful API 서버입니다. 회원 정보를 조회, 등록, 수정하는 기능을 제공하며, 데이터베이스는 MySQL을 사용합니다.

## ⚙️ Tech Stack
- **언어**: Java 21
- **프레임워크**: Spring Boot 3.5.6
- **빌드 도구**: Gradle
- **데이터베이스**: MySQL 5.7 (Docker Compose를 통해 실행)
- **API 문서**: OpenAPI 3.0, Swagger UI
- **로깅**: log4jdbc-log4j2-jdbc4 (SQL 쿼리 로깅)
- **유틸리티**: Lombok
- **테스트**: JUnit5

## 📁 Project Structure
```
.
├── build.gradle                # Gradle 빌드 설정 및 의존성 관리
├── docker/
│   ├── docker-compose.yml      # Docker Compose 설정 (MySQL DB)
│   └── sqls/
│       └── ddl.sql             # MySQL 데이터베이스 DDL 및 초기 데이터
├── docs/                       # 프로젝트 관련 문서
│   └── 1.1_member.md           # 회원 관련 요구사항 문서
└── src/
    ├── main/
    │   ├── java/               # 백엔드 Java 소스 코드
    │   │   └── com/practic/demo/
    │   │       ├── DemoApplication.java    # Spring Boot 애플리케이션 진입점
    │   │       ├── member/                 # 회원 관련 도메인 코드 (Controller, Service, Repository, Entity 등)
    │   │       └── settings/               # 공통 설정 및 예외 처리
    │   └── resources/          # 설정 파일 및 정적 리소스
    │       ├── application.yaml            # Spring Boot 애플리케이션 설정 (DB 연결, 포트 등)
    │       ├── log4jdbc.log4j2.properties  # log4jdbc 설정
    │       └── logback.xml                 # Logback 로깅 설정
    └── test/                   # 테스트 코드
        └── java/
            └── com/practic/demo/
                └── DemoApplicationTests.java # Spring Boot 테스트
```

## 🚀 주요 기능 (Core Features)
이 프로젝트는 다음과 같은 회원 관리 기능을 제공합니다:

-   **회원 조회**: 특정 `memberId`로 회원 정보를 조회하거나, `memberIds` 목록으로 여러 회원 정보를 조회합니다.
    -   `GET /member/{memberId}`
    -   `GET /member?memberIds={id1},{id2}`
-   **회원 등록**: 새로운 회원 정보를 등록합니다.
    -   `POST /member`
-   **회원 상태 업데이트**: 특정 회원의 상태(ACTIVE, INACTIVE, BLOCKED)를 업데이트합니다.
    -   `PUT /member/{memberId}/status`

## 🛠️ 환경 설정 (Configuration & Setup)

### 1. 프로젝트 클론
```bash
git clone https://github.com/jak010/study-springboot-practice-src.git
cd study-springboot-practice-src
```

### 2. 데이터베이스 설정 (Docker Compose)
프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 MySQL 데이터베이스를 Docker 컨테이너로 실행합니다.
```bash
docker-compose -f docker/docker-compose.yml up -d
```
MySQL은 `19999` 포트로 노출되며, `root` 비밀번호는 `1234`, 데이터베이스 이름은 `demo`입니다. `docker/sqls/ddl.sql` 파일에 정의된 스키마와 초기 데이터가 자동으로 적용됩니다.

### 3. 애플리케이션 빌드 및 실행
Gradle을 사용하여 프로젝트를 빌드하고 실행합니다.
```bash
./gradlew build
./gradlew bootRun
```
애플리케이션은 기본적으로 `8080` 포트에서 실행됩니다.

## 📦 의존성 목록 (Dependencies)
`build.gradle` 파일에 정의된 주요 의존성은 다음과 같습니다:

-   `org.springdoc:springdoc-openapi-starter-webmvc-ui`: OpenAPI 3.0 및 Swagger UI를 위한 의존성. API 문서를 자동으로 생성하고 시각화합니다.
-   `org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4`: SQL 쿼리 로깅을 위한 라이브러리. 개발 중 데이터베이스 상호작용을 쉽게 디버깅할 수 있도록 도와줍니다.
-   `org.springframework.boot:spring-boot-starter-data-jdbc`: Spring Data JDBC를 사용하여 데이터베이스와 상호작용합니다.
-   `org.springframework.boot:spring-boot-starter-web`: RESTful API를 구축하기 위한 Spring Web 모듈입니다.
-   `org.springframework.boot:spring-boot-starter-validation`: 데이터 유효성 검사를 위한 Spring Boot Starter입니다.
-   `org.projectlombok:lombok`: Getter, Setter, 생성자 등을 자동으로 생성하여 boilerplate 코드를 줄여줍니다.
-   `com.mysql:mysql-connector-j`: MySQL 데이터베이스 드라이버입니다.

## 🏗️ 빌드 및 배포 (Build & Deploy)
-   **빌드**: Gradle을 사용하여 `build` 태스크를 실행하면 프로젝트가 빌드됩니다.
    ```bash
    ./gradlew build
    ```
-   **배포**: 현재는 로컬 환경에서 Docker Compose를 통해 MySQL을 실행하고 Spring Boot 애플리케이션을 직접 실행하는 방식입니다. 향후 Docker 이미지 빌드 및 배포 자동화 (예: GitHub Actions)를 고려할 수 있습니다.

## 🧪 테스트 (Testing)
-   **단위/통합 테스트**: `src/test/java` 디렉토리에 JUnit5 기반의 테스트 코드가 포함되어 있습니다.
-   **테스트 실행**: 다음 명령어를 사용하여 모든 테스트를 실행할 수 있습니다.
    ```bash
    ./gradlew test
    ```

## ➕ 추가 참고 (Etc.)
-   **API 문서 (Swagger UI)**: 애플리케이션 실행 후 다음 URL에서 API 문서를 확인할 수 있습니다.
    -   `http://localhost:8080/swagger-ui/index.html`
-   **GitHub Repository**: [https://github.com/jak010/study-springboot-practice-src](https://github.com/jak010/study-springboot-practice-src)
