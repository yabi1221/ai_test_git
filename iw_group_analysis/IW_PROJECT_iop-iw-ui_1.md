# iop-iw-ui 분석

## 역할

`iop-iw-ui`는 포털 사용자 화면을 담당하는 프론트엔드다. 실제 포털 진입점으로 보이며 `/iw` 경로 기준으로 서비스된다.

## 기술 스택

- React
- TypeScript
- Vite
- Axios
- React Query
- React Router
- TailwindCSS
- Nginx
- Docker
- Module Federation 흔적 존재

## 규모

- `src` 파일 수 약 `853`

## 빌드

- `package.json` 기준 Node 프로젝트
- 주요 스크립트
- `dev`
- `build`
- `build:prod`
- `preview`
- `type-check`

## 핵심 설정

환경변수에서 확인된 연결 대상:
- `VITE_API_BASE_URL`
- `VITE_UPLOAD_BASE_URL`
- `VITE_KEYCLOAK_BASE_URL`
- `VITE_KEYCLOAK_REALM`
- `VITE_KEYCLOAK_CLIENT_ID`
- `VITE_COMMON_API_BASE_URL`
- `VITE_WIDGET_REMOTE_URL`
- `VITE_AI_ADMIN_REMOTE_URL`
- `VITE_CATALOG_REMOTE_URL`
- `VITE_TUS_API_KEY`
- `VITE_BASE_PATH=/iw`

## 코드상 연동성

### API 서버
- `src/config/api.ts`에서 `VITE_API_BASE_URL` 기반 Axios 인스턴스 사용
- Bearer 토큰 자동 첨부
- 토큰 만료 시 refresh 흐름 처리

### Keycloak
- UI 환경변수에 Keycloak base URL / realm / client id가 직접 존재
- 로그인과 토큰 재발급 흐름이 프론트에 연결돼 있음

### TUS 서버
- 업로드 관련 환경변수와 API 키 존재
- 매니페스트에서 `/iw/files`가 TUS 서비스로 라우팅됨

### 외부 원격 앱
- `VITE_WIDGET_REMOTE_URL`
- `VITE_AI_ADMIN_REMOTE_URL`
- `VITE_CATALOG_REMOTE_URL`
- 즉, 포털 자체 외에 원격 프론트 모듈을 합성하는 구조가 있다.

## 배포 관점

- `Dockerfile`, `docker-compose.yml`, `nginx.conf`, `DockerfileK8s` 존재
- 매니페스트에서는 `/iw`는 UI 서비스로, `/iw/api`는 API로, `/iw/files`는 TUS로 분기

## 프로젝트 간 연동성

- `iop-iw-api`: 업무 API 호출 대상
- `iop-iw-tus`: 파일 업로드 대상
- `keycloak`: 인증 대상
- 외부 AI/위젯/카탈로그 프론트와 추가 연동

## 해석

이 UI는 단순 SPA가 아니라, 인증과 다중 원격 모듈을 포함한 포털 셸 역할을 한다.

## 파생 문서

- `IW_PROJECT_iop-iw-ui_2.md`: UI와 API 매핑, 화면-서비스 연결, 실사용 범위를 묶은 호출 분석
- `IW_PROJECT_iop-iw-ui_3.md`: 진입점, 인증, 에디터/RAG 결합, 호출 방식 혼재를 묶은 심화 분석
