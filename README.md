# '상점용 앱' 프로젝트
- 배달 상점에서 사용하는 서비스, 상점의 주문 관리를 위한 앱
- Role: **`delivery-store-app`**
- Port: **`20000`**

# Tech Stack
- Spring Boot 3
- JPA
- jQuery
- Thymeleaf
- Admin LTE

# 시스템 구성과 흐름
```mermaid
flowchart LR
    delivery-store-app[상점 배달앱 \n port:20000] --> delivery-store-api[배달 고객 API \n port:20001] --> delivery-store[(상점 데이터베이스\ndelivery_store)]
```

# 접속 URL
- 홈: http://localhost:20000/
- 주문목록: http://localhost:20000/orders?requestStoreOrderStatus=READY&storeId=7&queryBaseDate=2023-12-17

# 초기 계정
- ID: test@test.com
- PW: 1111