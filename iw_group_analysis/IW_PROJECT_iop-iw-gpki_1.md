# iop-iw-gpki 분석

## 역할

`iop-iw-gpki`는 GPKI 관련 웹 애플리케이션 패키지로 보인다.

## 기술 스택

- eGovFrame Boot Parent 4.3.0
- Maven
- WAR 패키징
- GPKI 관련 외부 라이브러리 의존

## pom 기준 특징

- `packaging=war`
- `org.egovframe.boot.starter.parent`
- 의존성
  - `com.gpki:gpkiapi`
  - `com.gpki:gpkisecureweb`
  - `com.gpki.secureweb:gpkisecurewebkeyboard`

## 현재 상태 해석

- `src` 아래 Java 소스는 현재 보이지 않는다.
- 즉, 소스 구현보다 GPKI 라이브러리 패키징/배포 목적일 수 있다.
- README도 매우 짧고 테스트 메모 수준이다.

## 추가 확인 근거

- `README.md`에는 `gpki 로그인을 위한 서블릿 WEB APP`라고 적혀 있다.
- `src/main/webapp/iw/gpki/gpkiLogin.jsp`와 `gpkiResult.jsp`가 존재한다.
- `DockerfileK8s`는 `iop-iw-gpki-1.0.0.war`를 Tomcat `ROOT.war`로 배포한다.
- `WEB-INF/conf/gpkiapi.conf`, `gpkisecureweb.properties`가 포함돼 있다.

즉, 이 프로젝트는 단순 의존성 패키징만이 아니라 실제 GPKI 웹 로그인 페이지와 결과 처리 JSP를 포함한 WAR 애플리케이션이다.

## 다른 프로젝트와의 관계

- `keycloak`, `user-storage-jpa-master` 안에 `GpkiAuthenticator` 클래스가 존재한다.
- 따라서 전체 GPKI 로그인 체계는 다음 중 하나일 수 있다.
  1. `iop-iw-gpki`가 별도 GPKI 웹 처리 담당
  2. Keycloak 인증기와 연동되는 보조 웹앱
  3. 과거/실험용 패키지

## 판단

현재 워크스페이스만 기준으로 보면 `운영 핵심 백엔드`보다는 `보조 또는 특수 로그인용 웹앱` 프로젝트로 보는 것이 합리적이다.

다만 기존 추정보다 역할은 더 선명하다.

- GPKI 로그인 화면/결과 처리 WAR
- Keycloak의 `GpkiAuthenticator`와 연계될 수 있는 외부 웹 컴포넌트
- 배포 저장소 기준 별도 서비스로 운영될 가능성 존재
