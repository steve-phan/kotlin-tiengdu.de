package de.tiengduc.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Objects
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


@Service
class JwtService {
    @Value("\${app.jwtSecret}")
    private lateinit var jwtSecret: String

    @Value("\${app.jwtExpirationInMs}")
    private var jwtExpirationInMs: Long = 0

    private val key: Key by lazy {
        Keys.hmacShaKeyFor(jwtSecret.toByteArray())
    }

    private fun generateToken(extraClaims : Map<String, Objects> ,userDetails: UserDetails, ) : String {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.username).signWith(key, SignatureAlgorithm.ES256).compact()
    }

    private fun  extractAllClaims(token: String) : Claims {
        return Jwts.parserBuilder()
                       .setSigningKey(key).build().parseClaimsJws(token).body
    }
}