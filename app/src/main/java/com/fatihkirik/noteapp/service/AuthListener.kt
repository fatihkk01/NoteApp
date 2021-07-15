package com.fatihkirik.noteapp.service

interface AuthListener {

    fun onAuthSuccess()

    fun onAuthFail(errorMessage:String)

}