package com.fastcampus.deliverystoreapp.contoller.login

import com.fastcampus.deliverystoreapp.common.http.CookieUtils
import com.fastcampus.deliverystoreapp.common.http.HttpConstants
import com.fastcampus.deliverystoreapp.contoller.login.dto.LoginForm
import com.fastcampus.deliverystoreapp.external.login.StoreLoginAdapter
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

/**
 * 로그인 컨트롤러
 */
@Controller
class LoginController(
    private val storeLoginAdapter: StoreLoginAdapter,
) {

    @GetMapping("/login")
    fun loginForm(model: Model): String {
        val loginForm = LoginForm(
            email = "",
            password = "",
        )
        model.addAttribute("loginForm", loginForm)
        return "/login/login"
    }

    @PostMapping("/login")
    fun login(
        loginForm: LoginForm,
        model: Model,
        httpServletResponse: HttpServletResponse,
    ): String {
        val loginResponse = storeLoginAdapter.login(loginForm.email, loginForm.password)
            ?: error("로그인 시 오류가 발생하였습니다")

        httpServletResponse.addCookie(
            CookieUtils.createCookie(HttpConstants.COOKIE_NAME_STORE_ID, loginResponse.storeId.toString())
        )
        httpServletResponse.addCookie(
            CookieUtils.createCookie(HttpConstants.COOKIE_NAME_ACCESS_TOKEN, loginResponse.accessToken)
        )

        return "redirect:/orders"
    }
}