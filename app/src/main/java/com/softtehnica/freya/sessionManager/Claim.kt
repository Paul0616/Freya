package com.softtehnica.freya.sessionManager

enum class Claim(val value: String) {
    canAddNewSales("1"),
    canVoidSales("2"),
    canVoidItems("3"),
    canEditItems("4"),
    canGiveItemDiscounts("5"),
    canGiveSaleDiscounts("6"),
    canViewAllSales("7"),
    canViewParkedSales("8"),
    canViewDashboard("9"),
    canViewFiscalOperations("10"),
    canViewSettings("11"),

    canEditParkedSales("12"),
    canModifySalesFromOtherUsers("13"),    //  todo
    canReprintFiscalTickets("14"), //  todo
    canViewClientOrders("15"),
    canViewSaleClients("16"),
    canCloseFiscalDay("18") ,  //  todo
    canSearchProducts("19")
}