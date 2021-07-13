import models.User
//interface para definir las dos respuesta que se pueden obtener del servidor success/failure
interface Callback {
    fun onSuccess(user: User)
    fun onFailure(exception: Exception)
}