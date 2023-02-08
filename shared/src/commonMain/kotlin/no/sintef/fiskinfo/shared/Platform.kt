package no.sintef.fiskinfo.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform