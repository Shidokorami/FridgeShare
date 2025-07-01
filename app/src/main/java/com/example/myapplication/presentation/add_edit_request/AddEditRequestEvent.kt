package com.example.myapplication.presentation.add_edit_request

sealed class AddEditRequestEvent {

    data class EnteredName(val value: String) : AddEditRequestEvent()

    data class EnteredQuantity(val value: String) : AddEditRequestEvent()

    data class ChangeUnitSelection(val unit: String) : AddEditRequestEvent()
    data class ChangeUnitDropdownExpanded(val expanded: Boolean) : AddEditRequestEvent()

    data class ChangeExpirationDate(val date: Long?) : AddEditRequestEvent()

    object SaveProduct : AddEditRequestEvent()
    object DeleteProduct : AddEditRequestEvent()

    object ShowDeleteConfirmation: AddEditRequestEvent()
    object HideDeleteConfirmation: AddEditRequestEvent()
}