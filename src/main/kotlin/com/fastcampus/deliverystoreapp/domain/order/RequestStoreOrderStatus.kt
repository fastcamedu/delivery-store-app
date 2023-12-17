package com.fastcampus.deliverystoreapp.domain.order

/**
 * 상점에서 관리하는 주문을 조회시 사용하는 요청용 Enum 클래스 (검색용)
 */
enum class RequestStoreOrderStatus(val description: String) {
    ALL("전체"),
    READY("주문 대기"),
    COOKING("조리 중"),
    DELIVERING("배달 중"),
    CANCEL("주문 취소"),
    COMPLETE("배달 완료")
}