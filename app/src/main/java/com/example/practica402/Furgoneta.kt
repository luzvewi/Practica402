class Furgoneta(
    ruedas: Int, motor: String, numAsientos: Int, color: String, modelo: String, nombre: String
):
    Vehiculo(if (ruedas<6) ruedas else 6, motor, numAsientos, color, modelo, nombre) {

}