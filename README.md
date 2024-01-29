# Open-Market
</br>

## 개요
- 쿠팡과 네이버 스마트스토어와 같은 online market place 서비스
- 목표
  - 객체지향 설계에 따라 유지보수성과 확장성을 고려하여 개발
  - 대용량 데이터 처리, 대규모 트래픽에도 견고하도록 설계
  - 코드 품질 향상을 위한 테스트 코드 작성
  - CI/CD를 통한 빌드/배포 자동화
</br>
</br>

## 기술 스택
- Java 17
- Spring Boot 3.2.2
- Gradle
- MyBatis
- MySQL 8.0
- Redis
- Jenkins
</br>
</br>

## 시스템 아키텍처
![architecture](https://github.com/f-lab-edu/open-market/assets/26354121/8eecb5fe-e1d1-4f31-a447-ab08820af252)
</br>
</br>

## ERD (설계중)
</br>
</br>

## git flow
![git-flow](https://github.com/f-lab-edu/open-market/assets/26354121/6a8de4c0-0778-4f5a-9718-047fa2aa489d)
- master : 제품으로 출시될 수 있는 브랜치
- develop : 다음 출시 버전을 개발하는 브랜치, feature에서 개발된 내용이 저장
- feature : 기능을 개발하는 브랜치, develop에서 파생된 브랜치
- release : 배포를 하기 전 내용을 QA(품질 검사)하기 위한 브랜치
- hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치
</br>

참고: https://techblog.woowahan.com/2553/
</br>
</br>

## 기능 정의
</br>
</br>

