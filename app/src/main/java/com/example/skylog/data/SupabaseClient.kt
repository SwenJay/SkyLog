package com.example.skylog.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {

    val client = createSupabaseClient(
        supabaseUrl = "https://vysvgismohsamxzzojjr.supabase.co",
        supabaseKey = "sb_publishable_ZbcMbuR1q6L_4_Cal6WrkA_2cDBqSol"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}