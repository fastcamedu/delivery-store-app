package com.fastcampus.deliverystoreapp.domain.order

/**
 * 상점에 들어온 주문 상태를 정의하는 Enum 클래스
 */
enum class StoreOrderStatus(val description: String) {
    READY("주문 대기"),
    COOKING("조리 중"),
    DELIVERING("배달 중"),
    CANCEL("주문 취소"),
    COMPLETE("배달 완료")
}