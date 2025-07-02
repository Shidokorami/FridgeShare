import com.example.myapplication.domain.model.ProductRequest

sealed class RequestListEvent {
    data class ChangeSelectedTab(val newIndex: Int) : RequestListEvent()
    data class OnDeleteRequestClick(val requestId: String) : RequestListEvent()
    data class OnFulfillRequestClick(val requestId: String, val currentUserId: String) : RequestListEvent()
    data class OnConfirmFulfillRequest(val updatedRequest: ProductRequest) : RequestListEvent() // nowy event
}
