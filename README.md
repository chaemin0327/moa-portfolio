# 모아 · 경험 아카이브

경험을 하나씩 기록하고 카드, 타임라인, 포트폴리오로 돌아보는 개인용 웹앱 시제품입니다.

## 구현한 기능

- 프로젝트, 공모전, 수상 경력, 자격증, 논문, 해외 경험, 동아리/대외활동, 교육 경험 등록
- 제목, 기간, 기관, 장소, 함께한 사람, 역할, 내용, 성과, 태그, 관련 링크 기록
- 경험 수정·삭제, 검색, 종류별 필터, 정렬, 카드·목록 보기
- 시작일 기준 타임라인
- 별표로 선택한 경험의 포트폴리오 미리보기 및 브라우저 인쇄/PDF 저장
- 프로필 편집 및 H2 파일 DB 저장
- 첫 실행 시 가상 예시 6개 생성 및 예시만 삭제하는 기능

## 기술

Java 21 / Spring Boot 3.5.16 / Spring Data JPA / H2 / Maven Wrapper 3.9.11

화면은 Spring Boot가 제공하는 HTML, CSS, JavaScript로 작성했습니다.

## 실행

Java 21을 설치한 환경에서 프로젝트 폴더 안에서 실행합니다.

```sh
./mvnw spring-boot:run
```

Windows에서는 `mvnw.cmd spring-boot:run`을 사용합니다.

실행 후 <http://localhost:8087>에 접속합니다. 종료는 `Ctrl+C`입니다.

```sh
./mvnw test
./mvnw package
```

## IntelliJ IDEA

1. 프로젝트 폴더 또는 `pom.xml`을 Maven 프로젝트로 엽니다.
2. Project SDK는 Java 21, Maven은 Maven Wrapper를 사용합니다.
3. Maven 도구 창에서 프로젝트를 동기화합니다.
4. `MoaPortfolioApplication`의 `main` 또는 `Moa · 모아 실행` 구성을 실행합니다.
5. 테스트는 `PortfolioApiTests` 또는 `Moa · 전체 테스트` 구성을 사용합니다.

실행 포트가 사용 중이면 기존 앱을 종료하거나 `PORT` 환경변수를 변경합니다.

## 데이터 및 현재 범위

- DB는 프로젝트 폴더의 `data/`에 저장되며 Git에 포함하지 않습니다.
- 개인용 로컬 시제품으로, 회원가입·다중 사용자·공개 링크 배포·파일 업로드는 구현하지 않았습니다.
- 기본적으로 `127.0.0.1`에서만 접속할 수 있습니다.
- API 통합 테스트 7개를 포함합니다. IntelliJ 실행 설정은 작성 당시 점검 중이며, 터미널 Maven 빌드와 테스트는 통과했습니다.

경험 입력 흐름과 프로젝트 정리 방식은 [FlowCV](https://flowcv.com/)와 [Peerlist Projects](https://blog.peerlist.io/p/introducing-peerlist-projects)를 참고했습니다. 화면과 코드는 이 프로젝트에서 작성했습니다.
