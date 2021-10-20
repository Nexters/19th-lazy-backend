# 밍굴맹굴 백엔드 <img src="https://media.giphy.com/media/Caly6OiVnoQxEezvZ3/giphy.gif" width="50px">

넥스터즈 19기 밍굴맹굴 서버

## Spec

- Java 11
- Spring boot 2.5.2
- JPA
- MySQL
- AWS
  - EC2
  - RDS

## API Docs

[Swagger UI 2](https://swagger.io/tools/swagger-ui/) 적용

| `환경` | URL |
|:---:|:---|
| `개발` | http://15.164.86.177:9093/swagger-ui.html |
| `운영` | 배포 후 작성 |

## Social Login

OAuth 서버를 통해 Redirect 되면 JWT 토큰을 받을 수 있습니다.
Swagger Authorize에 BearerAuth를 추가해주셔야 테스트가 가능합니다.

| `Provider` | URL |
|:---:|:---|
| `Kakao` | http://15.164.86.177:9093/oauth2/authorization/kakao |
| `Apple` | 개발 중 |

## Convention

| `Base` | Link |
|:---|:---|
| `Git Branch` | [Git-Flow](https://techblog.woowahan.com/2553/) |
| `Code Convention` | [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) |
| `REST API` | [REST Resource Naming Guide](https://restfulapi.net/resource-naming/) |
| `Database` | [MySQL Naming Guide](https://dev.mysql.com/doc/refman/5.7/en/identifiers.html) |

---

@Copyright [NEXTERS 박영준](https://github.com/jun108059)