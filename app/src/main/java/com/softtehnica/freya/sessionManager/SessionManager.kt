package com.softtehnica.freya.sessionManager

import com.softtehnica.freya.models.Location
import com.softtehnica.freya.models.User
import com.softtehnica.freya.saleManager.SaleManager

class SessionManager {

    companion object {
        var token: String? = null
        var currentUser: User? = null
        var currentLocation: Location? = null

        private val _claims = ArrayList<Claim>()
        val claims: ArrayList<Claim>
            get() = _claims

        fun loadClaims(claimStrings: List<String>) {
            for (claimString in claimStrings) {
                for (claim in Claim.values()) {
                    if (claimString == claim.value) {
                        _claims.add(claim)
                        break
                    }
                }
            }
        }

        fun logOut() {
            SaleManager.parkSale()
            token = null
            currentUser = null
            currentLocation = null
            claims.clear()
        }
    }


}