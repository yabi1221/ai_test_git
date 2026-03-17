# iop-iw-manifest 분석

## 역할

`iop-iw-manifest`는 애플리케이션 코드를 담는 저장소가 아니라 배포 정의를 담는 저장소다.

## 기술/구성

- Helm Chart
- ArgoCD Application
- 환경별 values 파일
- dev / prd 분리

## 확인된 차트

- `api-chart`
- `ui-chart`
- `ui-chart2`
- `tus-chart`
- `gpki-chart`
- `base-chart`

## 확인된 환경 대상

- `dev`
- `prd`

## 확인된 애플리케이션 묶음

- `iop-iw-api`
- `iop-iw-ui`
- `iop-iw-tus`
- `iop-iw-gpki`
- `iop-iw-sch`

## 중요한 관찰

### 1. 현재 워크스페이스에 없는 프로젝트 참조
- `iop-iw-sch`가 manifest에는 존재하지만 현재 `iw_group`에는 저장소가 없다.
- 스케줄러 전용 프로젝트가 별도 저장소이거나 누락된 상태로 보인다.

### 2. 실제 라우팅 정보 확인 가능
UI values 파일에서 다음 라우팅이 직접 확인된다.
- `/iw/files` → `iop-iw-tus-*-svc`
- `/iw/api/` → `iop-iw-api-*-svc`
- `/iw/public/` → `iop-iw-api-*-svc`
- `/iw` → `iop-iw-ui-*-svc`

즉, 프로젝트간 연결 관계를 가장 명확하게 보여주는 저장소다.

### 3. 의심되는 ArgoCD 경로 설정
- 일부 API/스케줄러 Application YAML이 `path: tus-chart`를 가리킨다.
- 예: `dev/iop-iw-api/application/iop-iw-api-mois-app.yaml`
- 이 값은 복사 실수 가능성이 높다.

## 프로젝트 간 연동성 관점의 의미

이 저장소를 기준으로 보면, 실제 포털은 다음 구조로 배포된다.

- 외부 사용자 진입: `UI`
- 업무 API: `API`
- 파일 업로드: `TUS`
- 특수 인증/부가 컴포넌트: `GPKI`
- 누락된 별도 컴포넌트 가능성: `SCH`

따라서 연동성 분석의 기준 저장소는 이 매니페스트라고 볼 수 있다.

## 파생 문서

- `IW_PROJECT_iop-iw-manifest_2.md`: 환경 차이, 검증 포인트, dev 범위 재해석을 묶은 분석
- `IW_PROJECT_iop-iw-manifest_3.md`: dev 실조합, application 정합성, prd 값 차이, 운영 위험을 묶은 심화 분석
