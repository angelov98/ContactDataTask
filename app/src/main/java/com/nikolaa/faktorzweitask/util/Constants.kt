package com.nikolaa.faktorzweitask.util

object NavigationConstants {
    const val WELCOME_NAV_ROUTE = "welcome_nav_route"
    const val CONTACTS_NAV_ROUTE="contacts_nav_route"
    const val CONTACT_DETAILS_NAV_ROUTE="contact_details_nav_route"
}

object ApiConstants {
    const val USERS_ENDPOINT = "users"
    const val USERS_BY_ID_ENDPOINT = "/{id}"
    const val USERS_BY_ID_PATH = "id"

    //HEADERS
    const val USER_AGENT = "User-Agent"
    const val ACCEPT = "Accept"
    const val USER_AGENT_VALUE = "FaktorZweiApp"
    const val ACCEPT_VALUE = "application/json"
}

object DatabaseConstants {
    const val DATABASE_NAME = "faktor_zwei_database"
    const val USER_DB_NAME = "users"
}