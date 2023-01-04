# MOBEE
영화 웹 어플리케이션
---
1. 프로젝트 정보 : 영화를 좋아하는 사람이 만든 영화관련 웹 사이트
* 작품명 : MOBEE ( Movie + Bee )
* 기획이유 : 내가 사용하는 영화 어플리케이션은 어떻게 만들어질까?
* 인원 : 이강희 ( 개인 )
* 설명 : 영화를 평가하고 유저간의 소통을 할 수 있는 커뮤니케이션 사이트
* 목표
> * 영화 사이트를 직접 구현하여 Bank-End System을 이해한다.
> * React.js를 활용하여 Front-End를 직접 구현하여 이해한다.
> * ORM (JPA)을 사용하여 객체지향스러운 개발을 이해한다.

2. 개발에 사용된 기술
* IDE : Intellij, VS Code
* Database Tool : DataGrip
* API : Postman
* Server : AWS EC2, S3, RDS
* 형상관리 : Github, Git bash, Git flow
* 개발환경 
> * SpringBoot 
> * Gradle 
> * Java 1.8 
> * MariaDB 
> * RedisDB 
> * Stomp-websocket 
> * Node.js ( 16 버전 이상 )
> * React-native
> * AWS EC2 Ubuntu / 22.LTS
> * JWT Token
> * Spring Security
> * Docker 
* 기술 사용 이유
> * EC2에는 BackEnd, S3에는 FrontEnd 빌드
> * 원활한 작업관리를 위해서 Git을 사용
> * 프로젝트 중 git flow 전략을 알게되어 추가하여 사용
> * 빠른 build와 가독성을 위해서 Gradle을 사용
> * 1.8을 사용하여 람다식과 Optional 사용
> * 데이터의 변경/확장될 일이 많이 없다고 판단하여 RDB를 사용하였으며 무료로 사용할 수 있는 MariaDB를 사용
> * 채팅방 인원을 정하기 위해서 Stomp-Websocket에서 지원하는 구독시스템을 사용
> * JWT과 Spring Security를 사용하여 보안을 강화 
> * Docker Container로 build하여 편리한 배포
* 추가 사항
> * 실시간 영화 크롤링
> https://github.com/yikanghee/mobeeProject/pull/7
> * 영어 번역 기능
> https://github.com/yikanghee/mobeeProject/pull/18
> * Swagger 추가
> https://github.com/yikanghee/mobeeProject/pull/21
> * 채팅 기능
> https://github.com/yikanghee/mobeeProject/pull/22
> * Exception 커스텀
> https://github.com/yikanghee/mobeeProject/pull/25



