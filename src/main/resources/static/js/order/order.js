$(function () {

    let storeId = $("input[name='storeId']").val();
    let queryBaseDate = $("input[name='queryBaseDate']").val();
    let requestStoreOrderStatus = $("input[name='requestStoreOrderStatus']").val();

    /**
     * 주문 준비 상태 탭에서 주문 클릭시
     * @param eventSelector
     */
    function handleReadyOrderStatus(eventSelector) {
        console.log(">>> [주문 준비 상태 탭] 주문 클릭");
        let orderId = eventSelector.data('order-id');
        $("input[name='chosenOrderId']").val(orderId);

        $.ajax({
            url: "http://localhost:40001/apis/orders/" + orderId,
            beforeSend: function( xhr ) {
            }
        })
            .done(function( data ) {
                let modalDialogOrderItems = $(".modal-dialog-order-items");
                console.log( "Sample of data:", data.orderItems);
                modalDialogOrderItems.html("")
                let modalDialogOrderItemTags = ""
                data.orderItems.forEach(function(orderItem) {
                    modalDialogOrderItemTags += "<tr>";
                    modalDialogOrderItemTags += "<td>" + orderItem.menuName + "</td>";
                    modalDialogOrderItemTags += "<td>" + orderItem.menuQuantity + "</td>";
                    modalDialogOrderItemTags += "<td>" + (orderItem.menuQuantity * orderItem.menuPrice) + "</td>";
                    modalDialogOrderItemTags += "</tr>";
                });
                modalDialogOrderItems.html(modalDialogOrderItemTags);

                // 주문 수락/거절 모달창
                $('#order-confirm-modal').modal('show');
            });
    }

    // 주문 준비 상태 탭에서 주문 클릭시
    $(".order-shorten-id").click(function () {
        let requestStoreOrderStatus = $("input[name='requestStoreOrderStatus']").val();
        if (requestStoreOrderStatus === "READY") {
            handleReadyOrderStatus($(this));
        }
    });

    $('#delivery-order-request-confirm-modal').on('shown.bs.modal', function () {
        // do something...
    });

    // 주문 거절 버튼 클릭, 고객에게 알림
    $('.order-reject-btn').on('click', function () {
        let chosenOrderId = $("input[name='chosenOrderId']").val();

        console.log(">>> 주문 거절, 주문 ID: " + chosenOrderId + "> 상점 ID: " + storeId);

        let requestData = {
            "orderId": chosenOrderId,
            "storeId": storeId,
            "storeOrderCommand": "REJECT"
        }
        $.ajax({
            type: 'PUT',
            data: JSON.stringify(requestData),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: "http://localhost:20001/apis/stores/orders/status",
        })
        .done(function( data ) {
            $('#cooking-time-choose-modal').modal('hide');
            $('#order-confirm-modal').modal('hide');
            location.reload();
        });
    });

    // 주문 수락 버튼 클릭
    $('.order-confirm-btn').on('click', function () {
       // 주문 수락, 조리시간 팝업
        $('#cooking-time-choose-modal').modal('show');
    });

    $('#cooking-time-choose-modal').on('shown.bs.modal', function () {

    });

    // 조리 시간 선택 버튼 이벤트
    $('.cooking-time-btn').on('click', function () {
        let cookingMinutes = $(this).data('cooking-time');
        let chosenOrderId = $("input[name='chosenOrderId']").val();
        let storeId = $("input[name='storeId']").val();
       console.log("> 조리 시간 선택: " + cookingMinutes);
       console.log("> 주문 ID: " + chosenOrderId);
       console.log("> 상점 ID: " + storeId);

        $(".cooking-time-btn").on('click', function () {
            let requestData = {
                "orderId": chosenOrderId,
                "storeId": storeId,
                "cookingMinutes": cookingMinutes,
                "storeOrderCommand": "ACCEPT"
            }
            console.log(">>> 조리 시간 선택: ", requestData);
            $.ajax({
                type: 'PUT',
                data: JSON.stringify(requestData),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                url: "http://localhost:20001/apis/stores/orders/status",
            })
            .done(function( data ) {
                $('#cooking-time-choose-modal').modal('hide');
                $('#order-confirm-modal').modal('hide');
                location.reload();
            });
        });
    });

    // 주문 수락 모달창에서 뒤로가기 버튼 클릭
    $(".modal-header-title-left-arrow").on('click', function () {
        $('#order-confirm-modal').modal('show');
        $('#cooking-time-choose-modal').modal('hide');
    });

    // 주문 대기 탭 클릭
    $("#custom-tabs-order-ready-tab").on('click', function () {
        let changeRequestStoreOrderStatus = "READY";
        location.href = "http://localhost:20000/orders?requestStoreOrderStatus=" + changeRequestStoreOrderStatus + "&storeId=" + storeId + "&queryBaseDate=" + queryBaseDate;
    });

    // 조리중 탭 클릭
    $("#custom-tabs-cooking-tab").on('click', function () {
        let changeRequestStoreOrderStatus = "COOKING";
        location.href = "http://localhost:20000/orders?requestStoreOrderStatus=" + changeRequestStoreOrderStatus + "&storeId=" + storeId + "&queryBaseDate=" + queryBaseDate;
    });

    // 주문 목록 전체 조회 탭 클릭
    $("#custom-tabs-all-tab").on('click', function () {
        let changeRequestStoreOrderStatus = "ALL";
        location.href = "http://localhost:20000/orders?requestStoreOrderStatus=" + changeRequestStoreOrderStatus + "&storeId=" + storeId + "&queryBaseDate=" + queryBaseDate;
    });

    // 조리 완료 버튼 클릭
    // 인증 토큰을 헤더에 담아서 전송
    $(".btn-cooking-complete").on('click', function () {
        let accessToken = Cookies.get("AccessToken");
        let chosenOrderId = $(this).data('order-id');
        console.log(">>> 조리 완료 클릭, 주문 ID: " + chosenOrderId + ", 상점 ID: " + storeId);
        let requestData = {
            "orderId": chosenOrderId,
            "storeId": storeId,
            "storeOrderCommand": "COMPLETE"
        }
        $.ajax({
            type: 'PUT',
            data: JSON.stringify(requestData),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: "http://localhost:20001/apis/stores/orders/status",
            statusCode: {
                401: function (response) {
                    console.log(">>> 인증 실패");
                    location.href = "/login";
                },
                403: function(data) {
                    location.href = "/login";
                }
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
            },
            success: function(data){
                console.log(">>> 조리 완료 응답");
                console.log(data);
                $('#cooking-time-choose-modal').modal('hide');
                $('#order-confirm-modal').modal('hide');
            },
            error : function(xhr, ajaxSettings, thrownError){
                console.log(">>> 조리 완료 실패");
                console.log(data);
                alert("조리 완료시 오류가 발생하였습니다. 다시 시도해주세요.");
            },
            complete : function() {
                location.reload();
            }
        });
    });
});