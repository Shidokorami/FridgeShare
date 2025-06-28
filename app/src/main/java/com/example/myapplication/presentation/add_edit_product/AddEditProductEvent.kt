package com.example.myapplication.presentation.add_edit_product

sealed class AddEditProductEvent {
    data class EnteredName(val value: String) : AddEditProductEvent()

    data class EnteredQuantity(val value: String) : AddEditProductEvent()

    data class ChangeUnitSelection(val unit: String) : AddEditProductEvent()
    data class ChangeUnitDropdownExpanded(val expanded: Boolean) : AddEditProductEvent()

    data class ChangeExpirationDate(val date: Long?) : AddEditProductEvent()

    object SaveProduct : AddEditProductEvent()
    object DeleteProduct : AddEditProductEvent()

    object ShowDeleteConfirmation: AddEditProductEvent()
    object HideDeleteConfirmation: AddEditProductEvent()
}