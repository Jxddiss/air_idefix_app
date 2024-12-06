package com.nicholson.client_reservation_vol.donnée.fictive

import com.nicholson.client_reservation_vol.donnée.ISourceDeDonneeAuth

class SourceDeDonnéesAuthFictive : ISourceDeDonneeAuth {
    override suspend fun obtenirToken(): String {
        return "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxpUkhvcXVOWElHUXJMaUNweWJKSyJ9.eyJodHRwczovL2Fpci5pZMOpZml4LmNvbS8vcm9sZXMiOlsiRW1wbG95w6kgQWlyIElkw6lmaXgiXSwiY291cnJpZWwiOiJwaWVycmUuYmVybmFyZEBlbWFpbC5jb20iLCJpc3MiOiJodHRwczovL2Rldi03bGQ4MXBic3MzcGs3c2ljLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2NzUyNWFkMTdhYWQ2ODY2NWNhNDE2OWIiLCJhdWQiOlsiaHR0cHM6Ly9haXIuaWTDqWZpeC5jb20vIiwiaHR0cHM6Ly9kZXYtN2xkODFwYnNzM3BrN3NpYy51cy5hdXRoMC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNzMzNDUyNzk3LCJleHAiOjE3MzM1MzkxOTcsInNjb3BlIjoib3BlbmlkIHByb2ZpbGUiLCJndHkiOiJwYXNzd29yZCIsImF6cCI6ImhOTWtpcUZuclBmRjZqYTd1SE0xOXlodEdjT2c5V2JhIiwicGVybWlzc2lvbnMiOlsiY29uc3VsdGVyOmNsaWVudHMiLCJjcsOpZXI6Y2xpZW50cyIsImNyw6llcjp2b2xzIiwibW9kaWZpZXI6Y2xpZW50cyIsIm1vZGlmaWVyOnZvbHMiLCJzdXBwcmltZXI6Y2xpZW50cyJdfQ.SUcDXUGZfIM1gH_Uq3ndvlY6ckWJXg7AIDYc0nirgijh56RV-vHBMjDbrW07UtDjkWSIY8EPImXtoEyXt6PHw8sjHPNTccPwPEAqNoJ_DOtRtUMxRK_ok9ZOPbd8_cTQoxnJ7VEcrCpDtvW0vS03c97L0S7CJFfs27GLLwGlG4e2olwZjlAARL0OyOMLM5pldnF-By-JhVn_5HoXNIKe1VqAfEuvJqUOgGDWTuPPbqYSG_8WGz79n3z3vTdwa6JGOHpYXsY2E0XMZMImMwYcutPP9bHRD7M4X3rL69tm64zvtzCgC4UVm7ezEw-AOcJGdOwppVmpMznMVqdRuS_NRw"
    }

    override suspend fun seDeconnecter(): Boolean {
        return true
    }
}