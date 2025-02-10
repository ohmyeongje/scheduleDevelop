API명세서
| 기능         | 메서드   | URL                       | Request                                               | Response                                                | 응답코드         |
|--------------|----------|---------------------------|-------------------------------------------------------|---------------------------------------------------------|------------------|
| 회원가입     | `POST`   | `/users/signup`            | `{ "username": "홍길동", "email": "example@email.com", "password": "password" }` | `{ "id": 1, "username": "홍길동", "email": "example@email.com" }` | `201 Created`    |
| 로그인       | `POST`   | `/users/login`             | `{ "email": "example@email.com", "password": "password" }` | `{ "id": 1, "username": "홍길동", "email": "example@email.com" }` | `200 OK`        |
| 사용자 조회  | `GET`    | `/users/{id}`              | 없음                                                  | `{ "id": 1, "username": "홍길동", "email": "example@email.com" }` | `200 OK`        |
| 이메일 수정  | `PATCH`  | `/users/{id}/email`        | `{ "oldEmail": "example@email.com", "newEmail": "new@email.com" }` | 없음                                                    | `200 OK`        |
| 회원탈퇴     | `DELETE` | `/users/{id}`              | 없음                                                  | 없음                                                    | `204 No Content`|
| 일정 생성    | `POST`   | `/schedules`               | `{ "taskTitle": "회의", "dueDate": "2025-02-10T10:00:00" }` | `{ "id": 1, "taskTitle": "회의", "dueDate": "2025-02-10T10:00:00" }` | `201 Created`    |
| 일정 조회    | `GET`    | `/schedules/{id}`          | 없음                                                  | `{ "id": 1, "taskTitle": "회의", "dueDate": "2025-02-10T10:00:00" }` | `200 OK`        |
| 일정 수정    | `PATCH`  | `/schedules/{id}`          | `{ "taskTitle": "수정된 회의", "dueDate": "2025-02-11T10:00:00" }` | `{ "id": 1, "taskTitle": "수정된 회의", "dueDate": "2025-02-11T10:00:00" }` | `200 OK`        |
| 일정 삭제    | `DELETE` | `/schedules/{id}`          | 없음                                                  | 없음                                                    | `204 No Content`|


ERD다이어그램
![스크린샷 2025-02-10 180629](https://github.com/user-attachments/assets/de227b49-89ac-46cc-be63-3962e187e18e)


일정앱 과정 설명
 Lv 1. 일정 CRUD  `필수`

일정을 생성, 조회, 수정, 삭제할 수 있습니다.
일정은 아래 필드를 가집니다.
    - `작성 유저명`, `할일 제목`, `할일 내용`, `작성일`, `수정일` 필드
    - `작성일`, `수정일` 필드는 `JPA Auditing`을 활용합니다

 Lv 2. 유저 CRUD  `필수`

 유저를 생성, 조회, 수정, 삭제할 수 있습니다.
 유저는 아래와 같은 필드를 가집니다.
`유저명`, `이메일`, `작성일` , `수정일` 필드
 `작성일`, `수정일` 필드는 `JPA Auditing`을 활용합니다.
 연관관계 구현
    - 일정은 이제 `작성 유저명` 필드 대신 `유저 고유 식별자` 필드를 가집니다


Lv 3. 회원가입  `필수`

유저에 `비밀번호` 필드를 추가합니다.
    - 비밀번호 암호화는 도전 기능에서 수행합니다.

  Lv 4. 로그인(인증)  `필수`

- 키워드
    **인터페이스**
    - HttpServletRequest / HttpServletResponse : 각 HTTP 요청에서 주고받는 값들을 담고 있습니다.
   **설명**
   **Cookie/Session**을 활용해 로그인 기능을 구현합니다. → `2주차 Servlet Filter 실습 참고!`
       필터를 활용해 인증 처리를 할 수 있습니다.
       `@Configuration` 을 활용해 필터를 등록할 수 있습니다.
      **조건**
           `이메일`과 `비밀번호`를 활용해 로그인 기능을 구현합니다.
           회원가입, 로그인 요청은 인증 처리에서 제외합니다.
       **예외처리**
           로그인 시 이메일과 비밀번호가 일치하지 않을 경우 HTTP Status code 401을 반환합니다.
