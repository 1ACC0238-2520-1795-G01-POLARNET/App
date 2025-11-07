package pe.edu.upc.polarnet.core.networking

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://nuzqgqngjujcumiwqerx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im51enFncW5nanVqY3VtaXdxZXJ4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjE5MjgyMzEsImV4cCI6MjA3NzUwNDIzMX0.i6pzqIz_VCCiz14bu24sujqdu5KdF9w59fxxgCpF_cg"
    ) {
        install(Postgrest)
        install(Auth)
    }
}