# 협업 가이드

[여기남김 협업 가이드](https://github.com/ApptiveDev/yeogi-namgim/blob/main/docs/CONTRIBUTING.md)의 이슈·브랜치·커밋·PR 기준을 킵잇에 적용합니다.

## 작업 순서

1. 이슈 생성: 목적, 구현 범위, 완료 조건을 쓰고 담당자와 타입·영역 라벨을 지정합니다.
2. 최신 `main`에서 이슈별 작업 브랜치를 만듭니다.
3. 구현과 필요한 검증을 진행하고, 확인한 항목만 이슈에서 완료 표시합니다.
4. 작업 브랜치를 푸시하고 `main` 대상 PR을 만듭니다.
5. PR에 변경 이유, 확인 결과, 관련 이슈를 기록합니다.
6. CI와 변경 내용을 확인하고 `Squash and merge`로 병합합니다. 별도 리뷰가 필요하지 않은 작업은 작성자가 확인 후 병합할 수 있습니다.
7. 병합된 작업 브랜치를 삭제하고 로컬 `main`을 동기화합니다.

`main`에 직접 기능 커밋을 올리지 않습니다. 진행 중이면 Draft PR을 사용합니다.

## 영역과 라벨

| 영역 | 경로·책임 | 라벨 |
| --- | --- | --- |
| BE | `src/main/java/`, API, 검증, JPA, DB, `src/test/java/` | `be` |
| FE | `src/main/resources/static/`, HTML, CSS, JavaScript, 화면 상호작용 | `fe` |
| COMMON | Maven, `.github/`, 공통 문서·개발 환경 | `common` |

작업 타입은 `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`를 사용합니다. 이슈와 PR에는 타입 라벨과 영역 라벨을 모두 붙입니다. 화면 색상 등 사용자 경험 개선은 `feat`, 동작 변경 없는 코드 포맷만 `style`입니다.

## 제목과 브랜치

- 이슈: `[BE] feat: 인턴 및 사용자 지정 경험 분류 지원`
- 브랜치: `feat/be/#3-experience-categories`
- 커밋: `feat: 인턴 및 사용자 지정 경험 분류 지원`
- PR: `feat: 인턴 및 사용자 지정 경험 분류 지원`

영역은 이슈에서 `BE`, `FE`, `COMMON`, 브랜치에서 `be`, `fe`, `common`으로 표기합니다. 두 영역이 한 작업에 꼭 필요한 경우 `[FE/BE]`, `fe-be`, 라벨 `fe`·`be`를 함께 사용합니다. 브랜치 작업명은 영문 소문자·하이픈을 사용합니다.

커밋과 PR 제목에는 영역·이슈번호를 반복하지 않고, 소문자 타입과 간결한 한글 요약을 씁니다. 제목 끝에는 마침표를 붙이지 않습니다.

```sh
git switch main
git fetch origin --prune
git merge --ff-only origin/main
git switch -c 'feat/be/#3-experience-categories'
# 구현·검증·커밋
git push -u origin 'feat/be/#3-experience-categories'
```

## PR 완료 기준

- 목적이 하나이며 BE/FE 변경은 가능한 한 별도 이슈·PR로 나눕니다.
- 대상은 최신 `main`이며 충돌을 해결합니다.
- 담당자, 타입·영역 라벨, 확인 결과를 기록합니다.
- 이슈를 완료하면 `Closes #번호`, 일부 작업이면 `Refs #번호`로 연결합니다.
- 테스트·빌드·화면 검증 중 필요한 확인을 수행합니다. 확인하지 못한 것은 명시합니다.
- 필수 리뷰 의견이 있다면 해결하고, 스쿼시 커밋 제목은 PR 제목으로 합니다.

## 검증

```sh
./mvnw test
./mvnw package
node --check src/main/resources/static/app.js
git diff --check
```

BE는 유효한 요청·잘못된 입력·저장 후 조회·기존 데이터 호환을 확인합니다. FE는 실제 화면에서 입력·수정·필터·타임라인·포트폴리오와 모바일 넘침을 확인합니다. 단순 표시 문구 변경에 불필요한 테스트를 추가하지 않습니다.

개인 기록이 들어 있는 `data/`, `.idea/`, 실행 로그와 비밀정보는 커밋하지 않습니다.
