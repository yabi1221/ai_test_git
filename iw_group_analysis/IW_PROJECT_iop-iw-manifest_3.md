# iop-iw-manifest 심화 분석

## 목적

이 문서는 dev 실조합, application 정합성, prd 값 차이, 운영 위험을 묶은 심화 문서다.

## 1. dev 실조합

사용자 확인 기준으로 실개발 축은 `msit`, `mois`다.

공통 구조:

- UI host 기준 진입
- `/iw/api`는 API 서비스
- `/iw/files`는 TUS 서비스
- Keycloak realm, 기관코드, DB, Kafka user가 기관별로 치환

## 2. 가장 중요한 정합성 이슈

- 일부 `iop-iw-api` application이 `path: tus-chart`
- 일부 `iop-iw-sch` application도 `path: tus-chart`

이 패턴은 `dev`, `prd` 모두에서 보여서 복붙 누락 또는 템플릿 관리 문제 가능성이 높다.

### 확인 근거

- `dev/iop-iw-api/application/iop-iw-api-msit-app.yaml:14`
- `dev/iop-iw-api/application/iop-iw-api-mois-app.yaml:14`
- `prd/iop-iw-api/application/iop-iw-api-gov-app.yaml:14`
- `prd/iop-iw-api/application/iop-iw-api-mois-app.yaml:14`
- `prd/iop-iw-api/application/iop-iw-api-msit-app.yaml:14`
- `dev/iop-iw-sch/application/iop-iw-sch-msit-app.yaml:14`
- `dev/iop-iw-sch/application/iop-iw-sch-mois-app.yaml:14`
- `prd/iop-iw-sch/application/iop-iw-sch-gov-app.yaml:14`
- `prd/iop-iw-sch/application/iop-iw-sch-mois-app.yaml:14`
- `prd/iop-iw-sch/application/iop-iw-sch-msit-app.yaml:14`

## 3. 운영값 복붙 흔적

- `prd` 일부 값 파일에 타 기관 값이 섞인 흔적
- `dev-mois` 관련 IDM/Common API 흔적이 다른 축에 섞인 사례
- 존재하지 않는 `iop-iw-sch` 프로젝트 참조

### 확인 근거

- `dev/iop-iw-ui/values/iop-iw-ui-mfds-values.yaml`
  - 파일명은 `mfds`인데 내부 API/TUS 목적지는 `msit`
- `prd/iop-iw-ui/values/iop-iw-ui-msit-values.yaml:25`
  - `https://dev-mois.aiworks.go.kr/idm`
- `prd/iop-iw-ui/values/iop-iw-ui-gov-values.yaml:27`
  - `https://dev-mois.aiworks.go.kr/idm`
- `prd/iop-iw-ui/values/iop-iw-ui-mpb-values.yaml:22`
  - `https://dev-mois.aiworks.go.kr/idm`
- 현재 워크스페이스에는 `iop-iw-sch` 소스 저장소가 없음

## 결론

`manifest`는 이 그룹 전체에서 가장 강한 운영 리스크 저장소다.
