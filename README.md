API명세서
| 기능         | 메서드   | URL                   | Request                     | Response                    | 응답코드 |
|--------------|----------|-----------------------|-----------------------------|-----------------------------|----------|
| 회원가입     | `POST`   | `/users/signup`        | `{ "username": "홍길동", "email": "example@email.com", "password": "password" }` | `{ "id": 1, "username": "홍길동", "email": "example@email.com" }` | `201 Created` |
| 로그인       | `POST`   | `/users/login`         | `{ "email": "example@email.com", "password": "password" }` | `{ "id": 1, "username": "홍길동", "email": "example@email.com" }` | `200 OK` |
| 사용자 조회  | `GET`    | `/users/{id}`          | 없음                        | `{ "id": 1, "username": "홍길동", "email": "example@email.com" }` | `200 OK` |
| 이메일 수정  | `PATCH`  | `/users/{id}/email`    | `{ "oldEmail": "example@email.com", "newEmail": "new@email.com" }` | 없음                        | `200 OK` |
| 회원탈퇴     | `DELETE` | `/users/{id}`          | 없음                        | 없음                        | `204 No Content` |
