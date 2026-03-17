# iop-iw-api 분석

## 역할

`iop-iw-api`는 포털의 핵심 업무 백엔드다. UI가 호출하는 대부분의 업무 API가 이 프로젝트에 들어 있다.

## 기술 스택

- Spring Boot 2.7.18
- Java 8
- MyBatis 중심
- 일부 JPA 의존성 존재
- Spring Security
- JWT
- Keycloak 연동
- Spring Batch / Scheduler
- Kafka
- AWS S3
- Swagger / springdoc

## 빌드

### 확인된 빌드 파일
- `pom.xml`
- `build.gradle`

### 해석
- 실제 운영 기준은 Maven일 가능성이 높다.
- 다만 Gradle 설정도 살아 있어서 빌드 체계가 혼재된 상태다.

## 진입점

- `src/main/java/kr/go/iop/iw/MainApplication.java`

설정 특징:
- `@EnableScheduling`
- `@EnableAsync`
- `@EnableTransactionManagement`
- 배치 자동설정 제외
- Mapper 스캔 활성화

## 규모

- Java 소스 약 `488`개
- MyBatis XML 약 `50`개
- 추출 엔드포인트 약 `371`개

## 주요 패키지

- `auth`
- `document`
- `attachment`
- `assignment`
- `admin`
- `user`
- `uscm`
- `rag`
- `schedule`
- `mail`
- `share`
- `voc`
- `brm`
- `ihb`

## 설정 구조

핵심 설정:
- `application.yml`
- `application-dev.yml`
- `.env` 계열

주요 환경값:
- `PORT`
- `KEYCLOAK_*`
- `JWT_SECRET`
- `INTERNAL_API_KEY`
- `EXTERNAL_API_KEY`
- `TUS_SERVER_URL`
- `RAG_API_URL`
- `S3_*`
- `KAFKA_*`
- `APIGW_ENDPOINT`
- `CATALOG_ENDPOINT`

## 데이터소스

### 메인 DB
- MariaDB
- 업무 데이터 저장소

### 공통 DB
- PostgreSQL
- 공통 사용자/조직 동기화 계열로 보임

### 구현 파일
- `schedule/config/MultiDataSourceConfig.java`

## 인증/보안

핵심 파일:
- `common/config/security/SecurityConfig.java`
- `common/config/jwt/*`
- `common/config/keycloak/*`

확인 사항:
- Stateless API
- JWT 인증 필터 사용
- Keycloak 토큰 검증 사용
- 외부 API Key 인증 필터 사용
- Swagger 공개
- 대부분의 업무 경로는 인증 필요

## 외부 연동

- Keycloak
- TUS 업로드 서버
- MariaDB / PostgreSQL
- Kafka
- S3
- 외부 RAG API
- API Gateway
- Works 메일/메시지
- Catalog / Cache

## 프로젝트 간 연동성

### UI와의 관계
- UI의 `VITE_API_BASE_URL` 대상
- 매니페스트에서 `/iw/api`가 이 서비스로 라우팅됨

### TUS와의 관계
- TUS 업로드 서버의 훅 콜백 대상
- 내부 경로 `/internal/tusd`로 업로드 이벤트를 받음

### Keycloak와의 관계
- 토큰 검증, 로그인 연동, 권한 해석
- Keycloak 확장 저장소와 간접 연결됨

## 관찰 포인트

- 저장소 안에 `build`, `target`, `bin`이 함께 있어 산출물 흔적이 섞여 있다.
- Gradle/Maven 의존성이 완전히 같지 않다.
- 대형 모놀리식 구조에 가깝다.
- 테스트 소스는 현재 보이지 않는다.

## API 목록

상세 API 목록은 별도 파일:
- `IW_PROJECT_iop-iw-api_7.md`: 소스 재추출 기준 API 전수 목록

## 파생 문서

- `IW_PROJECT_iop-iw-api_2.md`: UI 실사용 API 기준 컨트롤러/서비스/DAO와 대표 메서드 매핑 통합
- `IW_PROJECT_iop-iw-api_3.md`: `assignment/document` DTO, 변경 컬럼, XML 매퍼, 핵심 테이블 영향
- `IW_PROJECT_iop-iw-api_4.md`: `admin/auth` 구조와 화면 기준 백엔드 우선 진입점
- `IW_PROJECT_iop-iw-api_5.md`: 패키지 책임도, 인증 축, 업로드 후처리, 구조 복잡도
- `IW_PROJECT_iop-iw-api_6.md`: 카테고리별 API 부록
- `IW_PROJECT_iop-iw-api_7.md`: 소스 재추출 기준 API 전수 목록
- `IW_PROJECT_iop-iw-api_8.md`: 기능영역별 API 재분류와 엔드포인트 기능 요약
- `IW_PROJECT_iop-iw-api_9.md`: UI 화면 기준 API 역매핑과 디버깅 시작점
