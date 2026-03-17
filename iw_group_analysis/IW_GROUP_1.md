# iw_group 통합 분석 요약

## 분석 대상

- `iop-iw-api`
- `iop-iw-ui`
- `iop-iw-tus`
- `iop-iw-gpki`
- `iop-iw-manifest`
- `keycloak`
- `user-storage-jpa-master`

## 전체 구조 한눈에 보기

이 그룹은 `포털 UI`, `업무 API`, `대용량 업로드 서버`, `GPKI 관련 구성`, `Keycloak 확장`, `배포 매니페스트`로 구성된 포털 묶음이다.

가장 자연스러운 구조는 다음과 같다.

1. 사용자는 `iop-iw-ui`에 접속한다.
2. UI는 `Keycloak`로 로그인하고 토큰을 받는다.
3. UI는 업무 데이터 조회/수정 시 `iop-iw-api`를 호출한다.
4. 파일 업로드는 `iop-iw-tus`로 보내고, 업로드 완료 이벤트는 내부 훅으로 다시 `iop-iw-api`에 전달된다.
5. `iop-iw-api`는 `Keycloak`, `MariaDB`, `PostgreSQL`, `Kafka`, `S3`, 외부 RAG API`와 연동한다.
6. 실제 쿠버네티스 배포 라우팅과 환경값은 `iop-iw-manifest`가 담당한다.
7. `keycloak`, `user-storage-jpa-master`는 Keycloak 확장 Provider 저장소이며, `iop-iw-gpki`는 GPKI 관련 별도 웹 애플리케이션/패키지로 보인다.

## 프로젝트별 역할 요약

### iop-iw-ui
- React + TypeScript + Vite 기반 프론트엔드
- `/iw` 경로 기준 포털 화면 제공
- API, TUS, Keycloak, 외부 원격 앱 URL을 환경변수로 받음

### iop-iw-api
- Spring Boot 기반 핵심 업무 API 서버
- 문서, 첨부, 사용자, 권한, 관리자, 배치, RAG, 메일 등 대부분의 백엔드 기능 담당
- MariaDB + PostgreSQL 다중 데이터소스 사용

### iop-iw-tus
- TUS 프로토콜 기반 대용량 업로드 서버
- 업로드 완료 이벤트를 API 서버의 `/internal/tusd`로 전달

### iop-iw-gpki
- eGovFrame WAR 형태의 GPKI 관련 웹 애플리케이션 패키지
- 소스는 현재 거의 없고 의존성/패키징 중심

### iop-iw-manifest
- Helm/ArgoCD 기반 배포 저장소
- UI, API, TUS, GPKI, 별도 스케줄러(`iop-iw-sch`) 배포 값 포함

### keycloak
- Keycloak 26용 커스텀 Provider 저장소
- JDBC 사용자 저장소, GPKI/MetaMask 인증기, 세션 제한 이벤트 리스너 포함
- Keycloak 이해가 없는 개발자를 위한 입문 정리 문서 별도 작성
- 인증 시퀀스, 운영 체크리스트, 로그인 장애 대응 문서까지 확장 완료
- 실제 로그인 코드 추적 순서 문서 추가

### user-storage-jpa-master
- Keycloak 확장 실험/이전 버전 저장소로 보임
- 현재 `keycloak` 프로젝트와 역할이 많이 겹침

## 확인된 주요 연동성

### UI → API
- `iop-iw-ui/.env`, `.env.production`, `vite.config.ts`에서 `VITE_API_BASE_URL` 사용
- 매니페스트 `dev/iop-iw-ui/values/*`에서 `/iw/api/`를 `iop-iw-api-*-svc`로 라우팅

### UI → TUS
- UI 환경변수에 `VITE_UPLOAD_BASE_URL`, `VITE_TUS_API_KEY` 존재
- 매니페스트에서 `/iw/files`를 `iop-iw-tus-*-svc`로 라우팅

### UI → Keycloak
- UI 환경변수에 `VITE_KEYCLOAK_BASE_URL`, `VITE_KEYCLOAK_REALM`, `VITE_KEYCLOAK_CLIENT_ID` 존재
- 즉, 로그인 주체는 Keycloak

### API → Keycloak
- `SecurityConfig`, `KeycloakTokenValidator`, Keycloak 관련 서비스/설정 존재
- API는 Keycloak 토큰을 검증하고 일부 인증 흐름을 직접 다룸

### API → TUS
- API 설정에 `TUS_SERVER_URL` 존재
- TUS Dockerfile은 `TUSD_HOOKS_HTTP`를 API 내부 경로 `/internal/tusd`로 설정

### API → DB
- 메인 DB는 MariaDB
- 공통 DB는 PostgreSQL
- `MultiDataSourceConfig`로 별도 세션팩토리/트랜잭션 분리

### API → 외부 시스템
- Kafka
- S3
- RAG API
- API Gateway / Catalog / Cache
- Keycloak / OAuth2
- 메일/메시지 발송 경로

### Keycloak 확장 → API DB
- `user-storage-jpa-master/docker-compose.yml`에서 사용자 저장소 DB가 `usiw` MariaDB로 설정됨
- 즉, Keycloak 확장이 API와 같은 업무 DB 사용자 정보를 참조할 가능성이 높음

### GPKI 관련 연결
- `keycloak`과 `user-storage-jpa-master` 둘 다 `GpkiAuthenticator` 계열 클래스를 가짐
- `iop-iw-gpki`는 GPKI 전용 웹앱 패키지
- 따라서 GPKI는 단일 저장소가 아니라 `웹앱 + Keycloak 인증기` 형태로 분산돼 있을 가능성이 높음

## 빌드 관점 정리

### Maven이 주 기준으로 보이는 이유
- Java 계열 프로젝트 대부분에 `pom.xml`이 존재
- `keycloak`, `user-storage-jpa-master`, `iop-iw-gpki`는 Maven 전용에 가까움
- `iop-iw-api`만 `pom.xml`과 `build.gradle`이 동시에 존재
- 사용자 진술과 저장소 구성상 현재 운영 기준은 Maven일 가능성이 높음

### 예외
- `iop-iw-api`는 Gradle도 살아 있어 빌드 기준이 혼재됨
- `iop-iw-ui`는 Node/Vite 기반이라 Maven 대상이 아님
- `iop-iw-manifest`는 빌드보다 배포 값 저장소 역할

## 주의점

### 1. API 저장소 빌드 체계 혼재
- `pom.xml`과 `build.gradle`이 공존
- 의존성도 완전히 일치하지 않음

### 2. Keycloak 확장 저장소 중복
- `keycloak`과 `user-storage-jpa-master`가 역할이 많이 겹침
- 어떤 저장소가 현재 운영 기준인지 확인 필요

### 3. 배포 저장소와 실제 워크스페이스 불일치
- `iop-iw-manifest`에는 `iop-iw-sch`가 있으나 현재 `iw_group`에는 프로젝트가 없음
- 별도 저장소이거나 누락된 워크스페이스일 가능성 있음

### 4. ArgoCD 앱 경로 이상 가능성
- 일부 `iop-iw-api`, `iop-iw-sch` 애플리케이션 정의가 `path: tus-chart`를 가리킴
- 실제 의도인지, 복사 실수인지 점검 필요

### 5. 테스트 부재 또는 약함
- `iop-iw-api`는 현재 `src/test/java`가 보이지 않음
- 대형 시스템 대비 회귀 테스트가 약할 가능성이 큼

## API 프로젝트 요약

- 메인 클래스: `kr.go.iop.iw.MainApplication`
- Java 소스: 약 `488`개
- MyBatis XML 매퍼: `50`개
- 추출된 API 엔드포인트: `371`개
- 카테고리: `admin`, `api`, `assignment`, `attachment`, `auth`, `brm`, `document`, `ihb`, `mail`, `rag`, `schedule`, `share`, `todo`, `uscm`, `user`, `voc`

상세 API 목록은 아래 파일에 별도 정리했다.

- `IW_PROJECT_iop-iw-api_7.md`: 소스 재추출 기준 API 전수 목록

## 결과 파일 목록

- `IW_GROUP_1.md`
- `IW_GROUP_2.md`
- `IW_PROJECT_iop-iw-api_1.md`
- `IW_PROJECT_iop-iw-api_2.md`
- `IW_PROJECT_iop-iw-api_3.md`
- `IW_PROJECT_iop-iw-api_4.md`
- `IW_PROJECT_iop-iw-api_5.md`
- `IW_PROJECT_iop-iw-api_6.md`
- `IW_PROJECT_iop-iw-api_7.md`
- `IW_PROJECT_iop-iw-api_8.md`
- `IW_PROJECT_iop-iw-api_9.md`
- `IW_PROJECT_iop-iw-ui_1.md`
- `IW_PROJECT_iop-iw-ui_2.md`
- `IW_PROJECT_iop-iw-ui_3.md`
- `IW_PROJECT_iop-iw-tus_1.md`
- `IW_PROJECT_iop-iw-gpki_1.md`
- `IW_PROJECT_iop-iw-manifest_1.md`
- `IW_PROJECT_iop-iw-manifest_2.md`
- `IW_PROJECT_iop-iw-manifest_3.md`
- `IW_PROJECT_keycloak_1.md`
- `IW_PROJECT_keycloak_2.md`
- `IW_PROJECT_user-storage-jpa-master_1.md`
- `IW_PROJECT_user-storage-jpa-master_2.md`
