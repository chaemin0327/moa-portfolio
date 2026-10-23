# 경험 API

경험 CRUD는 `/api/experiences`를 사용합니다. `POST`는 201과 생성 결과, `GET`은 목록/단일 결과, `PUT /{id}`는 수정 결과, `DELETE /{id}`는 204를 반환합니다.

## 인턴/경력

```json
{
  "type": "WORK",
  "title": "백엔드 개발 인턴",
  "organization": "회사 / 개발팀",
  "startDate": "2026-07-01",
  "ongoing": true,
  "role": "백엔드 인턴",
  "description": "담당 업무와 직접 기여한 내용",
  "outcome": "업무 성과와 배운 점",
  "tags": "Java, SQL",
  "featured": true
}
```

## 직접 지정 분류

`type: CUSTOM`이면 `customCategory`에 최대 30자의 이름을 입력합니다. 누락·공백·30자 초과는 400입니다. 앞뒤·연속 공백은 저장 시 정리하며, 기본 종류로 수정하면 이름을 비웁니다.

```json
{
  "type": "CUSTOM",
  "customCategory": "봉사활동",
  "title": "청소년 코딩 교육",
  "startDate": "2026-05-01",
  "featured": true
}
```

응답은 `id`, 입력 필드, `demo`, `customCategory`를 포함합니다. 기존 8종의 요청은 새 필드 없이도 동작합니다. 제목·시작일·종류는 필수이고, 종료일은 시작일 이전일 수 없습니다. 진행 중이면 종료일을 비웁니다.

개인용 로컬 API이며 로그인·사용자별 권한은 아직 없습니다.

## 기존 H2 파일 DB 호환

분류는 Java enum을 문자열(VARCHAR)로 명시해 저장합니다. 초기 H2 네이티브 ENUM에 고정된 8종 제한을 없애고 `ddl-auto=update`로 기존 행을 보존한 채 전환합니다. 초기 ENUM 스키마와 기존 행이 있는 상태에서 WORK/CUSTOM 저장 및 이전 행 유지를 회귀 테스트합니다.
