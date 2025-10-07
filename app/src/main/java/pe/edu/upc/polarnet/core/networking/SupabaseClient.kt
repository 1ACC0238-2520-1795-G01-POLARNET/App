package pe.edu.upc.polarnet.core.networking

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://ivbtkzjqjjblwcokutkk.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Iml2YnRrempxampibHdjb2t1dGtrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTk3ODU5MDQsImV4cCI6MjA3NTM2MTkwNH0.pWyezcw8v9rwFfeSlhIBcqcN3isZvOMhCvrf83HggsA"
    ) {
        install(Postgrest)
        install(Auth)
    }
}